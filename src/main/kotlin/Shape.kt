package main.kotlin

/**
 * Shape class that represents a generic shape in 2D space.
 * This class serves as a base class for specific shapes like Circle, Rectangle, and Square.
 *
 * @property location The location of the shape.
 *
 * @author Simon Wessel
 * @version 2.1
 * @since 1.5
 */
abstract class Shape(locationParam: Point) {

    /** Backing field for location */
    private var _location: Point = locationParam

    /**
     * The location of the shape.
     *
     * @return the location point.
     */
    var location: Point
        /** Returns the location of the shape. */
        get() = _location
        /** Sets the location of the shape. */
        set(v) { _location = v }

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
