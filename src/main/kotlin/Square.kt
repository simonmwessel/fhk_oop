package main.kotlin

import kotlin.math.sqrt
import kotlin.random.Random

/**
 * Square class that represents a square in 2D space.
 *
 * @author Simon Wessel
 * @version 2.0
 * @since 1.0
 */
class Square : Shape {

    /** Contains the side length of the square */
    private var sideLength: Float = 0.0f

    /** @return returns the side length of the square */
    fun getSideLength(): Float {
        return sideLength
    }

    /** @param sideLength sets the side length of the square */
    fun setSideLength(sideLength: Float) {
        this.sideLength = sideLength
    }

    /**
     * Default constructor for Square.
     * Initializes the top left corner with a random point and side length with a random value between 0 and 10.
     */
    constructor() : super(Point()) {
        sideLength = Random.nextFloat() * 10
    }

    /**
     * Constructor for Square with specified top left corner and side length.
     *
     * @param topLeft The top left corner of the square.
     * @param side The side length of the square.
     */
    constructor(topLeft: Point, side: Float) : super(topLeft) {
        sideLength = side
    }

    companion object {
        /**
         * Static method to create a Square from a given area.
         *
         * @param topLeft The top left corner of the square.
         * @param area The area of the square.
         * @return A new Square object with the specified area.
         */
        fun fromArea(topLeft: Point, area: Float): Square {
            return Square(topLeft, sqrt(area.toDouble()).toFloat())
        }
    }

    /**
     * Calculates the area of the square.
     *
     * @return The area of the square.
     */
    override fun getArea(): Float {
        return sideLength * sideLength
    }

    /**
     * Returns a string representation of the square in the following format:
     * "Type       |      X |      Y |     Side   |            |     Area"
     *
     * @return A string representation of the square.
     */
    override fun toString(): String {
        return String.format(
            "{ %-15s | x: %8.2f | y: %8.2f | Side: %12.2f | Area: %10.2f }",
            "Type: Square", getLocation().getX(), getLocation().getY(), sideLength, getArea()
        )
    }
}
