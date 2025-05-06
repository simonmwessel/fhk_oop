package main.kotlin

import kotlin.random.Random
import kotlin.math.sqrt

/**
 * Circle class that represents a circle in 2D space.
 * This class extends the Shape class.
 *
 * @property center The center of the circle.
 * @property radius The radius of the circle.
 *
 * @constructor Creates a circle with the specified center and radius.
 *
 * @author Simon Wessel
 * @version 2.1
 * @since 1.0
 */
class Circle(center: Point, radiusParam: Float) : Shape(center) {

    /** Backing field for radius */
    private var _radius: Float = radiusParam

    /**
     * The radius of the circle.
     *
     * @return the radius.
     */
    var radius: Float
        /** Returns the radius of the circle. */
        get() = _radius
        /** Sets the radius of the circle. */
        set(v) {
            _radius = v
        }

    /**
     * Default constructor for Circle.
     * Initializes the center with a random point and radius with a random value between 0 and 10.
     */
    constructor() : this(Point(), Random.nextFloat() * 10)

    companion object {
        /**
         * Static method to create a Circle from a given area.
         *
         * @param center The center of the circle.
         * @param area The area of the circle.
         *
         * @return A new Circle object with the specified area.
         */
        fun fromArea(center: Point, area: Float): Circle =
            Circle(center, sqrt((area / Math.PI)).toFloat())
    }

    /**
     * Calculates the area of the circle.
     *
     * @return The area of the circle.
     */
    override fun getArea(): Float = (Math.PI * radius * radius).toFloat()

    /**
     * Returns a string representation of the Circle object in the following
     * format: "Type       |      X |      Y |     Radius |            |     Area"
     *
     * @return A string representation of the Circle.
     */
    override fun toString(): String =
        String.format(
            "{ %-15s | x: %8.2f | y: %8.2f | Radius: %10.2f | Area: %10.2f }",
            "Type: Circle", location.x, location.y, radius, getArea()
        )
}
