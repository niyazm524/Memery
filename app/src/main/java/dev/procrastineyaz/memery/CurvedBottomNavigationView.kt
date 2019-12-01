package dev.procrastineyaz.memery

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.PathShape
import android.util.AttributeSet
import androidx.core.content.res.use
import com.google.android.material.bottomnavigation.BottomNavigationView


class CurvedBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    BottomNavigationView(context, attrs) {
    private val path = Path()
    private val paint = Paint()

    /** the CURVE_CIRCLE_RADIUS represent the radius of the fab button  */
    private var fabRadius: Float = 56f * context.resources.displayMetrics.density
    private var backgroundShapeColor: Int = Color.WHITE
    // the coordinates of the first curve
    private val firstCurveStart = PointF()
    private val firstCurveEnd = PointF()
    private val firstCurveControl1 = PointF()
    private val firstCurveControl2 = PointF()

    //the coordinates of the second curve
    private var secondCurveStart = PointF()
    private val secondCurveEnd = PointF()
    private val secondCurveControl1 = PointF()
    private val secondCurveControl2 = PointF()

    private var navBarWidth = 0
    private var navBarHeight = 0

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.CurvedBottomNavigationView,
            defStyle,
            0
        ).use {
            fabRadius = it.getDimensionPixelSize(
                R.styleable.CurvedBottomNavigationView_fab_size,
                fabRadius.toInt()
            ).toFloat()
            fabRadius /= 2
            backgroundShapeColor =
                it.getColor(R.styleable.CurvedBottomNavigationView_backgroundColor, Color.WHITE)
        }
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = Color.WHITE
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        navBarWidth = width
        navBarHeight = height
        val halfWidth = w / 2f
        firstCurveStart.set(halfWidth - 1.5f * fabRadius, 0f)
        secondCurveEnd.set(halfWidth + 1.5f * fabRadius, 0f)
        firstCurveEnd.set(halfWidth, fabRadius * 1.15f)
        secondCurveStart = firstCurveEnd
        firstCurveControl1.set(firstCurveStart.x + fabRadius / 2, firstCurveStart.y)
        firstCurveControl2.set(firstCurveEnd.x - fabRadius, firstCurveEnd.y)
        secondCurveControl1.set(secondCurveStart.x + fabRadius, secondCurveStart.y)
        secondCurveControl2.set(secondCurveEnd.x - fabRadius / 2, secondCurveEnd.y)

        path.apply {
            reset()
            moveTo(0f, 0f)
            lineTo(firstCurveStart.x, firstCurveStart.y)
            cubicTo(
                firstCurveControl1.x, firstCurveControl1.y,
                firstCurveControl2.x, firstCurveControl2.y,
                firstCurveEnd.x, firstCurveEnd.y
            )
            cubicTo(
                secondCurveControl1.x, secondCurveControl1.y,
                secondCurveControl2.x, secondCurveControl2.y,
                secondCurveEnd.x, secondCurveEnd.y
            )
            lineTo(navBarWidth.toFloat(), 0f)
            lineTo(navBarWidth.toFloat(), navBarHeight.toFloat())
            lineTo(0f, navBarHeight.toFloat())
            close()
        }
        val shapeDrawable = ShapeDrawable(PathShape(path, w.toFloat(), h.toFloat()))
        shapeDrawable.colorFilter =
            PorterDuffColorFilter(backgroundShapeColor, PorterDuff.Mode.SRC_IN)
        background = shapeDrawable
    }
}
