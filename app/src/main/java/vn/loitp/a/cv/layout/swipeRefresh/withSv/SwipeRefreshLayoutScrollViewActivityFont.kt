package vn.loitp.a.cv.layout.swipeRefresh.withSv

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setColorForSwipeRefreshLayout
import com.loitp.core.ext.setDelay
import com.loitp.core.utilities.LStoreUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_layout_swipe_refresh_sv.*
import vn.loitp.R

@LogTag("SwipeRefreshLayoutScrollViewActivity")
@IsFullScreen(false)
class SwipeRefreshLayoutScrollViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_swipe_refresh_sv
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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = SwipeRefreshLayoutScrollViewActivityFont::class.java.simpleName
        }
        swipeRefreshLayout.setOnRefreshListener {
            doTask()
        }
        swipeRefreshLayout.setColorForSwipeRefreshLayout()
        val poem = LStoreUtil.readTxtFromRawFolder(R.raw.loitp)
        textView.text = poem
    }

    private fun doTask() {
        setDelay(5000) {
            swipeRefreshLayout?.isRefreshing = false
            showShortInformation("Finish", true)
        }
    }
}