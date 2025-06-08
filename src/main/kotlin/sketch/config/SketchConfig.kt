package de.fhkiel.oop.sketch.config

import de.fhkiel.oop.config.Config
import kotlin.math.min

/**
 * Configuration parameters for the Processing sketch canvas.
 *
 * @property baseWidth  Reference width for scaling calculations. Defaults to [Config.MAX_X].
 * @property baseHeight Reference height for scaling calculations. Defaults to [Config.MAX_Y].
 *
 * @author Simon Wessel
 */
class SketchConfig {

    /**
     * Backing field for the base width.
     *
     * Defaults to [Config.MAX_X].
     */
    private var _baseWidth: Float = Config.MAX_X

    /**
     * Returns the base width for scaling calculations.
     *
     * @return the current reference width.
     * @see Config.MAX_X
     */
    var baseWidth: Float
        get() = _baseWidth
        /**
         * Sets the base width for scaling calculations.
         *
         * @throws IllegalArgumentException if value ≤ 0.
         */
        set(value) {
            require(value > 0f) { "Base width must be > 0" }
            _baseWidth = value
        }

    /**
     * Backing field for the base height.
     *
     * Defaults to [Config.MAX_Y].
     */
    private var _baseHeight: Float = Config.MAX_Y

    /**
     * Returns the base height for scaling calculations.
     *
     * @return the current reference height.
     * @see Config.MAX_Y
     */
    var baseHeight: Float
        get() = _baseHeight
        /**
         * Sets the base height for scaling calculations.
         *
         * @throws IllegalArgumentException if value ≤ 0.
         */
        set(value) {
            require(value > 0f) { "Base height must be > 0" }
            _baseHeight = value
        }

    /**
     * Returns the minimum of [baseWidth] and [baseHeight].
     *
     * This is used for uniform scaling.
     *
     * @return the smaller of width and height.
     */
    var baseMin: Float
        get() = min(_baseWidth, _baseHeight)
        /**
         * This is derived from [baseWidth] and [baseHeight],
         * so it cannot be set directly.
         *
         * @throws UnsupportedOperationException always.
         */
        set(@Suppress("UNUSED_PARAMETER") value) {
            throw UnsupportedOperationException("baseMin is derived and cannot be set directly")
        }
}
