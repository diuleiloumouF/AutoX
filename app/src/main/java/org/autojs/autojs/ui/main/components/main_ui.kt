package org.autojs.autojs.ui.main.components

import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.autojs.autojs.ui.log.LogActivityKt
import org.autojs.autojs.ui.main.web.DocumentSourceSelectDialog
import org.autojs.autojs.ui.main.web.EditorAppManager.Companion.loadHomeDocument
import org.autojs.autojs.ui.main.web.EditorAppManager.Companion.openDocument
import org.autojs.autoxjs.R

//主界面日志按钮
@Composable
fun LogButton() {
    val context = LocalContext.current
    IconButton(onClick = { LogActivityKt.start(context) }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_logcat),
            contentDescription = stringResource(id = R.string.text_logcat)
        )
    }
}

//文档界面菜单按钮
@Composable
fun DocumentPageMenuButton(getWebView: () -> WebView) {
    val context = LocalContext.current
    Box {
        var expanded by remember { mutableStateOf(false) }
        fun dismissMenu() {
            expanded = false
        }
        IconButton({ expanded = true }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
        }
        val iconModifier = Modifier.size(24.dp)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(text = "回到主页") },
                leadingIcon = {
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Default.Home,
                        contentDescription = null
                    )
                },
                onClick = {
                    dismissMenu()
                    loadHomeDocument(getWebView())
                }
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.text_browser_open)) },
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_external_link),
                        modifier = iconModifier,
                        contentDescription = null
                    )
                },
                onClick = {
                    dismissMenu()
                    openDocument(context)
                })
            DropdownMenuItem(
                text = { Text(text = "刷新") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Refresh,
                        modifier = iconModifier,
                        contentDescription = null
                    )
                },
                onClick = {
                    dismissMenu()
                    getWebView().clearCache(false)
                    getWebView().reload()
                })
            DropdownMenuItem(
                text = { Text(text = "选择文档源") },
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.community_list),
                        modifier = iconModifier,
                        contentDescription = null
                    )
                },
                onClick = {
                    dismissMenu()
                    DocumentSourceSelectDialog(getWebView()).show()
                })
        }
    }
}