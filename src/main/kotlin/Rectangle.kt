package main.kotlin

import kotlin.random.Random

/**
 * Rectangle class that represents a rectangle in 2D space.
 * This class extends the Shape class.
 *
 * @property topLeft The top left corner of the rectangle.
 * @property width The width of the rectangle.
 * @property height The height of the rectangle.
 *
 * @constructor Creates a rectangle with the specified top left corner, width, and height.
 *
 * @author Simon Wessel
 * @version 2.0
 * @since 1.0
 */
class Rectangle(topLeft: Point, width: Float, height: Float) : Shape(topLeft) {

    /**
     * Contains the width of the rectangle.
     */
    var width: Float = 0.0f
        get() = field
        set(value) {
            field = value
        }

    /**
     * Contains the height of the rectangle.
     */
    var height: Float = 0.0f
        get() = field
        set(value) {
            field = value
        }

    /**
     * Secondary constructor for Rectangle without parameters.
     * Initializes the top left corner with a random point and sets width and height with random values.
     */
    constructor() : this(Point(), Random.nextFloat() * 20, Random.nextFloat() * 20)

    companion object {
        /**
         * Static method to create a Rectangle from a given area and length.
         *
         * @param topLeft The top left corner of the rectangle.
         * @param area The area of the rectangle.
         * @param length The length of one side of the rectangle.
         *
         * @return A new Rectangle object with the specified area and length.
         */
        fun fromArea(topLeft: Point, area: Float, length: Float): Rectangle {
            return Rectangle(topLeft, area / length, length)
        }
    }

    /**
     * Calculates the area of the rectangle.
     *
     * @return The area of the rectangle.
     */
    override fun getArea(): Float {
        return width * height
    }

    /**
     * Returns a string representation of the rectangle in the following format:
     * "Type       |      X |      Y |     Width  |      Height |     Area"
     *
     * @return A string representation of the rectangle.
     */
    override fun toString(): String {
        return String.format(
            "{ %-15s | x: %8.2f | y: %8.2f | Width: %11.2f | Area: %10.2f | Height: %10.2f }",
            "Type: Rectangle", getLocation().getX(), getLocation().getY(), width, getArea(), height
        )
    }
}
