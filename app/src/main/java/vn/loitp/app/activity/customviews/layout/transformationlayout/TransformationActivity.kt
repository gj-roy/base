package vn.loitp.app.activity.customviews.layout.transformationlayout

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.skydoves.transformationlayout.onTransformationStartContainer
import kotlinx.android.synthetic.main.activity_transformation_main.*
import vn.loitp.app.R

// https://github.com/skydoves/TransformationLayout
@LogTag("MainActivity")
@IsFullScreen(false)
class TransformationActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_transformation_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        onTransformationStartContainer()
        super.onCreate(savedInstanceState)

        with(mainViewPager) {
            adapter = TransformationPagerAdapter(supportFragmentManager)
            offscreenPageLimit = 3
            addOnPageChangeListener(
                object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) = Unit
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) = Unit

                    override fun onPageSelected(position: Int) {
                        mainBottomNavigation.menu.getItem(position).isChecked = true
                    }
                }
            )
        }

        mainBottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.actionHome -> {
                    mainViewPager.currentItem = 0
                }
                R.id.actionLibray -> {
                    mainViewPager.currentItem = 1
                }
                R.id.actionRadio -> {
                    mainViewPager.currentItem = 2
                }
            }
            true
        }
    }
}
