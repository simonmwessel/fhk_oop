package de.fhkiel.oop.model

import de.fhkiel.oop.config.AppConfig
import de.fhkiel.oop.config.DefaultConfig
import de.fhkiel.oop.utils.FloatExtensions.formatCoordinateValue

/**
 * Represents a two-dimensional vector in 2D space.
 *
 * Both coordinates default to random values within the canvas bounds
 * defined by [de.fhkiel.oop.config.DefaultConfig.maxX] and [de.fhkiel.oop.config.DefaultConfig.maxY].
 *
 * @property _x backing field for the X-coordinate
 * @property _y backing field for the Y-coordinate
 *
 * @author  Simon Wessel
 * @version 2.3
 * @since   1.0
 */
data class Vector2D(
    private val _config: AppConfig = DefaultConfig,
    private var _x: Float = 0f,
    private var _y: Float = 0f
) {
    /**
     * X-coordinate, always within (0f..[de.fhkiel.oop.config.DefaultConfig.maxX]).
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
     * Y-coordinate, always within (0f..[de.fhkiel.oop.config.DefaultConfig.maxY]).
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
        _config.prefix +
        listOf(
            "X" to x.formatCoordinateValue(),
            "Y" to y.formatCoordinateValue()
        ).joinToString(_config.separator) { (k, v) ->
            k + _config.separatorKeyValue + v
        } +
        _config.suffix
}
