package vn.loitp.a.cv.bt.circularProgress

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import kotlinx.android.synthetic.main.l_cpb_sample_4.*
import vn.loitp.R

@LogTag("Sample4Activity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class Sample4ActivityFont : BaseActivityFont() {
    override fun setLayoutResourceId(): Int {
        return R.layout.l_cpb_sample_4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        circularButton1.isIndeterminateProgressMode = true
        circularButton1.setOnClickListener {
            when (circularButton1.progress) {
                0 -> {
                    circularButton1.progress = 50
                }
                100 -> {
                    circularButton1.progress = 0
                }
                else -> {
                    circularButton1.progress = 100
                }
            }
        }
        circularButton2.isIndeterminateProgressMode = true
        circularButton2.setOnClickListener {
            if (circularButton2.progress == 0) {
                circularButton2.progress = 50
            } else if (circularButton2.progress == -1) {
                circularButton2.progress = 0
            } else {
                circularButton2.progress = -1
            }
        }
    }

}