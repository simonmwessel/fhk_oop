package main.kotlin

import kotlin.random.Random

/**
 * Point class that represents a point in 2D space.
 *
 * @author
 * @version 1.0
 * @since 1.0
 */
class Point {

    /** Contains the x coordinate of the point */
    private var x: Float = 0.0f

    /** @return returns the x coordinate of the point */
    fun getX(): Float {
        return x
    }

    /** @param x sets the x coordinate of the point */
    fun setX(x: Float) {
        this.x = x
    }

    /** Contains the y coordinate of the point */
    private var y: Float = 0.0f

    /** @return returns the y coordinate of the point */
    fun getY(): Float {
        return y
    }

    /** @param y sets the y coordinate of the point */
    fun setY(y: Float) {
        this.y = y
    }

    /**
     * Default constructor for Point.
     * Initializes the point with random x and y coordinates between 0 and 100.
     */
    constructor() {
        this.x = Random.nextFloat() * 100
        this.y = Random.nextFloat() * 100
    }

    /**
     * Constructor for Point with specified x and y coordinates.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     */
    constructor(x: Float, y: Float) {
        this.x = x
        this.y = y
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
