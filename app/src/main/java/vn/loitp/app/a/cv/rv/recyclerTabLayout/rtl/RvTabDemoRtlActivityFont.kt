package vn.loitp.app.a.cv.rv.recyclerTabLayout.rtl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.tranIn
import kotlinx.android.synthetic.main.activity_recycler_tablayout_demo_rtl.*
import vn.loitp.R
import vn.loitp.app.a.cv.rv.recyclerTabLayout.Demo
import vn.loitp.app.a.cv.rv.recyclerTabLayout.DemoColorPagerAdapter
import vn.loitp.app.a.cv.rv.recyclerTabLayout.basic.RvTabDemoBasicActivityFont
import vn.loitp.app.a.cv.rv.recyclerTabLayout.utils.DemoData

@LogTag("RvTabDemoRtlActivity")
@IsFullScreen(false)
class RvTabDemoRtlActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_tablayout_demo_rtl
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val keyDemo = intent.getStringExtra(RvTabDemoBasicActivityFont.KEY_DEMO)
        if (keyDemo.isNullOrEmpty()) {
            onBaseBackPressed()
            return
        }
        val demo = Demo.valueOf(keyDemo)

        toolbar.setTitle(demo.titleResId)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val items = DemoData.loadDemoColorItems(this)
        items.sortedWith { lhs, rhs -> lhs.name.compareTo(rhs.name) }

        val adapter = DemoColorPagerAdapter()
        adapter.addAll(items)
        viewPager.adapter = adapter

        recyclerTabLayout.setUpWithViewPager(viewPager)
    }

    companion object {

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, RvTabDemoRtlActivityFont::class.java)
            intent.putExtra(RvTabDemoBasicActivityFont.KEY_DEMO, demo.name)
            context.startActivity(intent)
            context.tranIn()
        }
    }
}
