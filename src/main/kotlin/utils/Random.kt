package de.fhkiel.oop.utils

import kotlin.random.Random

/**
 * Utility object providing random helpers for numbers and colours.
 *
 * ### Numeric distributions
 * **Uniform** – every value in the range is equally likely.
 * **Normal (Gaussian)** – bell-curve; implementation uses a truncated
 *   normal distribution and clips outliers to the range borders.
 *
 * The global default is **uniform**, but every call can override the distribution
 * explicitly (see [ClosedFloatingPointRange.random]).
 *
 * @author  Simon Wessel
 * @version 1.0
 * @since   2.3
 */
object RandomUtils {

    /** Probability distributions supported by this utility. */
    enum class Distribution { UNIFORM, NORMAL }

    /** Globally active distribution for [ClosedFloatingPointRange.random] (default UNIFORM). */
    @JvmStatic
    var defaultDistribution: Distribution = Distribution.UNIFORM

    /** Uniformly distributed random value in this closed range. */
    @JvmStatic
    fun ClosedFloatingPointRange<Float>.randomUniform(): Float =
        Random.nextFloat() * (endInclusive - start) + start

    /**
     * Random value in this range, drawn from a **truncated normal distribution**.
     *
     * The mean (μ) defaults to the mid-point of the interval, the standard deviation (σ)
     * to `range / 6`, so that ± 3 σ equals the full width.
     * Values outside the interval are clipped to the nearest border.
     *
     * @param mean  the mean (μ) of the distribution (default = mid-point of range)
     * @param sigma the standard deviation (σ) of the distribution (default = range / 6)
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
     * Random value in this range using either the global default or the
     * caller-specified [Distribution].
     *
     * @param dist distribution to use (falls back to [defaultDistribution] if omitted)
     */
    @JvmStatic
    fun ClosedFloatingPointRange<Float>.random(
        dist: Distribution = defaultDistribution
    ): Float = when (dist) {
        Distribution.UNIFORM -> randomUniform()
        Distribution.NORMAL  -> randomNormal()
    }

    /**
     * Convenience dispatcher that chooses `randomUniform()` or `randomNormal()` depending on
     * the globally configured {@link #defaultDistribution}.
     */
    @JvmStatic
    fun ClosedFloatingPointRange<Float>.random(): Float =
        when (defaultDistribution) {
            Distribution.UNIFORM -> randomUniform()
            Distribution.NORMAL  -> randomNormal()
        }

    /**
     * Builds a random opaque ARGB‐Color.
     *
     * @param alpha optional alpha channel (0–255), default 255 = opaque
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
