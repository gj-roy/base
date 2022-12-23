package vn.loitp.a.cv.iv

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_iv_menu.*
import vn.loitp.R
import vn.loitp.a.cv.iv.bigIv.BigIvActivity
import vn.loitp.a.cv.iv.bigIv.BigIvWithSvActivity
import vn.loitp.a.cv.iv.circleIv.CircleIvActivity
import vn.loitp.a.cv.iv.coil.CoilActivity
import vn.loitp.a.cv.iv.comic.ComicViewActivity
import vn.loitp.a.cv.iv.continuousScrollable.ContinuousScrollableImageViewActivity
import vn.loitp.a.cv.iv.fidgetSpinner.FidgetSpinnerIvActivity
import vn.loitp.a.cv.iv.kenburn.KenburnViewActivity
import vn.loitp.a.cv.iv.panorama.PanoramaIvActivity
import vn.loitp.a.cv.iv.pinchToZoom.PinchToZoomViewPagerActivity
import vn.loitp.a.cv.iv.previewImageCollection.PreviewImageCollectionActivity
import vn.loitp.a.cv.iv.reflection.ReflectionActivity
import vn.loitp.a.cv.iv.roundedIv.RoundedIvActivity
import vn.loitp.a.cv.iv.scrollParallax.ScrollParallaxIvActivity
import vn.loitp.a.cv.iv.shapeableIv.ShapeableIvActivity
import vn.loitp.a.cv.iv.stfaiconIv.ListActivity
import vn.loitp.a.cv.iv.strectchy.StrectchyIvActivity
import vn.loitp.a.cv.iv.touch.TouchIvActivity
import vn.loitp.a.cv.iv.zoom.ZoomIvActivity

@LogTag("ImageViewMenuActivity")
@IsFullScreen(false)
class MenuImageViewActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_iv_menu
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
            this.tvTitle?.text = vn.loitp.a.cv.iv.MenuImageViewActivity::class.java.simpleName
        }
        btCirleImageView.setOnClickListener(this)
        btStretchyImageView.setOnClickListener(this)
        btTouchImageView.setOnClickListener(this)
        btZoomImageView.setOnClickListener(this)
        btFidgetSpinner.setOnClickListener(this)
        btContinuousScrollableImageView.setOnClickListener(this)
        btScrollParallaxImageView.setOnClickListener(this)
        btPanoramaImageView.setOnClickListener(this)
        btBigImageView.setOnClickListener(this)
        btBigImageViewWithScrollView.setOnClickListener(this)
        btTouchImageViewWithViewPager.setOnClickListener(this)
        btKenburnView.setOnClickListener(this)
        btComicView.setOnClickListener(this)
        btStfalconImageViewer.setOnClickListener(this)
        btReflection.setOnClickListener(this)
        btRoundedImageView.setOnClickListener(this)
        btPreviewImageCollection.setOnClickListener(this)
        btShapeableImageViewActivity.setOnClickListener(this)
        btCoil.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btCirleImageView -> launchActivity(CircleIvActivity::class.java)
            btStretchyImageView -> launchActivity(StrectchyIvActivity::class.java)
            btTouchImageView -> launchActivity(TouchIvActivity::class.java)
            btZoomImageView -> launchActivity(ZoomIvActivity::class.java)
            btFidgetSpinner -> launchActivity(FidgetSpinnerIvActivity::class.java)
            btContinuousScrollableImageView -> launchActivity(ContinuousScrollableImageViewActivity::class.java)
            btScrollParallaxImageView -> launchActivity(ScrollParallaxIvActivity::class.java)
            btPanoramaImageView -> launchActivity(PanoramaIvActivity::class.java)
            btBigImageView -> launchActivity(BigIvActivity::class.java)
            btBigImageViewWithScrollView -> launchActivity(BigIvWithSvActivity::class.java)
            btTouchImageViewWithViewPager -> launchActivity(PinchToZoomViewPagerActivity::class.java)
            btKenburnView -> launchActivity(KenburnViewActivity::class.java)
            btComicView -> launchActivity(ComicViewActivity::class.java)
            btStfalconImageViewer -> launchActivity(ListActivity::class.java)
            btReflection -> launchActivity(ReflectionActivity::class.java)
            btRoundedImageView -> launchActivity(RoundedIvActivity::class.java)
            btPreviewImageCollection -> launchActivity(PreviewImageCollectionActivity::class.java)
            btShapeableImageViewActivity -> launchActivity(ShapeableIvActivity::class.java)
            btCoil -> launchActivity(CoilActivity::class.java)
        }
    }
}