package de.fhkiel.oop.shapes

import de.fhkiel.oop.Sketch
import de.fhkiel.oop.config.Config
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.BoundingBox
import de.fhkiel.oop.model.Point
import de.fhkiel.oop.model.Shape
import de.fhkiel.oop.model.Style
import de.fhkiel.oop.model.distanceTo
import de.fhkiel.oop.utils.FloatExtensions.formatAreaValue
import de.fhkiel.oop.utils.FloatExtensions.formatAttribute1Value
import de.fhkiel.oop.utils.FloatExtensions.formatCoordinateValue
import de.fhkiel.oop.utils.FloatExtensions.formatStrokeWeightValue
import de.fhkiel.oop.utils.FloatExtensions.validateInRange
import de.fhkiel.oop.utils.RandomUtils.random
import processing.core.PApplet
import kotlin.Float
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * A circle defined by a center point and a radius.
 *
 * @property origin The center of the circle.
 * @property radius Radius length in units ∈ ([Float.MIN_VALUE]..[Config.MAX_CIRCLE_RADIUS]).
 *
 * @constructor Creates a [Circle] with given parameters.
 * Missing values default to random via [ClosedFloatingPointRange.random].
 *
 * @param originParam center point or random if omitted
 * @param radiusParam radius or random ∈ ([Float.MIN_VALUE]..[Config.MAX_CIRCLE_RADIUS]) if omitted
 * @param styleParam  Initial style (random colours & weight by default).
 *
 * @see Style
 * @see Shape
 * @see Point
 * @see Config
 *
 * @author  Simon Wessel
 * @version 2.7
 * @since   1.0
 */
class Circle(
    originParam: Point = Point(),
    radiusParam: Float = (Float.MIN_VALUE..Config.MAX_CIRCLE_RADIUS).random(),
    styleParam:  Style = Style()
)  : BaseShape(originParam, styleParam) {

    /**
     * Backing field for radius
     *
     * @throws IllegalArgumentException if outside the allowed range.
     */
    private var _radius: Float = radiusParam.validateInRange(
        "radius",
        Float.MIN_VALUE,
        Config.MAX_CIRCLE_RADIUS
    )

    /**
     * The radius of the circle.
     *
     * @return the radius.
     */
    var radius: Float
        /** Returns the radius of the circle. */
        get() = _radius
        /**
         * Sets the radius of the circle.
         *
         * @throws IllegalArgumentException if outside the allowed range.
         */
        set(v) {
            _radius = v.validateInRange(
                "radius",
                Float.MIN_VALUE,
                Config.MAX_CIRCLE_RADIUS
            )
        }

    /**
     * Companion object for [Circle] providing factory methods.
     */
    companion object {
        /**
         * Static method to create a Circle from a given area.
         *
         * @param center The center of the circle.
         * @param area   The area of the circle.
         *
         * @return A new Circle object with the specified area.
         */
        fun fromArea(center: Point, area: Float): Circle =
            Circle(center, sqrt((area / Math.PI)).toFloat())
    }

    /**
     * Calculates the area of the circle.
     *
     * @return The area of the circle.
     */
    override fun getArea(): Float = (Math.PI * radius.pow(2)).toFloat()

    /**
     * {@inheritDoc}
     *
     * For a circle, this checks if the distance from the [point] to the circle's [origin] is less than or equal to its [radius].
     */
    override fun contains(point: Point): Boolean =
        point.distanceTo(origin) <= radius

    /**
     * {@inheritDoc}
     *
     * For a circle, the bounding box is a square whose sides are equal to the circle's diameter (2 * [radius]),
     * centered at the circle's [origin].
     */
    override fun boundingBox(): BoundingBox =
        BoundingBox(
            x = origin.x - radius,
            y = origin.y - radius,
            width  = radius * 2,
            height = radius * 2
        )

    /**
     * Computes the screen bounding box of the circle based on the resize mode.
     *
     * @param mode The resize mode (UNIFORM_SCALE or RELATIVE).
     * @param sx   Horizontal scaling factor (windowWidth / baseWidth).
     * @param sy   Vertical scaling factor (windowHeight / baseHeight).
     * @param us   Unified scaling factor for circles/squares (min(windowScaleX, windowScaleY)).
     * @param offX Horizontal offset for the bounding box.
     * @param offY Vertical offset for the bounding box.
     *
     * @return The bounding box of the circle in screen coordinates.
     */
    override fun screenBoundingBox(
        mode : Sketch.ResizeMode,
        sx   : Float,
        sy   : Float,
        us   : Float,
        offX : Float,
        offY : Float
    ): BoundingBox = when (mode) {
        Sketch.ResizeMode.UNIFORM_SCALE ->
            super.screenBoundingBox(mode, sx, sy, us, offX, offY)

        Sketch.ResizeMode.RELATIVE -> {
            val cx = origin.x * sx
            val cy = origin.y * sy
            val r  = radius     * us
            BoundingBox(cx - r, cy - r, r * 2, r * 2)
        }
    }

    /**
     * Draws the circle with uniform scaling - maintains perfect roundness
     * while centering the entire composition.
     *
     * @param g Processing graphics context
     *
     * @see Shape.drawUniform
     */
    override fun drawUniform(g: PApplet) = withStyle(g) {
        ellipse(origin.x, origin.y, radius * 2, radius * 2)
    }

    /**
     * Draws the circle with relative positioning - scales position coordinates
     * while maintaining perfect roundness through [uniformScale].
     *
     * @param g            Processing graphics context
     * @param scaleX       Horizontal scaling factor (windowWidth / baseWidth)
     * @param scaleY       Vertical scaling factor (windowHeight / baseHeight)
     * @param uniformScale Unified scaling factor for circles/squares (min(windowScaleX, windowScaleY))
     *
     * @see Shape.drawRelative
     */
    override fun drawRelative(
        g: PApplet,
        scaleX: Float,
        scaleY: Float,
        uniformScale: Float
    ) = withStyle(g) {
        val cx = origin.x * scaleX
        val cy = origin.y * scaleY
        val r  = radius     * uniformScale

        ellipse(cx, cy, r * 2, r * 2)
    }

    /**
     * Returns a string representation of the Circle
     *
     * @return A string representation of the Circle.
     */
    override fun toString(): String =
        buildString(
            listOf(
                Triple("Type",   this::class.simpleName!!,         Config.PAD_TYPE   to Config.PAD_TYPE_VAL),
                Triple("X",      origin.x.formatCoordinateValue(), Config.PAD_CORD   to Config.PAD_CORD_VAL),
                Triple("Y",      origin.y.formatCoordinateValue(), Config.PAD_CORD   to Config.PAD_CORD_VAL),
                Triple("Radius", radius.formatAttribute1Value(),   Config.PAD_ATTR_1 to Config.PAD_ATTR_1_VAL),
                Triple("",       "",                               (Config.PAD_ATTR_2 + Config.SEPARATOR_KEY_VALUE.length + Config.PAD_ATTR_2_VAL) to 0),
                Triple("Area",   getArea().formatAreaValue(),      Config.PAD_AREA   to Config.PAD_AREA_VAL),

                Triple("Fill Color",    style.fill.toString(),
                    Config.PAD_FILL_COLR to Config.PAD_FILL_COLR_VAL),
                Triple("Stroke Color",  style.stroke.toString(),
                    Config.PAD_STRK_COLR to Config.PAD_STRK_COLR_VAL),
                Triple("Stroke Weight", style.weight.formatStrokeWeightValue(),
                    Config.PAD_STRK_WGHT to Config.PAD_STRK_WGHT_VAL)
            ))
}
