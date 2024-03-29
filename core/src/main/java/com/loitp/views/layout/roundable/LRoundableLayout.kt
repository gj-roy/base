package com.loitp.views.layout.roundable

import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.constraintlayout.widget.ConstraintLayout
import com.loitp.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LRoundableLayout : ConstraintLayout {

    private var path: Path? = null

    /** corner radius */
    private var cornerLeftTop: Float = 0F
    private var cornerRightTop: Float = 0F
    private var cornerLeftBottom: Float = 0F
    private var cornerRightBottom: Float = 0F

    /** side opt is top and bottom */
    private var cornerLeftSide: Float = 0F
    private var cornerRightSide: Float = 0F

    /** background color */
    private var backgroundColor: Int? = null

    /** stroke */
    private var strokeWidth: Int = 0
    private var strokeColor: Int? = null
    private var dashGap: Float = 0F
    private var dashWidth: Float = 0F

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        render(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        render(attrs)
    }

    constructor(context: Context) : super(context) {
        render(null)
    }

    private fun render(attrs: AttributeSet?) {
        attrs?.let {

            /** set corner radii */
            context.obtainStyledAttributes(it, R.styleable.LRoundableLayout).apply {
                cornerLeftTop =
                    this.getDimensionPixelSize(R.styleable.LRoundableLayout_lrl_cornerLeftTop, 0)
                        .toFloat()
                cornerRightTop =
                    this.getDimensionPixelSize(R.styleable.LRoundableLayout_lrl_cornerRightTop, 0)
                        .toFloat()
                cornerLeftBottom =
                    this.getDimensionPixelSize(R.styleable.LRoundableLayout_lrl_cornerLeftBottom, 0)
                        .toFloat()
                cornerRightBottom = this.getDimensionPixelSize(
                    R.styleable.LRoundableLayout_lrl_cornerRightBottom,
                    0
                ).toFloat()
                backgroundColor =
                    this.getColor(R.styleable.LRoundableLayout_lrl_backgroundColor, Color.WHITE)
                strokeWidth =
                    this.getDimensionPixelSize(R.styleable.LRoundableLayout_lrl_strokeLineWidth, 0)
                strokeColor =
                    this.getColor(R.styleable.LRoundableLayout_lrl_strokeLineColor, Color.BLACK)
                dashWidth =
                    this.getDimensionPixelSize(R.styleable.LRoundableLayout_lrl_dashLineWidth, 0)
                        .toFloat()
                dashGap =
                    this.getDimensionPixelSize(R.styleable.LRoundableLayout_lrl_dashLineGap, 0)
                        .toFloat()
                cornerLeftSide =
                    this.getDimensionPixelSize(R.styleable.LRoundableLayout_lrl_cornerLeftSide, 0)
                        .toFloat()
                cornerRightSide =
                    this.getDimensionPixelSize(R.styleable.LRoundableLayout_lrl_cornerRightSide, 0)
                        .toFloat()
            }.run {
                this.recycle()
            }
        }
    }

    /**
     * if left side value existed,
     * leftTop and leftBottom value is equal to leftSide value.
     * this is made in consideration of the custom attribute of motion layout.
     * because Constraint only has maximum two custom attribute. (2.0.0-beta2)
     */
    private fun checkSideValue() {
        if (cornerLeftSide != 0F) {
            cornerLeftTop = cornerLeftSide
            cornerLeftBottom = cornerLeftSide
        }

        if (cornerRightSide != 0F) {
            cornerRightTop = cornerRightSide
            cornerRightBottom = cornerRightSide
        }
    }

    @Suppress("unused")
    fun setCornerLeftTop(value: Float) {
        cornerLeftTop = value
        postInvalidate()
    }

    @Suppress("unused")
    fun setCornerLeftBottom(value: Float) {
        cornerLeftBottom = value
        postInvalidate()
    }

    @Suppress("unused")
    fun setCornerRightTop(value: Float) {
        cornerRightTop = value
        postInvalidate()
    }

    @Suppress("unused")
    fun setCornerRightBottom(value: Float) {
        cornerRightBottom = value
        postInvalidate()
    }

    @Suppress("unused")
    fun setCornerLeftSide(value: Float) {
        cornerLeftSide = value
        postInvalidate()
    }

    @Suppress("unused")
    fun setCornerRightSide(value: Float) {
        cornerRightSide = value
        postInvalidate()
    }

    @Suppress("unused")
    fun setStrokeLineWidth(value: Int) {
        strokeWidth = value
        postInvalidate()
    }

    @Suppress("unused")
    fun setStrokeLineColor(value: Int) {
        strokeColor = value
        postInvalidate()
    }

    @Suppress("unused")
    fun setDashLineWidth(value: Float) {
        dashWidth = value
        postInvalidate()
    }

    @Suppress("unused")
    fun setDashLineGap(value: Float) {
        dashGap = value
        postInvalidate()
    }

    @Suppress("unused")
    override fun setBackgroundColor(value: Int) {
        backgroundColor = value
        postInvalidate()
    }

    override fun dispatchDraw(canvas: Canvas) {
        /** for outline remake whenenver draw */
        path = null

        if (path == null) {
            path = Path()
        }

        checkSideValue()

        floatArrayOf(
            cornerLeftTop, cornerLeftTop, cornerRightTop, cornerRightTop, cornerRightBottom,
            cornerRightBottom, cornerLeftBottom, cornerLeftBottom
        ).apply {
            clipPathCanvas(canvas, this)
        }

        /** set drawable resource corner & background & stroke */
        GradientDrawable().apply {
            this.cornerRadii = floatArrayOf(
                cornerLeftTop, cornerLeftTop, cornerRightTop, cornerRightTop,
                cornerRightBottom, cornerRightBottom, cornerLeftBottom, cornerLeftBottom
            )

            if (strokeWidth != 0 && strokeColor != null)
                this.setStroke(strokeWidth, strokeColor!!, dashWidth, dashGap)

            backgroundColor?.let {
                /** set background color */
                this.setColor(it)
            } ?: this.setColor(Color.WHITE)

            /** set background color default : WHITE */
            background = this
        }

        outlineProvider = outlineProvider

        clipChildren = false

        super.dispatchDraw(canvas)
    }

    private fun clipPathCanvas(
        canvas: Canvas,
        floatArray: FloatArray
    ) {
        path?.let {
            it.addRoundRect(
                RectF(0F, 0F, canvas.width.toFloat(), canvas.height.toFloat()),
                floatArray,
                Path.Direction.CW
            )
            canvas.clipPath(it)
        }
    }

    /** For not showing red underline */
    override fun setOutlineProvider(provider: ViewOutlineProvider?) {
        super.setOutlineProvider(provider)
    }

    /** For not showing red underline */
    @Suppress("unused")
    override fun setElevation(elevation: Float) {
        super.setElevation(elevation)
    }

    /** For not showing red underline */
    @Suppress("unused")
    override fun setTranslationZ(translationZ: Float) {
        super.setTranslationZ(translationZ)
    }

    override fun getOutlineProvider(): ViewOutlineProvider {
        return object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                path?.let {
                    outline.setConvexPath(it)
                } ?: throw Exception()
            }
        }
    }
}
