package de.fhkiel.oop.model

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.utils.FloatExtensions.formatCoordinateValue

/**
 * Represents a two-dimensional vector in 2D space.
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
data class Vector2D(
    private var _x: Float = 0f,
    private var _y: Float = 0f
) {
    /**
     * X-coordinate, always within (0f..[de.fhkiel.oop.config.Config.MAX_X]).
     * @throws IllegalArgumentException if outside the allowed range.
     */
    var x: Float
        /** Returns the X-coordinate of the vector. */
        get() = _x
        /** Sets the X-coordinate of the vector. */
        set(value) {
            _x = value
        }

    /**
     * Y-coordinate, always within (0f..[de.fhkiel.oop.config.Config.MAX_Y]).
     * @throws IllegalArgumentException if outside the allowed range.
     */
    var y: Float
        /** Returns the Y-coordinate of the vector. */
        get() = _y
        /** Sets the Y-coordinate of the vector. */
        set(value) {
            _y = value
        }

    /**
     * Returns a string representation of the vector.
     *
     * @return a string representation of the vector in the format "{ X:  x.xx | Y:  y.yy }".
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
