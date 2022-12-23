package vn.loitp.a.cv.iv.bigIv

import android.net.Uri
import android.os.Bundle
import com.github.piasy.biv.loader.ImageLoader
import com.github.piasy.biv.view.GlideImageViewFactory
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LDialogUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_big_iv.*
import vn.loitp.R
import vn.loitp.common.Constants
import java.io.File

// https://github.com/Piasy/BigImageViewer
@LogTag("BigImageViewActivity")
@IsFullScreen(false)
class BigIvActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_big_iv
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = BigIvActivity::class.java.simpleName
        }

        LDialogUtil.hideProgress(progressBar)
        bigImageView.setImageViewFactory(GlideImageViewFactory())
        bigImageView.setImageLoaderCallback(object : ImageLoader.Callback {
            override fun onCacheHit(imageType: Int, image: File) {}

            override fun onCacheMiss(imageType: Int, image: File) {}

            override fun onStart() {
                LDialogUtil.showProgress(progressBar)
            }

            override fun onProgress(progress: Int) {
                logD("onProgress $progress")
            }

            override fun onFinish() {}

            override fun onSuccess(image: File) {
                logD("onSuccess")
                val ssiv = bigImageView.ssiv
                ssiv?.isZoomEnabled = true
                LDialogUtil.hideProgress(progressBar)
            }

            override fun onFail(error: Exception) {}
        })

        bt0.setSafeOnClickListener {
            bigImageView.showImage(
                Uri.parse(Constants.URL_IMG_LARGE_LAND_S),
                Uri.parse(Constants.URL_IMG_LARGE_LAND_O)
            )
        }
        bt1.setSafeOnClickListener {
            bigImageView.showImage(
                Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_S),
                Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_O)
            )
        }
        bt2.setSafeOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_LONG))
        }
        bt3.setSafeOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_GIFT))
        }
    }
}