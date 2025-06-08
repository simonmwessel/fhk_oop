package de.fhkiel.oop.model

import de.fhkiel.oop.config.AppConfig
import de.fhkiel.oop.config.DefaultConfig
import de.fhkiel.oop.utils.Color
import de.fhkiel.oop.utils.FloatExtensions.validateInRange
import de.fhkiel.oop.utils.RandomUtils.random
import de.fhkiel.oop.utils.RandomUtils.randomColor

/**
 * Immutable bundle of graphical attributes for a [Shape].
 *
 * @property fill   The fill (interior) colour, defaults to a random RGB-A colour.
 * @property stroke The stroke (outline) colour, defaults to another random RGB-A colour.
 * @property weight The stroke width in pixels, random between [de.fhkiel.oop.config.DefaultConfig.minStrkWeight] and [de.fhkiel.oop.config.DefaultConfig.maxStrkWeight].
 *
 * @see Color
 * @see de.fhkiel.oop.utils.RandomUtils.randomColor
 * @see AppConfig
 * @see DefaultConfig
 *
 * @author Simon Wessel
 */
data class Style(
    private val _config: AppConfig = DefaultConfig,

    /**
     * Backing field for the fill color. Default = random ARGB color.
     */
    private var _fill: Color   = randomColor(),

    /**
     * Backing field for the stroke color. Default = random ARGB color.
     */
    private var _stroke: Color = randomColor(),

    /**
     * Backing field for the stroke weight.
     */
    private var _weight: Float = (_config.minStrkWeight.._config.maxStrkWeight).random()

) {
    init {
        _weight = _weight.validateInRange(
            name = "style.weight",
            lo   = _config.minStrkWeight,
            hi   = _config.maxStrkWeight
        )
    }

    /** Fill color (RGBA). */
    var fill: Color
        get() = _fill
        set(v) { _fill = v }

    /** Stroke color (RGBA). */
    var stroke: Color
        get() = _stroke
        set(v) { _stroke = v }

    /**
     * Stroke weight in pixels.
     *
     * @throws IllegalArgumentException if outside the allowed range.
     */
    var weight: Float
        get() = _weight
        set(v) {
            _weight = v.validateInRange(
                "style.weight",
                _config.minStrkWeight,
                _config.maxStrkWeight
            )
        }
}
