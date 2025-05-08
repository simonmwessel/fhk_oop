package main.kotlin

import utils.FloatExtensions.formatAreaValue
import utils.FloatExtensions.formatAttribute1Value
import utils.FloatExtensions.formatAttribute2Value
import utils.FloatExtensions.formatCoordinateValue
import kotlin.random.Random

/**
 * Rectangle class that represents a rectangle in 2D space.
 *
 * @author  Simon Wessel
 * @version 2.2
 * @since   1.0
 */
class Rectangle(topLeft: Point, widthParam: Float, heightParam: Float) : Shape(topLeft) {

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

    /**
     * Default constructor for Rectangle.
     * Initializes the top left corner with a random point and sets width and height with random values.
     */
    constructor() : this(
        Point(),
        Random.nextFloat() * 20f,
        Random.nextFloat() * 20f
    )

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
     * Returns a string representation of the rectangle
     *
     * @return A string representation of the rectangle.
     */
    override fun toString(): String =
        buildString(
            listOf(
                Triple("Type",   this::class.simpleName!!,           Config.PAD_TYPE   to Config.PAD_TYPE_VAL),
                Triple("X",      location.x.formatCoordinateValue(), Config.PAD_CORD   to Config.PAD_CORD_VAL),
                Triple("Y",      location.y.formatCoordinateValue(), Config.PAD_CORD   to Config.PAD_CORD_VAL),
                Triple("Width",  width.formatAttribute1Value(),      Config.PAD_ATTR_1 to Config.PAD_ATTR_1_VAL),
                Triple("Height", height.formatAttribute2Value(),     Config.PAD_ATTR_2 to Config.PAD_ATTR_2_VAL),
                Triple("Area",   getArea().formatAreaValue(),        Config.PAD_AREA   to Config.PAD_AREA_VAL)
            ))
}
