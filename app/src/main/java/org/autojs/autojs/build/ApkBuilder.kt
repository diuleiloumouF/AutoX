package org.autojs.autojs.build

import android.util.Log
import androidx.core.net.toUri
import com.aiselp.autox.apkbuilder.AAPT_Util
import com.aiselp.autox.apkbuilder.ApkKeyStore
import com.aiselp.autox.apkbuilder.ApkSignUtil
import com.stardust.app.GlobalAppContext
import com.stardust.autojs.apkbuilder.ManifestEditor
import com.stardust.autojs.project.BuildInfo
import com.stardust.autojs.project.Constant
import com.stardust.autojs.project.ProjectConfig
import com.stardust.autojs.script.EncryptedScriptFileHeader
import com.stardust.autojs.script.JavaScriptFileSource
import com.stardust.io.Zip
import com.stardust.pio.PFiles
import com.stardust.util.AdvancedEncryptionStandard
import com.stardust.util.MD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import org.autojs.autojs.tool.addAllIfNotExist
import org.autojs.autojs.tool.copyTo
import org.autojs.autojs.tool.parseUriOrNull
import pxb.android.StringItem
import pxb.android.axml.AxmlWriter
import zhao.arsceditor.ArscUtil
import zhao.arsceditor.ResDecoder.ARSCDecoder
import zhao.arsceditor.ResDecoder.data.ResTable
import java.io.*
import java.security.DigestOutputStream
import java.security.MessageDigest
import java.util.regex.Pattern
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Created by Stardust on 2017/10/24.
 * Modified by wilinz on 2022/5/23
 */
class ApkBuilder(
    private val apkInputStream: InputStream,
    private val outApkFile: File,
    private val workspacePath: String
) {

    object BuildState {
        const val PREPARE = 0
        const val BUILD = 1
        const val SIGN = 2
        const val CLEAN = 3
        const val FINISH = 4
    }

    private var _progressState: MutableSharedFlow<Int> = MutableSharedFlow()
    val progressState get() = _progressState.asSharedFlow()

    //    private var progressCallback: ProgressCallback? = null
    private var arscPackageName: String? = null
    private var manifestEditor: ManifestEditor? = null
    private var projectConfig: ProjectConfig? = null
    private val waitSignApk1: String =
        PFiles.join(outApkFile.parent!!, outApkFile.nameWithoutExtension + NO_SIGN_APK_SUFFIX)
    private var advancedEncryptionStandard: AdvancedEncryptionStandard? = null

    private val nativePath = File(GlobalAppContext.get().cacheDir, "native-lib").path

    private var splashThemeId: Int = 0
    private var noDisplayThemeId: Int = 0

    /**
     * 新建工作目录并解压apk
     */
    suspend fun prepare(): ApkBuilder {
        _progressState.emit(BuildState.PREPARE)
        File(workspacePath).mkdirs()
        PFiles.deleteFilesOfDir(File(workspacePath))
        Zip.unzip(apkInputStream, File(workspacePath))
        unzipLibs()
        return this
    }

    suspend fun finish() {
        _progressState.emit(BuildState.FINISH)
    }

    private fun unzipLibs() {
        val context = GlobalAppContext.get()
        val myApkFile =
            File(context.packageManager.getApplicationInfo(context.packageName, 0).sourceDir)
        Zip.unzip(myApkFile, File(nativePath), "lib/")
    }

    private fun setScriptFile(path: String): ApkBuilder {
        if (PFiles.isDir(path)) {
            copyDir(path, "assets/project/")
        } else if (PFiles.isFile(path)) {
            val mainFile = File(path)
            replaceFile(oldFile = File(path), "assets/project/${mainFile.name}")
            projectConfig!!.mainScript = mainFile.name
        } else {
            throw IllegalArgumentException("Invalid source path: $path")
        }
        return this
    }

    private fun getAbsolutePath(name: String): String = projectConfig!!.getAbsolutePath(name)

    fun copyDir(
        srcPath: String,
        relativeTargetPath: String,
        ignoredPathList: List<String> = emptyList(),
    ) {
        val ignoredPath1 = ignoredPathList.toMutableList()
            .apply { addAllIfNotExist(projectConfig!!.ignoredDirs.map { getAbsolutePath(it) }) }

        val fromDir = File(srcPath)
        val toDir = File(workspacePath, relativeTargetPath)
        toDir.mkdirs()

        val children = fromDir.listFiles()
            ?.filter { file ->
                !ignoredPath1.any {
                    val dir = if (it.endsWith("/")) it else "$it/"
                    (file.path.startsWith(dir) || file.canonicalFile == File(it).canonicalFile)
                }
            } ?: return

        for (child in children) {
            if (child.isFile) {
                if (child.name.endsWith(".js")) {
                    encryptToDir(child, toDir)
                } else {
                    child.copyTo(File(toDir, child.name), true)
                }
            } else {
                copyDir(child.path, PFiles.join(relativeTargetPath, child.name + "/"))
            }
        }
    }

    private fun encryptToDir(file: File, toDir: File) {
        val newFile = File(toDir, file.name)
        encrypt(file, newFile)
    }

    private fun encrypt(file: File, newFile: File) {
        if (!projectConfig!!.isEncrypt) {
            newFile.delete()
            file.copyTo(newFile)
            return
        }
        val out = newFile.outputStream()
        EncryptedScriptFileHeader.writeHeader(
            out, JavaScriptFileSource(file).executionMode.toShort()
        )
        advancedEncryptionStandard!!.encrypt(file.inputStream(), out, true)
    }

    fun replaceFile(oldFile: File, relativeTargetPath: String): ApkBuilder {
        val newFile = File(workspacePath, relativeTargetPath)
        if (newFile.name.endsWith(".js")) {
            encrypt(oldFile, newFile)
        } else {
            oldFile.copyTo(newFile, overwrite = true)
        }
        return this
    }

    fun withConfig(config: ProjectConfig): ApkBuilder {
        projectConfig = config
        if (!config.launchConfig.displaySplash) {
            //在这里给splashThemeId赋值
            val arsc = File(workspacePath, "resources.arsc")
            val util = ArscUtil()
            util.openArsc(arsc.absolutePath) { _, type, key, value, id ->
                if (type == "style") {
                    if (key == "ScriptTheme.Splash") {
                        splashThemeId = id
                    } else if (key == "SplashNoDisplay") {
                        noDisplayThemeId = id
                    }
                }
            }
        }
        manifestEditor = editManifest()
            .setAppName(config.name)
            .setVersionName(config.versionName)
            .setVersionCode(config.versionCode)
            .setPackageName(config.packageName)
        setEncryptKey(config)
        arscPackageName = config.packageName!!
        copyLibraries(config)
        copyAssets(config)
        setScriptFile(config.sourcePath!!)
        updateAndWriteProjectConfig(config)
        return this
    }

    private fun copyAssets(config: ProjectConfig) {
        config.assets.forEach {
            var form = it.form
            if (!form.matches(Regex("^.*?://.*|^/.*")) || form.startsWith("./")) {
                form = getAbsolutePath(form)
            }
            val relativeTo = File("assets", it.to)
            val to = File(workspacePath, relativeTo.path)

            if (form.startsWith(Constant.Protocol.ASSETS)) {
                val path = form.replace(Constant.Protocol.ASSETS + "/", "")
                PFiles.copyAssetDir(GlobalAppContext.get().assets, path, to.path, null)
                return@forEach
            }

            if (it.to == Constant.Assets.PROJECT) {
                val file = File(form)
                copyDir(
                    srcPath = file.path,
                    relativeTargetPath = relativeTo.path,
                    ignoredPathList = listOf(
                        getAbsolutePath(ProjectConfig.CONFIG_FILE_NAME),
                        getAbsolutePath(projectConfig!!.sourcePath!!)
                    )
                )
                return@forEach
            }

            val file = File(form)
            if (file.isFile) {
                file.copyTo(to, overwrite = true)
            } else {
                copyDir(
                    srcPath = file.path,
                    relativeTargetPath = relativeTo.path,
                )
            }

        }
    }

    private fun copyLibraries(config: ProjectConfig) {
        config.libs.addAllIfNotExist(Constant.Libraries.TERMINAL_EMULATOR)
        config.abis.forEach { abi ->
            val abiDir = File(nativePath, abi)
            if (!abiDir.isDirectory) return@forEach
            if (config.useNodejs) {
                abiDir.list()?.find {
                    it.startsWith("libjavet-")
                }?.let {
                    File(abiDir, it).copyTo(
                        File(workspacePath, "lib/$abi/$it"), true
                    )
                }
            }
            config.libs.forEach { name ->
                kotlin.runCatching {
                    File(abiDir, name).copyTo(
                        File(workspacePath, "lib/$abi/$name"),
                        overwrite = true
                    )
                }
            }
        }
    }

    @Throws(FileNotFoundException::class)
    fun editManifest(): ManifestEditor {
        manifestEditor = ManifestEditorWithAuthorities(FileInputStream(manifestFile))
        return manifestEditor!!
    }

    private val manifestFile: File
        get() = File(workspacePath, "AndroidManifest.xml")

    private fun setEncryptKey(config: ProjectConfig) {
        config.buildInfo = BuildInfo.generate(
            config.versionCode.toLong()
        )
        advancedEncryptionStandard = AdvancedEncryptionStandard(
            MD5.md5(config.packageName + config.versionName).toByteArray(Charsets.UTF_8),
            MD5.md5(config.buildInfo.buildId + config.name).substring(0, 16)
        )
    }

    private fun updateAndWriteProjectConfig(config: ProjectConfig) {
        val projectFile = File(workspacePath, "assets/project/project.json")
        projectFile.parentFile?.let { if (!it.exists()) it.mkdirs() }
        projectFile.writeText(config.toJson())
    }

    suspend fun build(): ApkBuilder = withContext(Dispatchers.IO) {
        _progressState.emit(BuildState.BUILD)
        manifestEditor?.commit()
        manifestEditor?.writeTo(FileOutputStream(manifestFile))
        val dir = projectConfig!!.projectDirectory
        buildArsc(
            onReplaceIcon = { key, path ->
                val icon: String? = when (key) {
                    Constant.ResourceId.LAUNCHER_ICON -> projectConfig!!.icon
                    Constant.ResourceId.SPLASH_ICON -> projectConfig!!.launchConfig.splashIcon
                    else -> null
                }
                icon?.let { iconPath ->
                    val uri =
                        parseUriOrNull(iconPath) ?: File(
                            dir,
                            iconPath
                        ).toUri()
                    uri.copyTo(GlobalAppContext.get(), File(workspacePath, path))
                }
            },
        )
        return@withContext this@ApkBuilder
    }

    suspend fun sign(
        apkKeyStore: ApkKeyStore,
        v1Sign: Boolean = true,
        v2Sign: Boolean = true,
        v3Sign: Boolean = false,
        v4Sign: Boolean = false
    ): ApkBuilder {
        _progressState.emit(BuildState.SIGN)
        withContext(Dispatchers.IO) {
            val waitSignApk = File(waitSignApk1)
            ZipOutputStream(waitSignApk.outputStream()).use {
                inZip(File(workspacePath), it)
            }
            val optimizeFile =
                File(waitSignApk.parentFile, waitSignApk.nameWithoutExtension + "-opt.apk")
            AAPT_Util.aapt2Optimize(waitSignApk, optimizeFile)

            val s = ApkSignUtil.sign(
                optimizeFile,
                outApkFile,
                apkKeyStore,
                v1Sign, v2Sign, v3Sign, v4Sign
            )
            optimizeFile.delete()
            if (!s) throw Exception("sign apk error")
        }
        return this
    }

    suspend fun cleanWorkspace(): ApkBuilder {
        _progressState.emit(BuildState.CLEAN)
        delete(File(workspacePath))
        delete(File(nativePath))
        val waitSignApk = File(waitSignApk1)
        if (waitSignApk.exists()) {
            delete(waitSignApk)
        }
        return this
    }

    private fun buildArsc(
        onReplaceIcon: (key: String, path: String) -> Unit
    ) {
        val oldArsc = File(workspacePath, "resources.arsc")
        val newArsc = File(workspacePath, "resources_new.arsc")
        val decoder =
            ARSCDecoder(BufferedInputStream(FileInputStream(oldArsc)), null as ResTable?, false)
        FileOutputStream(newArsc).use {
            //这里替换包名后会导致资源文件出错，因此还是用原包名"org.autojs.autoxjs.inrt"
            //2025/3/21 fix: 资源不再与包名绑定
            decoder.CloneArsc(it, arscPackageName, false)
        }
        val util = ArscUtil()
        util.openArsc(newArsc.absolutePath) { _, type, key, value, id ->
            when (type) {
                "mipmap", "drawable" -> {
                    onReplaceIcon(key, value)
                }
            }
        }
        // 收集字符资源，以准备根据key进行替换

        util.getResouces("string", "[DEFAULT]")
        val launchConfig = projectConfig!!.launchConfig
        launchConfig.splashText.let { util.changeResource("powered_by_autojs", it) }
        if (launchConfig.serviceDesc.isNotBlank()) {
            util.changeResource(
                "text_accessibility_service_description",
                launchConfig.serviceDesc
            )
        }
        util.saveArsc(newArsc.absolutePath, oldArsc.absolutePath)
        newArsc.delete()
    }

    private fun delete(file: File) {
        if (file.isFile) {
            file.delete()
        } else {
            val files = file.listFiles() ?: return
            for (child in files) {
                delete(child)
            }
            file.delete()
        }
    }

    // 重新压缩apk
    private fun inZip(dir: File, out: ZipOutputStream) {
        val md = MessageDigest.getInstance("SHA-256")
        val dos = DigestOutputStream(out, md)
        delete(File(dir.path + "/META-INF/MANIFEST.MF"))
        delete(File(dir.path + "/META-INF/CERT.RSA"))
        delete(File(dir.path + "/META-INF/CERT.SF"))
        val files = dir.listFiles() ?: return
        for (file in files) {
            if (file.isFile) {
                doFile(file.name, file, out, dos)
            } else {
                doDir(file.name + "/", file, out, dos)
            }
        }
    }

    private inner class ManifestEditorWithAuthorities(manifestInputStream: InputStream) :
        ManifestEditor(manifestInputStream) {
        override fun onAttr(attr: AxmlWriter.Attr) {
            if (projectConfig!!.launchConfig.isHideLauncher && attr.value is StringItem && "android.intent.category.LAUNCHER" == (attr.value as StringItem).data) {
                Log.e("attr", "onAttr: " + (attr.value as StringItem).data + "----" + "")
                (attr.value as StringItem).data = "android.intent.category.DEFAULT"
            } else if (projectConfig!!.launchConfig.isHideAccessibilityServices && "permission" == attr.name.data && attr.value is StringItem && "android.permission.BIND_ACCESSIBILITY_SERVICE" == (attr.value as StringItem).data) {
                (attr.value as StringItem).data = ""
            } else if (!projectConfig!!.launchConfig.displaySplash && splashThemeId != 0 && attr.value == splashThemeId) {
                attr.value = noDisplayThemeId
            } else if ("authorities" == attr.name.data && attr.value is StringItem) {
                val item = attr.value as StringItem
                item.data = item.data.replace(inrtAppId, projectConfig!!.packageName!!)
            } else {
                super.onAttr(attr)
            }
        }
    }

    companion object {
        private const val inrtAppId = "org.autojs.autoxjs.inrt"
        private const val NO_SIGN_APK_SUFFIX = "_no-sign.apk"
        private val stripPattern = Pattern.compile("^META-INF/(.*)[.](SF|RSA|DSA|MF)$")
        private const val TAG: String = "ApkBuilder"

        private fun doDir(
            prefix: String,
            dir: File,
            zos: ZipOutputStream,
            dos: DigestOutputStream
        ) {
            zos.putNextEntry(ZipEntry(prefix))
            zos.closeEntry()
            val files = dir.listFiles() ?: return
            for (f in files) {
                if (f.isFile) {
                    doFile(prefix + f.name, f, zos, dos)
                } else {
                    doDir(prefix + f.name + "/", f, zos, dos)
                }
            }
        }

        private fun doFile(name: String, f: File, zos: ZipOutputStream, dos: DigestOutputStream) {
            zos.putNextEntry(ZipEntry(name))
            f.inputStream().use {
                it.copyTo(dos)
            }
            zos.closeEntry()
        }
    }

    init {
        PFiles.ensureDir(outApkFile.path)
    }

}