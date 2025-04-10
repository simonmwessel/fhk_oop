package main.kotlin

import kotlin.random.Random
import kotlin.math.sqrt

/**
 * Circle class that represents a circle in 2D space.
 *
 * @author Simon Wessel
 * @version 2.0
 * @since 1.0
 * */
class Circle : Shape {

    /** Contains the radius of the circle */
    private var radius: Float = 0.0f

    /**
     * @return returns the radius of the circle
     */
    fun getRadius(): Float {
        return radius
    }

    /**
     * @param radius sets the radius of the circle
     */
    fun setRadius(radius: Float) {
        this.radius = radius
    }

    /**
     * Default constructor for Circle.
     * Initializes the center with a random point and radius with a random value between 0 and 10.
     */
    constructor() : super(Point()) {
        radius = Random.nextFloat() * 10
    }

    /**
     * Constructor for Circle with specified center and radius.
     *
     * @param center The center of the circle.
     * @param radius The radius of the circle.
     */
    constructor(center: Point, radius: Float) : super(center) {
        this.radius = radius
    }

    companion object {
        /**
         * Static method to create a Circle from a given area.
         *
         * @param center The center of the circle.
         * @param area The area of the circle.
         * @return A new Circle object with the specified area.
         */
        fun fromArea(center: Point, area: Float): Circle {
            return Circle(center, sqrt((area / Math.PI)).toFloat())
        }
    }

    /**
     * Calculates the area of the circle.
     *
     * @return The area of the circle.
     */
    override fun getArea(): Float {
        return (Math.PI * radius * radius).toFloat()
    }

    /**
     * Returns a string representation of the Circle object in the following
     * format: "Type       |      X |      Y |     Radius |            |     Area"
     *
     * @return A string representation of the Circle.
     */
    override fun toString(): String {
        return String.format(
            "{ %-15s | x: %8.2f | y: %8.2f | Radius: %10.2f | Area: %10.2f }",
            "Type: Circle", getLocation().getX(), getLocation().getY(), radius, getArea()
        )
    }
}
