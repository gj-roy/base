package vn.loitp.app.activity.customviews.webview

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LAppResource
import com.views.LWebView
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_web_view.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_web_view)
@LogTag("LWebViewActivity")
@IsFullScreen(false)
class LWebViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView.onScrollChangedCallback = object : LWebView.OnScrollChangedCallback {
            override fun onScroll(l: Int, t: Int, oldl: Int, oldt: Int) {
            }

            override fun onScrollTopToBottom() {
                logD("onScrollTopToBottom")
            }

            override fun onScrollBottomToTop() {
                logD("onScrollBottomToTop")
            }

            override fun onProgressChanged(progress: Int) {
                logD("onProgressChanged $progress")
                pb.progress = progress
                if (progress == 100) {
                    pb.visibility = View.GONE
                } else {
                    pb.visibility = View.VISIBLE
                }
            }
        }

        btLoadUrl.setSafeOnClickListener {
            webView.loadUrl("http://truyentranhtuan.com/")
        }
        btLoadData.setSafeOnClickListener {
            webView.loadDataString(bodyContent = "Hello, world!")
        }
        btLoadDataCustom.setSafeOnClickListener {
            val fontSizePx = LAppResource.getDimenValue(R.dimen.txt_small)
            webView.loadDataString(bodyContent = getString(R.string.large_dummy_text),
                    backgroundColor = "black",
                    textColor = "white",
                    textAlign = "justify",
                    fontSizePx = fontSizePx
            )
        }
        btLoadDataFromAsset.setSafeOnClickListener {
            webView.loadUrl("file:///android_asset/web/policy.html")
        }
        swEnableCopyContent.setOnCheckedChangeListener { _, isChecked ->
            webView.setEnableCopyContent(isChecked)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
