package main.kotlin

import kotlin.random.Random

/**
 * Point class that represents a point in 2D space.
 *
 * @property x The x coordinate of the point.
 * @property y The y coordinate of the point.
 *
 * @constructor Creates a point with the specified x and y coordinates.
 *
 * @author
 * @version 2.2
 * @since 1.0
 */
class Point(xParam: Float, yParam: Float) {

    /** Backing field for x coordinate */
    private var _x: Float = xParam

    /** Backing field for y coordinate */
    private var _y: Float = yParam

    /**
     * The x coordinate of the point.
     *
     * @return the x coordinate.
     */
    var x: Float
        /** Returns the x coordinate of the point. */
        get() = _x
        /** Sets the x coordinate of the point. */
        set(value) { _x = value }

    /**
     * The y coordinate of the point.
     *
     * @return the y coordinate.
     */
    var y: Float
        /** Returns the y coordinate of the point. */
        get() = _y
        /** Sets the y coordinate of the point. */
        set(value) { _y = value }

    /**
     * Secondary constructor for Point without parameters.
     *
     * Initializes the point with random x and y coordinates between 0 and 100.
     */
    constructor() : this(
        Random.nextFloat() * 100f,
        Random.nextFloat() * 100f
    )

    /**
     * Returns a string representation of the point.
     *
     * @return a string representation of the point in the format "(   x.xx |   y.yy)".
     */
    override fun toString(): String = String.format("x: %6.2f, y: %6.2f", x, y)
}
