package org.autojs.autojs.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class SwipeRefreshWebView : SwipeRefreshLayout {

    val webView = WebView(context)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        isEnabled = false
        webView.apply {
            fillMaxSize()
            setup()
        }
        addView(webView)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK &&
            event.action == KeyEvent.ACTION_UP &&
            webView.canGoBack()
        ) {
            webView.goBack()
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun WebView.setup() {
        settings.apply {
            javaScriptEnabled = true  //设置支持Javascript交互
            javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
            allowContentAccess = true;
            defaultTextEncodingName = "utf-8"//设置编码格式
            setSupportMultipleWindows(false)
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
            setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
            builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
            displayZoomControls = false //设置原生的缩放控件，启用时被leakcanary检测到内存泄露
            useWideViewPort = true //让WebView读取网页设置的viewport，pc版网页
            loadWithOverviewMode = false
            loadsImagesAutomatically = true //设置自动加载图片
            blockNetworkImage = false
            blockNetworkLoads = false;
            setNeedInitialFocus(true);
            saveFormData = true;
            domStorageEnabled = true
            databaseEnabled = true   //开启 database storage API 功能
            safeBrowsingEnabled = false
            mediaPlaybackRequiresUserGesture = false;
            // 5.0以上允许加载http和https混合的页面(5.0以下默认允许，5.0+默认禁止)
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
        }
    }

}
