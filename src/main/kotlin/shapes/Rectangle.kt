package de.fhkiel.oop.shapes

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.model.BaseShape
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
 * @version 2.6
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
     * Draws the rectangle with uniform scaling - entire scene grows/shrinks
     * equally while keeping center position.
     *
     * @param g Processing graphics context
     *
     * @see Shape.drawUniform
     */
    override fun drawUniform(g: PApplet) {
        g.pushStyle()
        g.fill(style.fill.red, style.fill.green, style.fill.blue, style.fill.alpha)
        g.stroke(style.stroke.red, style.stroke.green, style.stroke.blue, style.stroke.alpha)
        g.strokeWeight(style.weight)
        g.rect(origin.x, origin.y, width, height)
        g.popStyle()
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
    ) {
        val x = origin.x * scaleX
        val y = origin.y * scaleY
        val w = width  * uniformScale
        val h = height * uniformScale

        g.pushStyle()
        g.fill(style.fill.red, style.fill.green, style.fill.blue, style.fill.alpha)
        g.stroke(style.stroke.red, style.stroke.green, style.stroke.blue, style.stroke.alpha)
        g.strokeWeight(style.weight)
        g.rect(x, y, w, h)
        g.popStyle()
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
