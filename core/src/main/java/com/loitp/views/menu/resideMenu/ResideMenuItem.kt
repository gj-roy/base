package com.loitp.views.menu.resideMenu

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.loitp.R
import com.loitp.core.ext.setTextShadow
import com.loitp.core.ext.setTextSizePx
import com.loitp.core.utils.ConvertUtils

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ResideMenuItem : LinearLayout {
    @Suppress("unused")
    var ivIcon: ImageView? = null
    var tvTitle: TextView? = null

    constructor(context: Context) : super(context) {
        initViews(context)
    }

    @Suppress("unused")
    constructor(
        context: Context,
        icon: Int,
        title: Int
    ) : super(context) {
        initViews(context)

        ivIcon?.setImageResource(icon)
        tvTitle?.setText(title)
    }

    constructor(
        context: Context,
        icon: Int,
        title: String?
    ) : super(context) {
        initViews(context)

        ivIcon?.setImageResource(icon)
        tvTitle?.text = title
    }

    private fun initViews(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.l_v_reside_menu_item, this)

        ivIcon = findViewById(R.id.ivIcon)
        tvTitle = findViewById(R.id.tvTitle)
    }

    /**
     * set the icon color;
     *
     */
    fun setIcon(icon: Int) {
        ivIcon?.setImageResource(icon)
    }

    /**
     * set the title with resource
     * ;
     *
     */
    fun setTitle(title: Int) {
        tvTitle?.setText(title)
    }

    /**
     * set the title with string;
     *
     */
    fun setTitle(title: String?) {
        tvTitle?.text = title
    }

    fun setTextColor(color: Int) {
        tvTitle?.setTextColor(color)
    }

    fun setTextSize(size: Float) {
        tvTitle.setTextSizePx(size = size)
    }

    fun setTextShadow(color: Int) {
        tvTitle?.setTextShadow(color = color)
    }

    @Suppress("unused")
    fun setIvIconSizePx(sizeInPx: Int) {
        ivIcon?.apply {
            layoutParams.width = sizeInPx
            layoutParams.height = sizeInPx
            requestLayout()
        }
    }

    fun setIvIconSizeDp(sizeInDp: Int) {
        ivIcon?.apply {
            layoutParams.width = ConvertUtils.dp2px(sizeInDp.toFloat())
            layoutParams.height = ConvertUtils.dp2px(sizeInDp.toFloat())
            requestLayout()
        }
    }
}
