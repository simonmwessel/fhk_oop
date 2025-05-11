package de.fhkiel.oop.utils

import kotlin.random.Random

/**
 * Utility object providing random helpers for numeric ranges and colours.
 *
 * ### Distributions
 * - **[Distribution.UNIFORM]**: Every value in the range is equally likely.
 * - **[Distribution.NORMAL]**: Truncated Gaussian distribution. The peak (mean) can be
 *   controlled via `peakFraction` or `mean`, and the spread via `sigma`.
 *
 * ### Global Defaults (for [Distribution.NORMAL])
 * - [defaultPeakFraction]: Relative peak position (0.0–1.0, overrides [defaultMean]).
 * - [defaultMean]: Absolute mean μ (used if [defaultPeakFraction] is `null`).
 * - [defaultSigma]: Standard deviation σ.
 *
 * By default, all range-based calls use the global [defaultDistribution], but
 * each call can override via explicit parameters.
 *
 * @author Simon Wessel
 * @version 1.2
 * @since 2.3
 */
object RandomUtils {

    /** Probability distributions supported by this utility. */
    enum class Distribution { UNIFORM, NORMAL }

    /**
     * Global default distribution for random calls.
     * @see ClosedFloatingPointRange.random
     */
    @JvmStatic
    var defaultDistribution: Distribution = Distribution.UNIFORM

    /**
     * Global default relative peak position (0.0–1.0) for [Distribution.NORMAL] distribution.
     * Overrides [defaultMean] if set to non-null.
     */
    @JvmStatic
    var defaultPeakFraction: Float? = null

    /**
     * Global default mean μ for [Distribution.NORMAL] distribution.
     * Used only if [defaultPeakFraction] is `null`.
     */
    @JvmStatic
    var defaultMean: Float? = null

    /**
     * Global default standard deviation σ for [Distribution.NORMAL] distribution.
     */
    @JvmStatic
    var defaultSigma: Float? = null

    /**
     * Returns a uniformly distributed random [Float] in this range.
     *
     * @receiver Closed range of [Float].
     *
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
     * @param peakFraction Relative peak position (0.0–1.0). Overrides [mean].
     * @param mean         Mean μ (falls back to [defaultMean] or midpoint if not provided).
     * @param sigma        Standard deviation σ (falls back to [defaultSigma] or range/6).
     *
     * @return Truncated normal random value, clipped to the range.
     */
    @JvmStatic
    fun ClosedFloatingPointRange<Float>.randomNormal(
        peakFraction: Float? = defaultPeakFraction,
        mean: Float? = defaultMean,
        sigma: Float? = defaultSigma
    ): Float {
        val actualMean = peakFraction?.let {
            start + it.coerceIn(0f, 1f) * (endInclusive - start)
        } ?: mean ?: ((start + endInclusive) / 2f)

        val actualSigma = sigma ?: defaultSigma ?: ((endInclusive - start) / 6f)

        val gaussian = java.util.Random().nextGaussian() * actualSigma + actualMean
        return gaussian.toFloat().coerceIn(start, endInclusive)
    }

    /**
     * Returns a random [Float] using the specified distribution.
     *
     * @receiver Closed range of [Float].
     *
     * @param dist         Distribution to use (defaults to [defaultDistribution]).
     * @param peakFraction For [Distribution.NORMAL]: Relative peak position (0.0–1.0). Overrides [mean].
     * @param mean         For [Distribution.NORMAL]: Mean μ (default: global → midpoint).
     * @param sigma        For [Distribution.NORMAL]: Standard deviation σ (default: global → range/6).
     *
     * @return Random value following the chosen distribution.
     */
    @JvmStatic
    fun ClosedFloatingPointRange<Float>.random(
        dist: Distribution = defaultDistribution,
        peakFraction: Float? = defaultPeakFraction,
        mean: Float? = defaultMean,
        sigma: Float? = defaultSigma
    ): Float = when (dist) {
        Distribution.UNIFORM -> randomUniform()
        Distribution.NORMAL -> randomNormal(peakFraction, mean, sigma)
    }

    /**
     * Convenience overload that uses the global [defaultDistribution].
     *
     * @receiver Closed range of [Float].
     *
     * @param peakFraction For [Distribution.NORMAL]: Relative peak position (0.0–1.0). Overrides [mean].
     * @param mean         For [Distribution.NORMAL]: Mean μ (default: global → midpoint).
     * @param sigma        For [Distribution.NORMAL]: Standard deviation σ (default: global → range/6).
     *
     * @return Random value following the global distribution.
     *
     * @see ClosedFloatingPointRange.random
     */
    @JvmStatic
    fun ClosedFloatingPointRange<Float>.random(
        peakFraction: Float? = defaultPeakFraction,
        mean: Float? = defaultMean,
        sigma: Float? = defaultSigma
    ): Float = when (defaultDistribution) {
        Distribution.UNIFORM -> randomUniform()
        Distribution.NORMAL -> randomNormal(peakFraction, mean, sigma)
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
