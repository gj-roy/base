package vn.loitp.a.sec

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_sec_menu.*
import vn.loitp.R
import vn.loitp.a.sec.simple.SimpleEncryptDecryptStringActivity

import vn.loitp.a.sec.ssBiometricsAuthentication.SSBiometricsAuthenticationActivity

@LogTag("SecurityMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuSecurityActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sec_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuSecurityActivity::class.java.simpleName
        }
        bt0.setSafeOnClickListener {
            launchActivity(SimpleEncryptDecryptStringActivity::class.java)
        }
        btSSBiometricsAuthenticationActivity.setSafeOnClickListener {
            launchActivity(SSBiometricsAuthenticationActivity::class.java)
        }
    }
}