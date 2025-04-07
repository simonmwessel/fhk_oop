package main.kotlin

/**
 * Shape class that represents a generic shape in 2D space.
 *
 * @author Simon Wessel
 * @version 1.0
 * @since 1.1
 */
abstract class Shape {

    /** Contains the location of the shape */
    private var location: Point

    /** @return returns the location of the shape */
    fun getLocation(): Point {
        return location
    }

    /** @param point sets the location of the shape */
    fun setLocation(point: Point) {
        this.location = point
    }

    /**
     * Default constructor for Shape.
     * Initializes the location with a random point.
     */
    constructor() {
        this.location = Point()
    }

    /**
     * Constructor for Shape with specified location.
     *
     * @param point The location of the shape.
     */
    constructor(point: Point) {
        this.location = point
    }

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
