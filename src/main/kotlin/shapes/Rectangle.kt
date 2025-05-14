package de.fhkiel.oop.shapes

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.model.Point
import de.fhkiel.oop.model.Shape
import de.fhkiel.oop.utils.FloatExtensions.formatAreaValue
import de.fhkiel.oop.utils.FloatExtensions.formatAttribute1Value
import de.fhkiel.oop.utils.FloatExtensions.formatAttribute2Value
import de.fhkiel.oop.utils.FloatExtensions.formatCoordinateValue
import de.fhkiel.oop.utils.FloatExtensions.formatStrokeWeightValue
import de.fhkiel.oop.utils.RandomUtils.random
import processing.core.PApplet

/**
 * A rectangle defined by its top-left corner, width and height.
 *
 * @property origin The top-left corner of the rectangle.
 * @property width  Width in units ∈ (0f..[Config.MAX_RECT_WIDTH]).
 * @property height Height in units ∈ (0f..[Config.MAX_RECT_HEIGHT]).
 *
 * @constructor Creates a [Rectangle] with given parameters.
 * Missing values default to random via [ClosedFloatingPointRange.random].
 *
 * @param originParam top-left point or random if omitted
 * @param widthParam  width or random ∈ (0f..[Config.MAX_RECT_WIDTH]) if omitted
 * @param heightParam height or random ∈ (0f..[Config.MAX_RECT_HEIGHT]) if omitted
 *
 * @author  Simon Wessel
 * @version 2.3
 * @since   1.0
 */
class Rectangle(
    originParam: Point = Point(),
    widthParam:  Float = (0f..Config.MAX_RECT_WIDTH).random(),
    heightParam: Float = (0f..Config.MAX_RECT_HEIGHT).random()
) : Shape(originParam) {

    /** Backing field for width */
    private var _width: Float = widthParam

    /**
     * The width of the rectangle.
     *
     * @return the width.
     */
    var width: Float
        /** Returns the width of the rectangle. */
        get() = _width
        /** Sets the width of the rectangle. */
        set(v) {
            _width = v
        }

    /** Backing field for height */
    private var _height: Float = heightParam

    /**
     * The height of the rectangle.
     *
     * @return the height.
     */
    var height: Float
        /** Returns the height of the rectangle. */
        get() = _height
        /** Sets the height of the rectangle. */
        set(v) {
            _height = v
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
     * Renders the rectangle on the Processing canvas with configured visual attributes.
     *
     * 1. Saves current drawing style with [PApplet.pushStyle]
     * 2. Applies:
     *    - Fill color from [fillColor]
     *    - Stroke color from [strokeColor]
     *    - Stroke weight from [strokeWeight]
     * 3. Draws rectangle using Processing's [PApplet.rect] with:
     *    - Top-left corner at [origin.x], [origin.y]
     *    - Dimensions [width] x [height]
     * 4. Restores original style with [PApplet.popStyle]
     *
     * @param g Processing graphics context to draw on
     *
     * @see PApplet.rect
     * @see PApplet.pushStyle
     */
    override fun draw(g: PApplet) {
        g.pushStyle()
        g.fill(fillColor.red, fillColor.green, fillColor.blue, fillColor.alpha)
        g.stroke(strokeColor.red, strokeColor.green, strokeColor.blue, strokeColor.alpha)
        g.strokeWeight(strokeWeight)
        g.rect(origin.x, origin.y, width, height)
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

                Triple("Fill Color",    fillColor.toString(),
                    Config.PAD_FILL_COLR to Config.PAD_FILL_COLR_VAL),
                Triple("Stroke Color",  strokeColor.toString(),
                    Config.PAD_STRK_COLR to Config.PAD_STRK_COLR_VAL),
                Triple("Stroke Weight", strokeWeight.formatStrokeWeightValue(),
                    Config.PAD_STRK_WGHT to Config.PAD_STRK_WGHT_VAL)
            ))
}
