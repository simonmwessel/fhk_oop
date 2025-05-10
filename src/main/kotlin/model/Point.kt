package de.fhkiel.oop.model

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.utils.FloatExtensions.formatCoordinateValue
import de.fhkiel.oop.utils.RandomUtils.random

/**
 * Point class that represents a point in 2D space.
 *
 * Specified coordinates are used as given; if omitted, x and/or y
 * default to random values within the ranges defined in Config.
 *
 * @property xParam the x coordinate (default = random ∈ [[0, MAX_X]])
 * @property yParam the y coordinate (default = random ∈ [[0, MAX_Y]])
 *
 * @constructor Creates a point with the specified x and y coordinates.
 *              If either coordinate is omitted, a random value in the respective Config range is used.
 *
 * @author  Simon Wessel
 * @version 2.2
 * @since   1.0
 */
class Point(
    xParam: Float = (0f..Config.MAX_X).random(),
    yParam: Float = (0f..Config.MAX_Y).random()
) {

    /** Backing field for x coordinate */
    private var _x: Float = xParam

    /**
     * The x coordinate of the point.
     *
     * @return the x coordinate.
     */
    var x: Float
        /** Returns the x coordinate of the point. */
        get() = _x
        /** Sets the x coordinate of the point. */
        set(v) { _x = v }

    /** Backing field for y coordinate */
    private var _y: Float = yParam

    /**
     * The y coordinate of the point.
     *
     * @return the y coordinate.
     */
    var y: Float
        /** Returns the y coordinate of the point. */
        get() = _y
        /** Sets the y coordinate of the point. */
        set(v) { _y = v }

    /**
     * Returns a string representation of the point.
     *
     * @return a string representation of the point in the format "{ X:  x.xx | Y:  y.yy }".
     */
    override fun toString(): String =
        Config.PREFIX +
        listOf(
            "X" to x.formatCoordinateValue(),
            "Y" to y.formatCoordinateValue()
        ).joinToString(Config.SEPARATOR) { (k, v) ->
            k + Config.SEPARATOR_KEY_VALUE + v
        } +
        Config.SUFFIX
}
