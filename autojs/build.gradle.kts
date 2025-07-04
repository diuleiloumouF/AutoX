import org.gradle.kotlin.dsl.support.listFilesOrdered

plugins {
    id("com.android.library")
    id("kotlin-android")
}
kotlin {
    jvmToolchain(17)
}
android {
    compileSdk = versions.compile

    defaultConfig {
        minSdk = versions.mini
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("int", "MIN_SDK_VERSION", versions.mini.toString())
        buildConfigField("int", "VERSION_CODE", versions.appVersionCode.toString())
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
        aidl = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = compose_version
    }

    lint.abortOnError = false
    sourceSets {
        named("main") {
            res.srcDirs("src/main/res", "src/main/res-i18n")
        }
    }
    compileOptions {
        sourceCompatibility = versions.javaVersion
        targetCompatibility = versions.javaVersion
    }
    namespace = "com.stardust.autojs"
}

dependencies {
    androidTestImplementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.test.rules)
    debugImplementation(libs.leakcanary.android)
    implementation(libs.leakcanary.`object`.watcher.android)
    testImplementation(libs.junit)

    implementation(libs.coil.compose)
    implementation(libs.glide)
    implementation(libs.documentfile)
    implementation(libs.preference.ktx)
    implementation(libs.javet.android.node)
    api(libs.rxjava3.rxandroid)

    api(libs.ktsh)
    api(libs.bundles.shizuku)
    api("net.lingala.zip4j:zip4j:1.3.2")
    api("com.afollestad.material-dialogs:core:0.9.2.3")
    api(libs.material)
    api("com.github.hyb1996:EnhancedFloaty:0.31")
    api("com.makeramen:roundedimageview:2.3.0")
    // OkHttp
    api(libs.okhttp)
    // RootShell
    api("com.github.Stericson:RootShell:1.6")
    // Gson
    api(libs.google.gson)
    api(project(path = ":common"))
    api(project(path = ":automator"))
    api(project(path = ":LocalRepo:libtermexec"))
    api(project(path = ":LocalRepo:emulatorview"))
    api(project(path = ":LocalRepo:term"))
    api(project(path = ":LocalRepo:p7zip"))
    api(project(path = ":LocalRepo:OpenCV"))
    api(project(":paddleocr"))
    api(libs.mozilla.rhino)
    api(libs.mozilla.rhino.xml)
    api(libs.mozilla.rhino.tools)
    // libs
    api(fileTree("./libs") { include("dx.jar") })
    implementation("cz.adaptech:tesseract4android:4.1.1")
    implementation(libs.bundles.mlkit)
}

tasks.register("buildJsModule") {
    val v7ApiDir = File(projectDir, "src/main/js/v7-api")
    val v7ModuleDir = File(projectDir, "src/main/assets/v7modules")

    val v6ApiDir = File(projectDir, "src/main/js/v6-api")
    val v6ModuleDir = File(projectDir, "src/main/assets/v6modules")
    doFirst {
        exec {
            workingDir = v7ApiDir
            commandLine("node", "build.mjs")
        }
        exec {
            workingDir = v6ApiDir
            commandLine("node", "build.mjs")
        }
        copy {
            delete(v7ModuleDir)
            from(File(v7ApiDir, "dist"))
            into(v7ModuleDir)
        }
        copy {
            delete(v6ModuleDir)
            from(File(v6ApiDir, "dist"))
            into(v6ModuleDir)
        }
    }
}