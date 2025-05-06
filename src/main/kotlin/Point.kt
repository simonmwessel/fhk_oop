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
 * @version 2.1
 * @since 1.0
 */
class Point(x: Float, y: Float) {

    /**
     * Contains the x coordinate of the point
     */
    private var x: Float = 0.0f
        get() = field
        set(value) {
            field = value
        }

    /**
     * Contains the y coordinate of the point
     */
    private var y: Float = 0.0f
        get() = field
        set(value) {
            field = value
        }

    /**
     * Secondary constructor for Point without parameters.
     *
     * Initializes the point with random x and y coordinates between 0 and 100.
     */
    constructor() {
        x = Random.nextFloat() * 100
        y = Random.nextFloat() * 100
    }

    /**
     * Returns a string representation of the point.
     *
     * @return a string representation of the point in the format "(   x.xx |   y.yy)".
     */
    override fun toString(): String {
        return String.format("x: %6.2f, y: %6.2f", x, y)
    }
}
