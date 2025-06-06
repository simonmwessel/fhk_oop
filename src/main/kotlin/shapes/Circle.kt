package de.fhkiel.oop.shapes

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.config.ShapeStrategyConfig
import de.fhkiel.oop.mapper.CoordinateMapper
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.BoundingBox
import de.fhkiel.oop.model.Point
import de.fhkiel.oop.model.Shape
import de.fhkiel.oop.model.Style
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
    styleParam:  Style = Style(),
    strategiesParam: ShapeStrategyConfig = ShapeStrategyConfig.CIRCLE
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

    private var _strategies: ShapeStrategyConfig = strategiesParam

    /**
     * The strategies used for shape manipulation, such as move constraints.
     *
     * @return the strategies as [ShapeStrategyConfig].
     */
    override var strategies: ShapeStrategyConfig
        /** Returns the strategies of the circle. */
        get() = _strategies
        /** Sets the strategies of the circle. */
        set(v) { _strategies = v }

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
     * For a circle, the bounding box is a square whose sides are equal to the circle's diameter (2 * [radius]),
     * centered at the circle's [origin].
     */
    override fun boundingBoxAt(candidateOrigin: Point): BoundingBox =
        BoundingBox(
            x = candidateOrigin.x - radius,
            y = candidateOrigin.y - radius,
            width  = radius * 2,
            height = radius * 2
        )

    /**
     * Computes the screen bounding box of the circle based on the resize mode.
     *
     * @return The bounding box of the circle in screen coordinates.
     */
    override fun screenBoundingBox(mapper: CoordinateMapper): BoundingBox {
        val (screenCenterX, screenCenterY) = mapper.worldToScreen(origin.x, origin.y)
        val screenRadius = mapper.worldScalarToScreen(radius)
        return BoundingBox(
            screenCenterX - screenRadius,
            screenCenterY - screenRadius,
            screenRadius * 2,
            screenRadius * 2
        )
    }

    /**
     * Tests whether a point in screen coordinates hits the circle, including its stroke.
     *
     * @return `true` if the point hits the circle (fill or stroke), `false` otherwise.
     */
    override fun hitTestScreen(mapper: CoordinateMapper, mx: Float, my: Float): Boolean {
        val (screenCenterX, screenCenterY) = mapper.worldToScreen(origin.x, origin.y)
        val screenRadius = mapper.worldScalarToScreen(radius)
        val screenHalfStroke = mapper.worldScalarToScreen(style.weight / 2f)

        val distSq = (mx - screenCenterX).pow(2) + (my - screenCenterY).pow(2)
        val outerRadiusSq = (screenRadius + screenHalfStroke).pow(2)

        return distSq <= outerRadiusSq
    }

    /**
     * Draws the circle on the given [PApplet] using the provided [CoordinateMapper].
     *
     * @param g      The PApplet to draw on.
     * @param mapper The coordinate mapper to use for drawing.
     */
    override fun draw(g: PApplet, mapper: CoordinateMapper) = withStyle(g) {
        val (x, y) = mapper.worldToScreen(this@Circle.origin.x, this@Circle.origin.y)
        val radius = mapper.worldScalarToScreen(this@Circle.radius)
        ellipse(x, y, radius * 2, radius * 2)
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
