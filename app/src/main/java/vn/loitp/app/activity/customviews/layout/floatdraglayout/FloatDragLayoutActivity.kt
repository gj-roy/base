package vn.loitp.app.activity.customviews.layout.floatdraglayout

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.layout.floatdraglayout.DisplayUtil
import com.loitpcore.views.layout.floatdraglayout.FloatDragLayout
import com.loitpcore.views.layout.floatdraglayout.FloatDragPopupWindow
import kotlinx.android.synthetic.main.activity_float_drag_layout.*
import vn.loitp.app.R

@LogTag("FloatDragLayoutActivity")
@IsFullScreen(false)
class FloatDragLayoutActivity : BaseFontActivity(), View.OnClickListener {

    var floatDragPopupWindow: FloatDragPopupWindow? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_float_drag_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = FloatDragLayoutActivity::class.java.simpleName
        }
        var rootView = window.decorView as ViewGroup
        rootView = rootView.findViewById(android.R.id.content)

        val floatDragLayout = FloatDragLayout(this)
        floatDragLayout.setBackgroundResource(R.drawable.ic_launcher_loitp)
        val size = DisplayUtil.dp2px(this, 45)
        val layoutParams = FrameLayout.LayoutParams(size, size)
        floatDragLayout.layoutParams = layoutParams
        layoutParams.gravity = Gravity.CENTER_VERTICAL
        rootView.addView(floatDragLayout, layoutParams)

        floatDragLayout.setOnClickListener {
            showShortInformation("Click on the hover and drag buttons", true)
        }
        btChangeToFullScreen.setOnClickListener(this)
        btChangeToNoTitle.setOnClickListener(this)
        btChangeToWindows.setOnClickListener(this)
        btShowFloatDragPopupWindow.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view) {
            btChangeToFullScreen -> startActivity(
                Intent(
                    this,
                    FloatDragFullScreenActivity::class.java
                )
            )
            btChangeToNoTitle -> startActivity(Intent(this, FloatDragNoTitleActivity::class.java))
            btChangeToWindows -> startActivity(
                Intent(
                    this,
                    FloatDragWindowModeActivity::class.java
                )
            )
            btShowFloatDragPopupWindow -> showFloatDragPopupWindow()
        }
    }

    private fun showFloatDragPopupWindow() {
        if (floatDragPopupWindow == null) {
            val contentView = ImageView(this)
            contentView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            contentView.setImageResource(R.drawable.ic_launcher_loitp)
            floatDragPopupWindow = FloatDragPopupWindow.Builder(this)
                .setContentView(contentView)
                .setPosition(0, 300)
                .setOnClickListener { showShortInformation("Click on FloatDragPopupWindow", true) }
                .build()
        }
        floatDragPopupWindow?.show()
    }
}
