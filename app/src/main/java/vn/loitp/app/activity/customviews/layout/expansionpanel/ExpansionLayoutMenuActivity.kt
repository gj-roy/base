package vn.loitp.app.activity.customviews.layout.expansionpanel

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_expansion_panel_menu.*
import loitp.basemaster.R

//https://github.com/florent37/ExpansionPanel
class ExpansionLayoutMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btSample.setOnClickListener(this)
        btSampleViewgroup.setOnClickListener(this)
        btProgrammatically.setOnClickListener(this)
//        findViewById<View>(R.id.recyclerView).setOnClickListener { startActivity(Intent(activity, ExpansionPanelSampleActivityRecycler::class.java)) }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_expansion_panel_menu
    }

    override fun onClick(v: View?) {
        var intent: Intent? = null
        when (v) {
            btSample -> {
                intent = Intent(activity, ExpansionPanelSampleActivity::class.java)
            }
            btSampleViewgroup -> {
                intent = Intent(activity, ExpansionPanelSampleActivityViewGroup::class.java)
            }
            btProgrammatically -> {
                intent = Intent(activity, ExpansionPanelSampleActivityProgrammatically::class.java)
            }
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
