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
import de.fhkiel.oop.utils.FloatExtensions.formatAttribute2Value
import de.fhkiel.oop.utils.FloatExtensions.formatCoordinateValue
import de.fhkiel.oop.utils.FloatExtensions.formatStrokeWeightValue
import de.fhkiel.oop.utils.FloatExtensions.validateInRange
import de.fhkiel.oop.utils.RandomUtils.random
import processing.core.PApplet
import kotlin.Float

/**
 * A rectangle defined by its top-left corner, width and height.
 *
 * @property origin The top-left corner of the rectangle.
 * @property width  Width in units ∈ ([Float.MIN_VALUE]..[Config.MAX_RECT_WIDTH]).
 * @property height Height in units ∈ ([Float.MIN_VALUE]..[Config.MAX_RECT_HEIGHT]).
 *
 * @constructor Creates a [Rectangle] with given parameters.
 * Missing values default to random via [ClosedFloatingPointRange.random].
 *
 * @param originParam top-left point or random if omitted
 * @param widthParam  width or random ∈ ([Float.MIN_VALUE]..[Config.MAX_RECT_WIDTH]) if omitted
 * @param heightParam height or random ∈ ([Float.MIN_VALUE]..[Config.MAX_RECT_HEIGHT]) if omitted
 * @param styleParam  Initial style (random colours & weight by default).
 *
 * @see Style
 * @see Shape
 * @see Point
 * @see Config
 *
 * @author  Simon Wessel
 */
open class Rectangle(
    originParam: Point = Point(),
    widthParam:  Float = (Float.MIN_VALUE..Config.MAX_RECT_WIDTH).random(),
    heightParam: Float = (Float.MIN_VALUE..Config.MAX_RECT_HEIGHT).random(),
    styleParam:  Style = Style(),
    strategiesParam: ShapeStrategyConfig = ShapeStrategyConfig.RECTANGLE
) : BaseShape(originParam, styleParam) {

    /**
     * Backing field for width
     *
     * @throws IllegalArgumentException if outside the allowed range.
     */
    private var _width: Float = widthParam.validateInRange(
        "width",
        Float.MIN_VALUE,
        Config.MAX_RECT_WIDTH
    )

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
            _width = v.validateInRange(
                "width",
                Float.MIN_VALUE,
                Config.MAX_RECT_WIDTH
            )
        }

    /**
     * Backing field for height
     *
     * @throws IllegalArgumentException if outside the allowed range.
     */
    private var _height: Float = heightParam.validateInRange(
        "height",
        Float.MIN_VALUE,
        Config.MAX_RECT_HEIGHT
    )

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
            _height = v.validateInRange(
                "height",
                Float.MIN_VALUE,
                Config.MAX_RECT_HEIGHT
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
        fun fromArea(topLeft: Point, area: Float, length: Float): Rectangle =
            Rectangle(topLeft, area / length, length)
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
    override fun boundingBoxAt(candidateOrigin: Point): BoundingBox =
        BoundingBox(
            x      = candidateOrigin.x - style.weight / 2f,
            y      = candidateOrigin.y - style.weight / 2f,
            width  = width + style.weight,
            height = height + style.weight
        )

    /**
     * Checks if a point in screen coordinates hits the shape, including its stroke.
     *
     * @return `true` if the mouse coordinates hit the shape (fill or stroke), `false` otherwise.
     */
    override fun hitTestScreen(mapper: CoordinateMapper, mx: Float, my: Float): Boolean {
        val worldBox = mapper.worldBoundingBoxToScreen(boundingBoxAt(origin))

        val screenHalfStroke = mapper.worldScalarToScreen(style.weight / 2f)

        return mx >= worldBox.x - screenHalfStroke &&
                mx <= worldBox.x + worldBox.width + screenHalfStroke &&
                my >= worldBox.y - screenHalfStroke &&
                my <= worldBox.y + worldBox.height + screenHalfStroke
    }

    /**
     * Draws the circle on the given [PApplet] using the provided [CoordinateMapper].
     *
     * @param g      The PApplet to draw on.
     * @param mapper The coordinate mapper to use for drawing.
     */
    override fun draw(g: PApplet, mapper: CoordinateMapper) = withStyle(g) {
        val (x, y) = mapper.worldToScreen(this@Rectangle.origin.x, this@Rectangle.origin.y)
        val width  = mapper.worldScalarToScreen(this@Rectangle.width)
        val height = mapper.worldScalarToScreen(this@Rectangle.height)
        g.rect(x, y, width, height)
    }

    /**
     * Returns a string representation of the rectangle
     *
     * @return A string representation of the rectangle.
     */
    override fun toString(): String =
        buildString(
            listOf(
                Triple("Type",   this::class.simpleName!!,         Config.PAD_TYPE   to Config.PAD_TYPE_VAL),
                Triple("X",      origin.x.formatCoordinateValue(), Config.PAD_CORD   to Config.PAD_CORD_VAL),
                Triple("Y",      origin.y.formatCoordinateValue(), Config.PAD_CORD   to Config.PAD_CORD_VAL),
                Triple("Width",  width.formatAttribute1Value(),    Config.PAD_ATTR_1 to Config.PAD_ATTR_1_VAL),
                Triple("Height", height.formatAttribute2Value(),   Config.PAD_ATTR_2 to Config.PAD_ATTR_2_VAL),
                Triple("Area",   getArea().formatAreaValue(),      Config.PAD_AREA   to Config.PAD_AREA_VAL),

                Triple("Fill Color",    style.fill.toString(),
                    Config.PAD_FILL_COLR to Config.PAD_FILL_COLR_VAL),
                Triple("Stroke Color",  style.stroke.toString(),
                    Config.PAD_STRK_COLR to Config.PAD_STRK_COLR_VAL),
                Triple("Stroke Weight", style.weight.formatStrokeWeightValue(),
                    Config.PAD_STRK_WGHT to Config.PAD_STRK_WGHT_VAL)
            ))
}
