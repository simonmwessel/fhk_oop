package main.kotlin

import kotlin.random.Random

/**
 * Rectangle class that represents a rectangle in 2D space.
 *
 * @author Simon Wessel
 * @version 2.1
 * @since 1.0
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
        set(v) { _width = v }

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
        set(v) { _height = v }

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
         * @param area The area of the rectangle.
         * @param length The length of one side of the rectangle.
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
     * Returns a string representation of the rectangle in the following format:
     * "Type       |      X |      Y |     Width  |      Height |     Area"
     *
     * @return A string representation of the rectangle.
     */
    override fun toString(): String =
        String.format(
            "{ %-15s | x: %8.2f | y: %8.2f | Width: %11.2f | Area: %10.2f | Height: %10.2f }",
            "Type: Rectangle", location.x, location.y, width, getArea(), height
        )
}
