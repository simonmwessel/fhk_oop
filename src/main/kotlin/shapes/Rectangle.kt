package de.fhkiel.oop.shapes

import de.fhkiel.oop.config.Config
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
 * @version 2.7
 * @since   1.0
 */
class Rectangle(
    originParam: Point = Point(),
    widthParam:  Float = (Float.MIN_VALUE..Config.MAX_RECT_WIDTH).random(),
    heightParam: Float = (Float.MIN_VALUE..Config.MAX_RECT_HEIGHT).random(),
    styleParam:  Style = Style()
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
    var width: Float
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
    var height: Float
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
     * For a rectangle, this checks if the [point]'s x-coordinate is within the rectangle's x-range
     * (from [origin].x to [origin].x + [width]) and the y-coordinate is within the rectangle's y-range
     * (from [origin].y to [origin].y + [height]).
     */
    override fun contains(point: Point): Boolean =
        point.x in origin.x..(origin.x + width)
     && point.y in origin.y..(origin.y + height)

    /**
     * {@inheritDoc}
     *
     * For a rectangle, the bounding box is the rectangle itself, defined by its [origin], [width], and [height].
     */
    override fun boundingBox(): BoundingBox =
        BoundingBox(origin.x, origin.y, width, height)

    /**
     * Draws the rectangle with uniform scaling - entire scene grows/shrinks
     * equally while keeping center position.
     *
     * @param g Processing graphics context
     *
     * @see Shape.drawUniform
     */
    override fun drawUniform(g: PApplet) = withStyle(g) {
        g.rect(origin.x, origin.y, this@Rectangle.width, this@Rectangle.height)
    }

    /**
     * Draws the rectangle with relative positioning - scales position and
     * dimensions independently to fill available space.
     *
     * @param g            Processing graphics context
     * @param scaleX       Horizontal scaling factor (windowWidth / baseWidth)
     * @param scaleY       Vertical scaling factor (windowHeight / baseHeight)
     * @param uniformScale Ignored for rectangles, but required for interface compatibility.
     *
     * @see Shape.drawRelative
     */
    override fun drawRelative(
        g: PApplet,
        scaleX: Float,
        scaleY: Float,
        uniformScale: Float
    ) = withStyle(g) {
        val x = origin.x * scaleX
        val y = origin.y * scaleY
        val w = this@Rectangle.width  * uniformScale
        val h = this@Rectangle.height * uniformScale

        rect(x, y, w, h)
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
