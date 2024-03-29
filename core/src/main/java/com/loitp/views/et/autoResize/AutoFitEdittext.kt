package com.loitp.views.et.autoResize

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatEditText
import com.loitp.views.et.autoResize.AutoFitHelper.Companion.create

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AutoFitEdittext : AppCompatEditText, AutoFitHelper.OnTextSizeChangeListener {
    /**
     * Returns the [AutoFitHelper] for this View.
     */
    var autoFitHelper: AutoFitHelper? = null
        private set

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs, defStyle)
    }

    @Suppress("unused")
    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) {
        autoFitHelper = create(this, attrs, defStyle)
            .addOnTextSizeChangeListener(this)
    }
    // Getters and Setters
    /**
     * {@inheritDoc}
     */
    override fun setTextSize(
        unit: Int,
        size: Float
    ) {
        super.setTextSize(unit, size)

        autoFitHelper?.setTextSize(unit = unit, size = size)
    }

    /**
     * {@inheritDoc}
     */
    override fun setLines(lines: Int) {
        super.setLines(lines)

        autoFitHelper?.setMaxLines(lines = lines)
    }

    /**
     * {@inheritDoc}
     */
    override fun setMaxLines(maxLines: Int) {
        super.setMaxLines(maxLines)

        autoFitHelper?.setMaxLines(lines = maxLines)
    }
    /**
     * Returns whether or not the text will be automatically re-sized to fit its constraints.
     */
    /**
     * If true, the text will automatically be re-sized to fit its constraints; if false, it will
     * act like a normal TextView.
     *
     * param sizeToFit
     */
    var isSizeToFit: Boolean?
        get() = autoFitHelper?.isEnabled
        set(sizeToFit) {
            autoFitHelper?.setEnabled(enabled = sizeToFit)
        }

    /**
     * Sets the property of this field (sizeToFit), to automatically resize the text to fit its
     * constraints.
     */
    @Suppress("unused")
    fun setSizeToFit() {
        isSizeToFit = true
    }
    /**
     * Returns the maximum size (in pixels) of the text in this View.
     */
    /**
     * Set the maximum text size to the given value, interpreted as "scaled pixel" units. This size
     * is adjusted based on the current density and user font size preference.
     *
     * param size The scaled pixel size.
     * @attr ref android.R.styleable#TextView_textSize
     */
    @Suppress("unused")
    var maxTextSize: Float?
        get() = autoFitHelper?.maxTextSize
        set(size) {
            size?.let {
                autoFitHelper?.setMaxTextSize(size = it)
            }
        }

    /**
     * Set the maximum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     * @attr ref android.R.styleable#TextView_textSize
     */
    @Suppress("unused")
    fun setMaxTextSize(unit: Int, size: Float) {
        autoFitHelper?.setMaxTextSize(unit = unit, size = size)
    }

    /**
     * Returns the minimum size (in pixels) of the text in this View.
     */
    val minTextSize: Float?
        get() = autoFitHelper?.minTextSize

    /**
     * Set the minimum text size to the given value, interpreted as "scaled pixel" units. This size
     * is adjusted based on the current density and user font size preference.
     *
     * @param minSize The scaled pixel size.
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    @Suppress("unused")
    fun setMinTextSize(minSize: Int) {
        autoFitHelper?.setMinTextSize(unit = TypedValue.COMPLEX_UNIT_SP, size = minSize.toFloat())
    }

    /**
     * Set the minimum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit    The desired dimension unit.
     * @param minSize The desired size in the given units.
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    @Suppress("unused")
    fun setMinTextSize(unit: Int, minSize: Float) {
        autoFitHelper?.setMinTextSize(unit = unit, size = minSize)
    }
    /**
     * Returns the amount of precision used to calculate the correct text size to fit within its
     * bounds.
     */
    /**
     * Set the amount of precision used to calculate the correct text size to fit within its
     * bounds. Lower precision is more precise and takes more time.
     *
     * param precision The amount of precision.
     */
    @Suppress("unused")
    var precision: Float?
        get() = autoFitHelper?.precision
        set(precision) {
            precision?.let {
                autoFitHelper?.setPrecision(it)
            }
        }

    override fun onTextSizeChange(
        textSize: Float,
        oldTextSize: Float
    ) {
        // do nothing
    }
}
