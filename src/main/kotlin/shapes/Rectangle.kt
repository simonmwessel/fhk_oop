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
import de.fhkiel.oop.utils.FloatExtensions.formatAttribute2Value
import de.fhkiel.oop.utils.FloatExtensions.formatCoordinateValue
import de.fhkiel.oop.utils.FloatExtensions.formatStrokeWeightValue
import de.fhkiel.oop.utils.RandomUtils.random
import processing.core.PApplet
import kotlin.Float

/**
 * A rectangle defined by its top-left corner, width and height.
 *
 * @property origin The top-left corner of the rectangle.
 * @property width  Width in units ∈ ([de.fhkiel.oop.config.DefaultConfig.minRectWidth]..[de.fhkiel.oop.config.DefaultConfig.maxRectWidth]).
 * @property height Height in units ∈ ([de.fhkiel.oop.config.DefaultConfig.minRectHeight]..[de.fhkiel.oop.config.DefaultConfig.maxRectHeight]).
 *
 * @constructor Creates a [Rectangle] with given parameters.
 * Missing values default to random via [ClosedFloatingPointRange.random].
 *
 * @param originParam top-left vector or random if omitted
 * @param widthParam  width or random ∈ ([de.fhkiel.oop.config.DefaultConfig.minRectWidth]..[de.fhkiel.oop.config.DefaultConfig.maxRectWidth]) if omitted
 * @param heightParam height or random ∈ ([de.fhkiel.oop.config.DefaultConfig.minRectHeight]..[de.fhkiel.oop.config.DefaultConfig.maxRectHeight]) if omitted
 * @param styleParam  Initial style (random colours & weight by default).
 *
 * @see Style
 * @see Shape
 * @see Vector2D
 * @see AppConfig
 *
 * @author  Simon Wessel
 */
open class Rectangle(
    config:      AppConfig = DefaultConfig,
    originParam: Vector2D  = Vector2D(),
    widthParam:  Float = (config.minRectWidth..config.maxRectWidth).random(),
    heightParam: Float = (config.minRectWidth..config.maxRectWidth).random(),
    styleParam:  Style = Style(),
    strategiesParam: ShapeStrategyConfig = ShapeStrategyConfig.RECTANGLE
) : BaseShape(config, originParam, styleParam) {

    /**
     * Backing field for width
     *
     * @throws IllegalArgumentException if outside the allowed range.
     */
    private var _width: Float = widthParam

    /**
     * The width of the rectangle.
     *
     * @return the width.
     */
    open var width: Float
        /** Returns the width of the rectangle. */
        get() = _width
        /**
         * Sets the width of the rectangle.
         *
         * @throws IllegalArgumentException if outside the allowed range.
         */
        set(v) {
            _width = v
        }

    /**
     * Backing field for height
     *
     * @throws IllegalArgumentException if outside the allowed range.
     */
    private var _height: Float = heightParam

    /**
     * The height of the rectangle.
     *
     * @return the height.
     */
    open var height: Float
        /** Returns the height of the rectangle. */
        get() = _height
        /**
         * Sets the height of the rectangle.
         *
         * @throws IllegalArgumentException if outside the allowed range.
         */
        set(v) {
            _height = v
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
     * Companion object for [Rectangle] providing factory methods.
     */
    companion object {
        /**
         * Static method to create a Rectangle from a given area and length.
         *
         * @param topLeft The top left corner of the rectangle.
         * @param area    The area of the rectangle.
         * @param length  The length of one side of the rectangle.
         *
         * @return A new Rectangle object with the specified area and length.
         */
        fun fromArea(topLeft: Vector2D, area: Float, length: Float, config: AppConfig = DefaultConfig): Rectangle =
            Rectangle(config, topLeft, area / length, length)
    }

    /**
     * Calculates the area of the rectangle.
     *
     * @return The area of the rectangle.
     */
    override fun getArea(): Float = width * height

    /**
     * {@inheritDoc}
     *
     * For a rectangle, the geometric box is `(width × height)` at [candidateOrigin].
     * We then expand by half the stroke weight on each side to include the border.
     *
     * @param candidateOrigin The top-left corner candidate.
     * @return A [BoundingBox] including the stroke border.
     * @see BaseShape.boundingBoxAt
     */
    override fun boundingBoxAt(candidateOrigin: Vector2D): BoundingBox =
        BoundingBox(
            origin = candidateOrigin,
            width  = width,
            height = height
        )

    /**
     * {@inheritDoc}
     *
     * Maps the rectangle's bounding box to screen coordinates,
     * including the stroke width as a margin.
     *
     * @param mapper The coordinate mapper to use for conversion.
     * @param candidateOrigin The top-left corner candidate in world coordinates.
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
        val worldBox = boundingBoxAt(candidateOrigin)
        val originPx = mapper.worldToScreen(worldBox.origin)
        val wPx      = mapper.worldScalarToScreen(worldBox.width)
        val hPx      = mapper.worldScalarToScreen(worldBox.height)

        val strokePx = mapper.worldScalarToScreen(style.weight)
        val marginPx = strokePx / 2f

        return BoundingBox(
            origin = Vector2D(config, originPx.x - marginPx, originPx.y - marginPx),
            width  = wPx     + strokePx,
            height = hPx     + strokePx
        )
    }

    /**
     * Checks if a vector in screen coordinates hits the shape, including its stroke.
     *
     * @return `true` if the mouse coordinates hit the shape (fill or stroke), `false` otherwise.
     */
    override fun hitTestScreen(mapper: CoordinateMapper, mx: Float, my: Float): Boolean {
        val worldBox         = screenBoundingBoxAt(mapper, origin)
        val screenHalfStroke = mapper.worldScalarToScreen(style.weight / 2f)

        return mx >= worldBox.origin.x - screenHalfStroke &&
               mx <= worldBox.origin.x + worldBox.width + screenHalfStroke &&
               my >= worldBox.origin.y - screenHalfStroke &&
               my <= worldBox.origin.y + worldBox.height + screenHalfStroke
    }

    /**
     * Draws the circle on the given [PApplet] using the provided [CoordinateMapper].
     *
     * @param g      The PApplet to draw on.
     * @param mapper The coordinate mapper to use for drawing.
     */
    override fun draw(g: PApplet, mapper: CoordinateMapper) = withStyle(g, mapper) {
        val vector = mapper.worldToScreen(this@Rectangle.origin)
        val width  = mapper.worldScalarToScreen(this@Rectangle.width)
        val height = mapper.worldScalarToScreen(this@Rectangle.height)
        g.rect(vector.x, vector.y, width, height)

        super.draw(g, mapper)
    }

    /**
     * Returns a string representation of the rectangle
     *
     * @return A string representation of the rectangle.
     */
    override fun toString(): String =
        buildString(
            listOf(
                Triple("Type",   this::class.simpleName!!,         config.padType  to config.padTypeVal),
                Triple("X",      origin.x.formatCoordinateValue(), config.padCord  to config.padCordVal),
                Triple("Y",      origin.y.formatCoordinateValue(), config.padCord  to config.padCordVal),
                Triple("Width",  width.formatAttribute1Value(),    config.padAttr1 to config.padAttr1Val),
                Triple("Height", height.formatAttribute2Value(),   config.padAttr2 to config.padAttr2Val),
                Triple("Area",   getArea().formatAreaValue(),      config.padArea  to config.padAreaVal),

                Triple("Fill Color",    style.fill.toString(),
                    config.padFillColr to config.padFillColrVal),
                Triple("Stroke Color",  style.stroke.toString(),
                    config.padStrkColr to config.padStrkColrVal),
                Triple("Stroke Weight", style.weight.formatStrokeWeightValue(),
                    config.padStrkWght to config.padStrkWghtVal),
            ))
}
