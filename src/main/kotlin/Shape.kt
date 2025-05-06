package main.kotlin

/**
 * Shape class that represents a generic shape in 2D space.
 * This class serves as a base class for specific shapes like Circle, Rectangle, and Square.
 *
 * @property point The location of the shape.
 *
 * @constructor Creates a shape with the specified point.
 *
 * @author Simon Wessel
 * @version 2.0
 * @since 1.5
 */
abstract class Shape(point: Point) {

    /** Contains the location of the shape */
    private var location: Point
        get() = field
        set(value) {
            field = value
        }

    /**
     * Secondary constructor for Shape without parameters.
     * Initializes the location with a random point.
     */
    constructor() : this(Point())

    /**
     * Abstract method to calculate the area of the shape.
     *
     * @return The area of the shape.
     */
    abstract fun getArea(): Float

    /**
     * Abstract method to calculate the perimeter of the shape.
     *
     * @return The perimeter of the shape.
     */
    abstract override fun toString(): String
}
