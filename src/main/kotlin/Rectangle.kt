package main.kotlin

import kotlin.random.Random

/**
 * Rectangle class that represents a rectangle in 2D space.
 *
 * @author Simon Wessel
 * @version 2.0
 * @since 1.0
 */
class Rectangle : Shape {

    /** Contains the width of the rectangle */
    private var width: Float = 0.0f

    /** @return returns the width of the rectangle */
    fun getWidth(): Float {
        return width
    }

    /** @param width sets the width of the rectangle */
    fun setWidth(width: Float) {
        this.width = width
    }

    /** Contains the height of the rectangle */
    private var height: Float = 0.0f

    /** @return returns the height of the rectangle */
    fun getHeight(): Float {
        return height
    }

    /** @param height sets the height of the rectangle */
    fun setHeight(height: Float) {
        this.height = height
    }

    /**
     * Default constructor for Rectangle.
     * Initializes the top left corner with a random point and sets width and height with random values.
     */
    constructor() : super(Point()) {
        width = Random.nextFloat() * 20
        height = Random.nextFloat() * 20
    }

    /**
     * Constructor for Rectangle with specified top left corner, width, and height.
     *
     * @param topLeft The top left corner of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     */
    constructor(topLeft: Point, width: Float, height: Float) : super(topLeft) {
        this.width = width
        this.height = height
    }

    companion object {
        /**
         * Static method to create a Rectangle from a given area and length.
         *
         * @param topLeft The top left corner of the rectangle.
         * @param area The area of the rectangle.
         * @param length The length of one side of the rectangle.
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
