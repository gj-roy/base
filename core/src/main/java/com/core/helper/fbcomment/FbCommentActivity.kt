package com.core.helper.fbcomment

import android.annotation.SuppressLint
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.R
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.views.LToast
import com.views.actionbar.LActionBar
import kotlinx.android.synthetic.main.l_activity_fb_cmt_core.*

class FbCommentActivity : BaseFontActivity() {
    internal var isLoading: Boolean = false
    private var postUrl: String? = null
    private var adView: AdView? = null
    private var mWebviewPop: WebView? = null

    companion object {
        // the default number of comments should be visible
        // on page load.
        private const val NUMBER_OF_COMMENTS = 50
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()
        val adUnitId = intent.getStringExtra(Constants.AD_UNIT_ID_BANNER)
        LLog.d(TAG, "adUnitId $adUnitId")
        if (adUnitId.isNullOrEmpty()) {
            lnAdview.visibility = View.GONE
        } else {
            adView = AdView(activity)
            adView?.let {
                it.adSize = AdSize.SMART_BANNER
                it.adUnitId = adUnitId
                LUIUtil.createAdBanner(it)
                lnAdview.addView(it)
                lnAdview.requestLayout()
                //int navigationHeight = DisplayUtil.getNavigationBarHeight(activity);
                //LUIUtil.setMargins(lnAdview, 0, 0, 0, navigationHeight + navigationHeight / 3);
            }
        }

        LUIUtil.setColorProgressBar(progressBar, ContextCompat.getColor(activity, R.color.colorPrimary))

        postUrl = if (Constants.IS_DEBUG) {
            "https://www.androidhive.info/2016/06/android-firebase-integrate-analytics/"
        } else {
            intent.getStringExtra(Constants.FACEBOOK_COMMENT_URL)
        }

        // finish the activity in case of empty url
        if (TextUtils.isEmpty(postUrl)) {
            LToast.show(activity, "The web url shouldn't be empty")
            onBackPressed()
            return
        }

        setLoading(isLoading = true)
        loadComments()
    }

    private fun setupActionBar() {
        lActionBar.apply {
            setOnClickBack(object : LActionBar.Callback {
                override fun onClickBack(view: View) {
                    onBackPressed()
                }

                override fun onClickMenu(view: View) {
                    //LToast.show(activity, "onClickMenu");
                }
            })
            inviMenuIcon()
            hideBlurView()
            setTvTitle("Facebook Comment")
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_fb_cmt_core
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadComments() {
        commentsWebView.apply {
            webViewClient = UriWebViewClient()
            webChromeClient = UriChromeClient()
            settings.javaScriptEnabled = true
            settings.setAppCacheEnabled(true)
            settings.domStorageEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.setSupportMultipleWindows(true)
            settings.setSupportZoom(false)
            settings.builtInZoomControls = false
            CookieManager.getInstance().setAcceptCookie(true)
            if (Build.VERSION.SDK_INT >= 21) {
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
            }

            // facebook comment widget including the article url
            val html = "<!doctype html> <html lang=\"en\"> <head></head> <body> " +
                    "<div id=\"fb-root\"></div> <script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = \"//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6\"; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'facebook-jssdk'));</script> " +
                    "<div class=\"fb-comments\" data-href=\"" + postUrl + "\" " +
                    "data-numposts=\"" + NUMBER_OF_COMMENTS + "\" data-order-by=\"reverse_time\">" +
                    "</div> </body> </html>"

            loadDataWithBaseURL("http://www.nothing.com", html, "text/html", "UTF-8", null)
            minimumHeight = 200
        }
    }

    private fun setLoading(isLoading: Boolean) {
        this.isLoading = isLoading
        if (isLoading) {
            LUIUtil.setProgressBarVisibility(progressBar, View.VISIBLE)
        } else {
            LUIUtil.setDelay(1000, Runnable {
                LUIUtil.setProgressBarVisibility(progressBar, View.GONE)
            })
        }
        invalidateOptionsMenu()
    }

    private inner class UriWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            val host = Uri.parse(url).host
            return host != "m.facebook.com"
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            //val host = Uri.parse(url).host
            setLoading(false)
            if (url.contains(other = "/plugins/close_popup.php?reload")) {
                val handler = Handler()
                handler.postDelayed({
                    rlWebview.removeView(mWebviewPop)
                    loadComments()
                }, 600)
            }
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            setLoading(false)
        }
    }

    private inner class UriChromeClient : WebChromeClient() {

        @SuppressLint("SetJavaScriptEnabled")
        override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message): Boolean {
            mWebviewPop = WebView(applicationContext)
            mWebviewPop?.let {
                it.isVerticalScrollBarEnabled = false
                it.isHorizontalScrollBarEnabled = false
                it.webViewClient = UriWebViewClient()
                it.webChromeClient = this
                it.settings.javaScriptEnabled = true
                it.settings.domStorageEnabled = true
                it.settings.setSupportZoom(false)
                it.settings.builtInZoomControls = false
                it.settings.setSupportMultipleWindows(true)
                it.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                rlWebview.addView(it)
            }
            val transport = resultMsg.obj as WebView.WebViewTransport
            transport.webView = mWebviewPop
            resultMsg.sendToTarget()
            return true
        }

        override fun onConsoleMessage(cm: ConsoleMessage): Boolean {
            LLog.d(TAG, "onConsoleMessage: " + cm.message())
            return true
        }

        override fun onCloseWindow(window: WebView) {}
    }

    public override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    public override fun onResume() {
        adView?.resume()
        super.onResume()
    }

    public override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }
}
