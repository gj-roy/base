package vn.loitp.a.cv.rv.carouselRv

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_rv_carousel.*
import vn.loitp.R

@LogTag("CarouselRecyclerViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class CarouselRecyclerViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_rv_carousel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/sparrow007/CarouselRecyclerview"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = CarouselRecyclerViewActivityFont::class.java.simpleName
        }

        val list = ArrayList<DataModel>()
        list.add(DataModel(R.drawable.loitp, "Thi is cool"))
        list.add(DataModel(R.drawable.logo, "Thi is cool"))
        list.add(DataModel(R.drawable.loitp, "Thi is cool"))
        list.add(DataModel(R.drawable.logo, "Thi is cool"))

        val adapter = DataAdapter(list)

        recycler.apply {
            this.adapter = adapter
            set3DItem(true)
            setAlpha(true)
            setInfinite(true)
            setIsScrollingEnabled(true)
        }

        button.setSafeOnClickListener {
            adapter.removeData()
        }
    }
}