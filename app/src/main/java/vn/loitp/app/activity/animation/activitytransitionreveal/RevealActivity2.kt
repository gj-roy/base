package vn.loitp.app.activity.animation.activitytransitionreveal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.IsSwipeActivity
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.tombayley.activitycircularreveal.CircularReveal
import kotlinx.android.synthetic.main.activity_reveal_2.*
import vn.loitp.app.R

@LogTag("OtherActivity")
@IsFullScreen(false)
@IsSwipeActivity(true)
class RevealActivity2 : BaseFontActivity() {

    companion object {
        const val KEY_DATA_RETURN = "KEY_DATA_RETURN"
    }

    private var activityCircularReveal: CircularReveal? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_reveal_2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityCircularReveal = CircularReveal(rootView)
        activityCircularReveal?.onActivityCreate(intent)
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(KEY_DATA_RETURN, "data " + System.currentTimeMillis())
        setResult(Activity.RESULT_OK, intent)
        activityCircularReveal?.unRevealActivity(this)
    }
}
