package vn.loitp.app.activity.customviews.menu.drawerbehavior

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_drawer_behavior_main.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.menu.drawerbehavior.drawer.*

@LogTag("DrawerBehaviorMainActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class DrawerBehaviorMainActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_drawer_behavior_main
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
            this.tvTitle?.text = DrawerBehaviorMainActivity::class.java.simpleName
        }
        buttonDefault.setOnClickListener(this)
        buttonAdvance1.setOnClickListener(this)
        buttonAdvance2.setOnClickListener(this)
        buttonAdvance3.setOnClickListener(this)
        buttonAdvance4.setOnClickListener(this)
        buttonAdvance5.setOnClickListener(this)
        buttonAdvance6.setOnClickListener(this)
        buttonAdvance3d_1.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            buttonDefault -> startActivity(Intent(this, DefaultDrawerActivity::class.java))
            buttonAdvance1 -> startActivity(Intent(this, AdvanceDrawer1Activity::class.java))
            buttonAdvance2 -> startActivity(Intent(this, AdvanceDrawer2Activity::class.java))
            buttonAdvance3 -> startActivity(Intent(this, AdvanceDrawer3Activity::class.java))
            buttonAdvance4 -> startActivity(Intent(this, AdvanceDrawer4Activity::class.java))
            buttonAdvance5 -> startActivity(Intent(this, AdvanceDrawer5Activity::class.java))
            buttonAdvance6 -> startActivity(Intent(this, AdvanceDrawer6Activity::class.java))
            buttonAdvance3d_1 -> startActivity(Intent(this, Advance3DDrawer1Activity::class.java))
        }
        LActivityUtil.tranIn(this)
    }
}
