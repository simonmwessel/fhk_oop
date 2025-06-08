package de.fhkiel.oop.sketch.config

import de.fhkiel.oop.config.AppConfig
import de.fhkiel.oop.config.DefaultConfig
import kotlin.math.min

/**
 * Configuration parameters for the Processing sketch canvas.
 *
 * @property baseWidth  Reference width for scaling calculations. Defaults to [de.fhkiel.oop.config.DefaultConfig.maxX].
 * @property baseHeight Reference height for scaling calculations. Defaults to [de.fhkiel.oop.config.DefaultConfig.maxY].
 *
 * @author Simon Wessel
 */
class SketchConfig(config: AppConfig = DefaultConfig) {

    /**
     * Backing field for the base width.
     *
     * Defaults to [de.fhkiel.oop.config.DefaultConfig.maxX].
     */
    private var _baseWidth: Float = config.maxX

    /**
     * Returns the base width for scaling calculations.
     *
     * @return the current reference width.
     * @see de.fhkiel.oop.config.DefaultConfig.maxX
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
     * Defaults to [de.fhkiel.oop.config.DefaultConfig.maxY].
     */
    private var _baseHeight: Float = config.maxY

    /**
     * Returns the base height for scaling calculations.
     *
     * @return the current reference height.
     * @see de.fhkiel.oop.config.DefaultConfig.maxY
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
