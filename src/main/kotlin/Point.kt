package main.kotlin

import utils.FloatExtensions.formatCoordinateValue
import kotlin.random.Random

/**
 * Point class that represents a point in 2D space.
 *
 * @property x The x coordinate of the point.
 * @property y The y coordinate of the point.
 *
 * @constructor Creates a point with the specified x and y coordinates.
 *
 * @author  Simon Wessel
 * @version 2.1
 * @since   1.0
 */
class Point(xParam: Float, yParam: Float) {

    /** Backing field for x coordinate */
    private var _x: Float = xParam

    /**
     * The x coordinate of the point.
     *
     * @return the x coordinate.
     */
    var x: Float
        /** Returns the x coordinate of the point. */
        get() = _x
        /** Sets the x coordinate of the point. */
        set(v) { _x = v }

    /** Backing field for y coordinate */
    private var _y: Float = yParam

    /**
     * The y coordinate of the point.
     *
     * @return the y coordinate.
     */
    var y: Float
        /** Returns the y coordinate of the point. */
        get() = _y
        /** Sets the y coordinate of the point. */
        set(v) { _y = v }

    /**
     * Secondary constructor for Point without parameters.
     *
     * Initializes the point with random x and y coordinates between 0 and 100.
     */
    constructor() : this(
        Random.nextFloat() * Config.MAX_X,
        Random.nextFloat() * Config.MAX_Y
    )

    /**
     * Returns a string representation of the point.
     *
     * @return a string representation of the point in the format "{ X:  x.xx | Y:  y.yy }".
     */
    override fun toString(): String =
        Config.PREFIX +
        listOf(
            "X" to x.formatCoordinateValue(),
            "Y" to y.formatCoordinateValue()
        ).joinToString(Config.SEPARATOR) { (k, v) ->
            k + Config.SEPARATOR_KEY_VALUE + v
        } +
        Config.SUFFIX
}
