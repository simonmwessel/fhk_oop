package de.fhkiel.oop.sketch.config

/**
 * Configuration parameters for on-screen hint box visibility.
 *
 * @property duration  Milliseconds the hint remains visible. Defaults to 15 000.
 * @property startTime Timestamp (ms) when the hint was last shown. Defaults to 0.
 *
 * @author Simon Wessel
 */
class HintConfig {
    /**
     * Backing field for the hint visibility duration.
     *
     * Defaults to 15 000 milliseconds.
     */
    private var _duration: Int = 15_000

    /**
     * Returns the duration the hint box remains visible.
     *
     * @return hint visibility duration in milliseconds.
     */
    var duration: Int
        get() = _duration
        /**
         * Sets the hint visibility duration.
         *
         * @throws IllegalArgumentException if value < 0.
         */
        set(value) {
            require(value >= 0) { "Hint duration must be ≥ 0" }
            _duration = value
        }

    /**
     * Backing field for the timestamp of the last hint display.
     *
     * Defaults to 0 milliseconds.
     */
    private var _startTime: Int = 0

    /**
     * Returns the timestamp (ms) of the last hint display.
     *
     * @return hint start time in milliseconds.
     */
    var startTime: Int
        get() = _startTime
        /**
         * Sets the hint start time.
         *
         * @throws IllegalArgumentException if value < 0.
         */
        set(value) {
            require(value >= 0) { "Hint start time must be ≥ 0" }
            _startTime = value
        }
}
