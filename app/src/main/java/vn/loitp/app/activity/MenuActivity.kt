package vn.loitp.app.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.IsKeepScreenOn
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.helper.adHelper.AdHelperActivity
import com.loitpcore.core.utilities.*
import kotlinx.android.synthetic.main.activity_firebase.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_menu.lActionBar
import vn.loitp.app.R
import vn.loitp.app.activity.animation.MenuAnimationActivity
import vn.loitp.app.activity.api.MenuAPIActivity
import vn.loitp.app.activity.customviews.MenuCustomViewsActivity
import vn.loitp.app.activity.database.MenuDatabaseActivity
import vn.loitp.app.activity.demo.MenuDemoActivity
import vn.loitp.app.activity.function.MenuFunctionActivity
import vn.loitp.app.activity.game.MenuGameActivity
import vn.loitp.app.activity.interviewVN.InterviewVNIQActivity
import vn.loitp.app.activity.more.MoreActivity
import vn.loitp.app.activity.network.NetworkActivity
import vn.loitp.app.activity.pattern.MenuPatternActivity
import vn.loitp.app.activity.picker.MenuPickerActivity
import vn.loitp.app.activity.security.MenuSecurityActivity
import vn.loitp.app.activity.service.MenuServiceActivity
import vn.loitp.app.activity.tutorial.MenuTutorialActivity
import vn.loitp.app.activity.utillsCore.UtilsCoreActivity
import vn.loitp.app.activity.utils.UtilsActivity

@LogTag("MenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
@IsKeepScreenOn(true)
class MenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupConfigGoogle()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuActivity::class.java.simpleName
        }

        tvPolicy.apply {
            LUIUtil.setTextUnderline(this)
            setSafeOnClickListener {
                LSocialUtil.openUrlInBrowser(
                    context = this@MenuActivity, url = Constants.URL_POLICY
                )
            }
        }

        swDarkTheme.apply {
            isChecked = LUIUtil.isDarkTheme()
            setOnCheckedChangeListener { _, isDarkTheme ->
                if (isDarkTheme) {
                    LUIUtil.setDarkTheme(isDarkTheme = true)
                } else {
                    LUIUtil.setDarkTheme(isDarkTheme = false)
                }
                finish()//correct
                startActivity(Intent(this@MenuActivity, MenuActivity::class.java))
                overridePendingTransition(0, 0)
            }
        }

        btApi.setOnClickListener(this)
        btAnimation.setOnClickListener(this)
        btCustomView.setOnClickListener(this)
        btDemo.setOnClickListener(this)
        btFunction.setOnClickListener(this)
        btRateApp.setOnClickListener(this)
        btMoreApp.setOnClickListener(this)
        btDatabase.setOnClickListener(this)
        btPattern.setOnClickListener(this)
        btChat.setOnClickListener(this)
        btGithub.setOnClickListener(this)
        btAdHelper.setOnClickListener(this)
        btFbFanpage.setOnClickListener(this)
        btFrmMore.setOnClickListener(this)
        btTutorial.setOnClickListener(this)
        btPicker.setOnClickListener(this)
        btNetwork.setOnClickListener(this)
        btSecurity.setOnClickListener(this)
        btService.setOnClickListener(this)
        btUtils.setOnClickListener(this)
        btUtilsCore.setOnClickListener(this)
        btGame.setOnClickListener(this)
        btFeedback.setOnClickListener(this)
        btInterviewVNIQActivity.setOnClickListener(this)
    }

    private fun setupConfigGoogle() {
        val app = LPrefUtil.getGGAppSetting()
//        logD(">>>setupConfigGoogle " + BaseApplication.gson.toJson(app))
        val isFullData = app.config?.isFullData == true
//        logD(">>>setupConfigGoogle isFullData $isFullData")
        if (isFullData) {
            btApi.isVisible = true
            btAnimation.isVisible = true
            btCustomView.isVisible = true
            btDemo.isVisible = true
            btFunction.isVisible = true
            btRateApp.isVisible = true
            btMoreApp.isVisible = true
            btDatabase.isVisible = true
            btPattern.isVisible = true
            btChat.isVisible = true
            btGithub.isVisible = true
            btAdHelper.isVisible = true
            btFbFanpage.isVisible = true
            btFrmMore.isVisible = true
            btTutorial.isVisible = true
            btPicker.isVisible = true
            btNetwork.isVisible = true
            btSecurity.isVisible = true
            btService.isVisible = true
            btUtils.isVisible = true
        } else {
            btApi.isVisible = true
            btAnimation.isVisible = false
            btCustomView.isVisible = false
            btDemo.isVisible = false
            btFunction.isVisible = false
            btRateApp.isVisible = true
            btMoreApp.isVisible = true
            btDatabase.isVisible = false
            btPattern.isVisible = false
            btChat.isVisible = false
            btGithub.isVisible = false
            btAdHelper.isVisible = false
            btFbFanpage.isVisible = false
            btFrmMore.isVisible = false
            btTutorial.isVisible = false
            btPicker.isVisible = false
            btNetwork.isVisible = true
            btSecurity.isVisible = false
            btService.isVisible = false
            btUtils.isVisible = false
        }
    }

    private var doubleBackToExitPressedOnce = false

    override fun onBaseBackPressed() {
//        super.onBaseBackPressed()
        if (doubleBackToExitPressedOnce) {
//            onBaseBackPressed()
            super.onBaseBackPressed()//correct
            return
        }
        this.doubleBackToExitPressedOnce = true
        showSnackBarInfor(msg = getString(R.string.press_again_to_exit), isFullWidth = false)
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun onClick(v: View) {
        when (v) {
            btApi -> launchActivity(MenuAPIActivity::class.java)
            btAnimation -> launchActivity(MenuAnimationActivity::class.java)
            btCustomView -> launchActivity(MenuCustomViewsActivity::class.java)
            btDemo -> launchActivity(MenuDemoActivity::class.java)
            btRateApp -> LSocialUtil.rateApp(this, packageName)
            btMoreApp -> LSocialUtil.moreApp(this)
            btFunction -> launchActivity(MenuFunctionActivity::class.java)
            btGame -> launchActivity(MenuGameActivity::class.java)
            btDatabase -> launchActivity(MenuDatabaseActivity::class.java)
            btPattern -> launchActivity(MenuPatternActivity::class.java)
            btChat -> LSocialUtil.chatMessenger(this)
            btGithub -> {
                LSocialUtil.openUrlInBrowser(
                    context = this, url = "https://github.com/tplloi/base"
                )
            }
            btAdHelper -> {
                launchActivity(cls = AdHelperActivity::class.java, data = {
                    it.putExtra(Constants.AD_HELPER_IS_ENGLISH_LANGUAGE, true)
                })
            }
            btFbFanpage -> LSocialUtil.likeFacebookFanpage(this)
            btFrmMore -> launchActivity(MoreActivity::class.java)
            btTutorial -> launchActivity(MenuTutorialActivity::class.java)
            btPicker -> launchActivity(MenuPickerActivity::class.java)
            btNetwork -> launchActivity(NetworkActivity::class.java)
            btSecurity -> launchActivity(MenuSecurityActivity::class.java)
            btService -> launchActivity(MenuServiceActivity::class.java)
            btUtils -> launchActivity(UtilsActivity::class.java)
            btUtilsCore -> launchActivity(UtilsCoreActivity::class.java)
            btFeedback -> {
                LSocialUtil.sendEmail(
                    activity = this,
                    to = "roy93group@gmail.com",
                    cc = "roy93group@gmail.com",
                    bcc = "roy93group@gmail.com",
                    subject = "Feedback",
                    body = "..."
                )
            }
            btInterviewVNIQActivity -> launchActivity(InterviewVNIQActivity::class.java)
        }
    }
}
