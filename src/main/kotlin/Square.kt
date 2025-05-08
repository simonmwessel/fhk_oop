package main.kotlin

import utils.FloatExtensions.formatAreaValue
import utils.FloatExtensions.formatAttribute1Value
import utils.FloatExtensions.formatCoordinateValue
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * Square class that represents a square in 2D space.
 * This class extends the Shape class.
 *
 * @property topLeft    The top left corner of the square.
 * @property sideLength The length of one side of the square.
 *
 * @constructor Creates a square with the specified top left corner and side length.
 *
 * @author  Simon Wessel
 * @version 2.2
 * @since   1.0
 */
class Square(topLeft: Point, sideParam: Float) : Shape(topLeft) {

    /** Backing field for side length */
    private var _sideLength: Float = sideParam

    /**
     * The side length of the square.
     *
     * @return the side length.
     */
    var sideLength: Float
        /** Returns the side length of the square. */
        get() = _sideLength
        /** Sets the side length of the square. */
        set(v) {
            _sideLength = v
        }

    /**
     * Secondary constructor for Square without parameters.
     * Initializes the top left corner with a random point and side length with a random value between 0 and 10.
     */
    constructor() : this(Point(), Random.nextFloat() * 10)

    companion object {
        /**
         * Static method to create a Square from a given area.
         *
         * @param topLeft The top left corner of the square.
         * @param area    The area of the square.
         *
         * @return A new Square object with the specified area.
         */
        fun fromArea(topLeft: Point, area: Float): Square =
            Square(topLeft, sqrt(area.toDouble()).toFloat())
    }

    /**
     * Calculates the area of the square.
     *
     * @return The area of the square.
     */
    override fun getArea(): Float = sideLength * sideLength

    /**
     * Returns a string representation of the square
     *
     * @return A string representation of the square.
     */
    override fun toString(): String =
        buildString(
            listOf(
                Triple(  "Type", this::class.simpleName!!,           Config.PAD_TYPE   to Config.PAD_TYPE_VAL),
                Triple(  "X",    location.x.formatCoordinateValue(), Config.PAD_CORD   to Config.PAD_CORD_VAL),
                Triple(  "Y",    location.y.formatCoordinateValue(), Config.PAD_CORD   to Config.PAD_CORD_VAL),
                Triple(  "Side", sideLength.formatAttribute1Value(), Config.PAD_ATTR_1 to Config.PAD_ATTR_1_VAL),
                Triple(  "",     "",                                 (Config.PAD_ATTR_2 + Config.SEPARATOR_KEY_VALUE.length + Config.PAD_ATTR_2_VAL) to 0),
                Triple(  "Area", getArea().formatAreaValue(),        Config.PAD_AREA   to Config.PAD_AREA_VAL)
            ))
}
