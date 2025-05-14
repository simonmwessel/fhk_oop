package de.fhkiel.oop.model

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.utils.FloatExtensions.formatCoordinateValue
import de.fhkiel.oop.utils.FloatExtensions.validateInRange
import de.fhkiel.oop.utils.RandomUtils.random

/**
 * Represents a point in 2D space.
 *
 * Both coordinates default to random values within the canvas bounds
 * defined by [Config.MAX_X] and [Config.MAX_Y].
 *
 * @property _x backing field for the X-coordinate
 * @property _y backing field for the Y-coordinate
 *
 * @author  Simon Wessel
 * @version 2.3
 * @since   1.0
 */
data class Point(
    private var _x: Float = 0f,
    private var _y: Float = 0f
) {

    init {
        _x = _x.validateInRange("x", 0f, Config.MAX_X)
        _y = _y.validateInRange("y", 0f, Config.MAX_Y)
    }

    /**
     * X-coordinate, always within (0f..[de.fhkiel.oop.config.Config.MAX_X]).
     * @throws IllegalArgumentException if outside the allowed range.
     */
    var x: Float
        /** Returns the X-coordinate of the point. */
        get() = _x
        /** Sets the X-coordinate of the point. */
        set(value) {
            _x = value.validateInRange("x", 0f, Config.MAX_X)
        }

    /**
     * Y-coordinate, always within (0f..[de.fhkiel.oop.config.Config.MAX_Y]).
     * @throws IllegalArgumentException if outside the allowed range.
     */
    var y: Float
        /** Returns the Y-coordinate of the point. */
        get() = _y
        /** Sets the Y-coordinate of the point. */
        set(value) {
            _y = value.validateInRange("y", 0f, Config.MAX_Y)
        }

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
