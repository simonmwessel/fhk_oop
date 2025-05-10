package de.fhkiel.oop.utils

import kotlin.random.Random


/**
 * Utility object providing random helpers for numeric ranges and colours.
 *
 * ### Distributions
 * - **Uniform**: every value in the range is equally likely.
 * - **Normal**: truncated Gaussian; mean defaults to midpoint, σ = (range / 6),
 *   clipping outliers at the bounds.
 *
 * By default, all range-based calls use the global [defaultDistribution], but
 * each call can override via an explicit parameter.
 *
 * @author  Simon Wessel
 * @version 1.0
 * @since   2.3
 */
object RandomUtils {

    /** Probability distributions supported by this utility. */
    enum class Distribution { UNIFORM, NORMAL }

    /**
     * Global default distribution for range-based random calls.
     *
     * @see ClosedFloatingPointRange.random
     */
    @JvmStatic
    var defaultDistribution: Distribution = Distribution.UNIFORM

    /**
     * Returns a uniformly distributed random [Float] in this range.
     *
     * @receiver Closed range of [Float].
     * @return Uniform random value between `start` and `endInclusive`.
     */
    @JvmStatic
    fun ClosedFloatingPointRange<Float>.randomUniform(): Float =
        Random.nextFloat() * (endInclusive - start) + start

    /**
     * Returns a random [Float] drawn from a truncated normal distribution.
     *
     * @receiver Closed range of [Float].
     *
     * @param mean  Mean μ (default = midpoint of the range).
     * @param sigma Standard deviation σ (default = range / 6 ⇒ ±3σ covers the range).
     *
     * @return Truncated normal random value, clipped to the range.
     */
    @JvmStatic
    fun ClosedFloatingPointRange<Float>.randomNormal(
        mean: Float = (start + endInclusive) / 2f,
        sigma: Float = (endInclusive - start) / 6f
    ): Float {
        val gaussian = java.util.Random().nextGaussian() * sigma + mean
        return gaussian.toFloat().coerceIn(start, endInclusive)
    }

    /**
     * Returns a random [Float] using the specified distribution.
     *
     * @receiver Closed range of [Float].
     *
     * @param dist Distribution to use (defaults to [defaultDistribution]).
     *
     * @return Random value following the chosen distribution.
     */
    @JvmStatic
    fun ClosedFloatingPointRange<Float>.random(
        dist: Distribution = defaultDistribution
    ): Float = when (dist) {
        Distribution.UNIFORM -> randomUniform()
        Distribution.NORMAL  -> randomNormal()
    }

    /**
     * Convenience overload that uses the global [defaultDistribution].
     *
     * @receiver Closed range of [Float].
     *
     * @return Random value following the global distribution.
     *
     * @see ClosedFloatingPointRange.random
     */
    @JvmStatic
    fun ClosedFloatingPointRange<Float>.random(): Float =
        when (defaultDistribution) {
            Distribution.UNIFORM -> randomUniform()
            Distribution.NORMAL  -> randomNormal()
        }

    /**
     * Builds a random opaque ARGB [Color].
     *
     * @param alpha Alpha channel (0–255), default = 255 (opaque).
     *
     * @return Random [Color] with specified alpha.
     */
    @JvmStatic
    fun randomColor(alpha: Int = 0xFF): Color =
        Color(
            r = Random.nextInt(0, 256),
            g = Random.nextInt(0, 256),
            b = Random.nextInt(0, 256),
            a = alpha
        )
}
