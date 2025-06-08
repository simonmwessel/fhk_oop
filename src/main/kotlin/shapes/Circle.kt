package de.fhkiel.oop.shapes

import de.fhkiel.oop.config.AppConfig
import de.fhkiel.oop.config.DefaultConfig
import de.fhkiel.oop.config.ShapeStrategyConfig
import de.fhkiel.oop.mapper.CoordinateMapper
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.BoundingBox
import de.fhkiel.oop.model.Vector2D
import de.fhkiel.oop.model.Shape
import de.fhkiel.oop.model.Style
import de.fhkiel.oop.utils.FloatExtensions.formatAreaValue
import de.fhkiel.oop.utils.FloatExtensions.formatAttribute1Value
import de.fhkiel.oop.utils.FloatExtensions.formatCoordinateValue
import de.fhkiel.oop.utils.FloatExtensions.formatStrokeWeightValue
import de.fhkiel.oop.utils.RandomUtils.random
import processing.core.PApplet
import kotlin.Float
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * A circle defined by a center vector and a radius.
 *
 * @property origin The center of the circle.
 * @property radius Radius length in units ∈ ([de.fhkiel.oop.config.DefaultConfig.minCircleRadius]..[de.fhkiel.oop.config.DefaultConfig.maxCircleRadius]).
 *
 * @constructor Creates a [Circle] with given parameters.
 * Missing values default to random via [ClosedFloatingPointRange.random].
 *
 * @param originParam center vector or random if omitted
 * @param radiusParam radius or random ∈ ([de.fhkiel.oop.config.DefaultConfig.minCircleRadius]..[de.fhkiel.oop.config.DefaultConfig.maxCircleRadius]) if omitted
 * @param styleParam  Initial style (random colours & weight by default).
 *
 * @see Style
 * @see Shape
 * @see Vector2D
 * @see AppConfig
 * @see DefaultConfig
 *
 * @author  Simon Wessel
 * @version 2.7
 * @since   1.0
 */
class Circle(
    config: AppConfig = DefaultConfig,
    originParam: Vector2D = Vector2D(),
    radiusParam: Float = (config.minCircleRadius..config.maxCircleRadius).random(),
    styleParam:  Style = Style(),
    strategiesParam: ShapeStrategyConfig = ShapeStrategyConfig.CIRCLE
)  : BaseShape(config, originParam, styleParam) {

    /**
     * Backing field for radius
     *
     * @throws IllegalArgumentException if outside the allowed range.
     */
    private var _radius: Float = radiusParam

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
            _radius = v
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
        fun fromArea(center: Vector2D, area: Float, config: AppConfig = DefaultConfig): Circle =
            Circle(config, center, sqrt((area / Math.PI)).toFloat())
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
     * For a circle, the geometric box is a square of side `2 * radius`
     * centered at [candidateOrigin]. We then expand by half the stroke weight
     * on each side to include the border.
     *
     * @param candidateOrigin The hypothetical center vector.
     * @return A [BoundingBox] including the stroke border.
     * @see BaseShape.boundingBoxAt
     */
    override fun boundingBoxAt(candidateOrigin: Vector2D): BoundingBox =
        BoundingBox(
            origin = Vector2D(
                config,
                candidateOrigin.x - radius,
                candidateOrigin.y - radius),
            width  = radius * 2,
            height = radius * 2
        )

    /**
     * {@inheritDoc}
     *
     * Maps the circle's bounding box to screen coordinates.
     * The bounding box is centered at the screen position of the circle's origin,
     * with a width and height of `2 * radius + stroke weight`.
     * The stroke weight is added as a margin on each side to include the border.
     *
     * @param mapper The coordinate mapper to use for conversion.
     * @param candidateOrigin The hypothetical center vector in world coordinates.
     *
     * @return A [BoundingBox] in screen coordinates that includes the stroke margin.
     *
     * @see BaseShape.screenBoundingBoxAt
     * @see CoordinateMapper.worldToScreen
     * @see CoordinateMapper.worldScalarToScreen
     * @see BoundingBox
     * @see Vector2D
     */
    override fun screenBoundingBoxAt(
        mapper: CoordinateMapper,
        candidateOrigin: Vector2D
    ): BoundingBox {
        val centrePx = mapper.worldToScreen(candidateOrigin)
        val radiusPx = mapper.worldScalarToScreen(radius)
        val marginPx = mapper.worldScalarToScreen(style.weight / 2f)

        return BoundingBox(
            origin = Vector2D(
                config,
                centrePx.x - radiusPx - marginPx,
                centrePx.y - radiusPx - marginPx
            ),
            width  = radiusPx * 2f + marginPx * 2f,
            height = radiusPx * 2f + marginPx * 2f
        )
    }

    /**
     * Tests whether a vector in screen coordinates hits the circle, including its stroke.
     *
     * @return `true` if the vector hits the circle (fill or stroke), `false` otherwise.
     */
    override fun hitTestScreen(mapper: CoordinateMapper, mx: Float, my: Float): Boolean {
        val screenCenterVector = mapper.worldToScreen(origin)
        val screenRadius       = mapper.worldScalarToScreen(radius)
        val screenHalfStroke   = mapper.worldScalarToScreen(style.weight / 2f)

        val distSq        = (mx - screenCenterVector.x).pow(2) + (my - screenCenterVector.y).pow(2)
        val outerRadiusSq = (screenRadius + screenHalfStroke).pow(2)

        return distSq <= outerRadiusSq
    }

    /**
     * Draws the circle on the given [PApplet] using the provided [CoordinateMapper].
     *
     * @param g      The PApplet to draw on.
     * @param mapper The coordinate mapper to use for drawing.
     */
    override fun draw(g: PApplet, mapper: CoordinateMapper) = withStyle(g, mapper) {
        val vector = mapper.worldToScreen(this@Circle.origin)
        val radius = mapper.worldScalarToScreen(this@Circle.radius)
        ellipse(vector.x, vector.y, radius * 2, radius * 2)

        super.draw(g, mapper)
    }

    /**
     * Returns a string representation of the Circle
     *
     * @return A string representation of the Circle.
     */
    override fun toString(): String =
        buildString(
            listOf(
                Triple("Type",   this::class.simpleName!!,         config.padType   to config.padTypeVal),
                Triple("X",      origin.x.formatCoordinateValue(), config.padCord   to config.padCordVal),
                Triple("Y",      origin.y.formatCoordinateValue(), config.padCord   to config.padCordVal),
                Triple("Radius", radius.formatAttribute1Value(),   config.padAttr1  to config.padAttr1Val),
                Triple("",       "",                               (config.padAttr2 + config.separatorKeyValue.length + config.padAttr2Val) to 0),
                Triple("Area",   getArea().formatAreaValue(),      config.padArea   to config.padAreaVal),

                Triple("Fill Color",    style.fill.toString(),
                    config.padFillColr to config.padFillColrVal),
                Triple("Stroke Color",  style.stroke.toString(),
                    config.padStrkColr to config.padStrkColrVal),
                Triple("Stroke Weight", style.weight.formatStrokeWeightValue(),
                    config.padStrkWght to config.padStrkWghtVal),
            ))
}
