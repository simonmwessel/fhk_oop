package de.fhkiel.oop.shapes

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.model.Point
import de.fhkiel.oop.model.Shape
import de.fhkiel.oop.utils.FloatExtensions.formatAreaValue
import de.fhkiel.oop.utils.FloatExtensions.formatAttribute1Value
import de.fhkiel.oop.utils.FloatExtensions.formatCoordinateValue
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
 * @author  Simon Wessel
 * @version 2.2
 * @since   1.0
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
         * @param area   The area of the circle.
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
     * Returns a string representation of the Circle
     *
     * @return A string representation of the Circle.
     */
    override fun toString(): String =
        buildString(
            listOf(
                Triple(  "Type",   this::class.simpleName!!,           Config.PAD_TYPE   to Config.PAD_TYPE_VAL),
                Triple(  "X",      location.x.formatCoordinateValue(), Config.PAD_CORD   to Config.PAD_CORD_VAL),
                Triple(  "Y",      location.y.formatCoordinateValue(), Config.PAD_CORD   to Config.PAD_CORD_VAL),
                Triple(  "Radius", radius.formatAttribute1Value(),     Config.PAD_ATTR_1 to Config.PAD_ATTR_1_VAL),
                Triple(  "",       "",                                 (Config.PAD_ATTR_2 + Config.SEPARATOR_KEY_VALUE.length + Config.PAD_ATTR_2_VAL) to 0),
                Triple(  "Area",   getArea().formatAreaValue(),        Config.PAD_AREA   to Config.PAD_AREA_VAL)
            ))
}
