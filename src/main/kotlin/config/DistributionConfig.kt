package de.fhkiel.oop.config

import de.fhkiel.oop.utils.RandomUtils.Distribution

/**
 * Configuration for generating random numbers with different distributions.
 *
 * Specifies the type of [Distribution] to use and its parameters.
 * This class is used by [GenerationParams] to define how size and origin
 * values for shapes are generated in [de.fhkiel.oop.factory.FormFactory].
 *
 * @property distribution The type of probability distribution to use (e.g., [Distribution.NORMAL], [Distribution.UNIFORM]).
 * @property peakFraction For [Distribution.NORMAL]: Relative peak position (0.0-1.0) in the range.
 *   If set, this overrides the [mean] property.
 * @property mean For [Distribution.NORMAL]: Absolute mean value for the distribution.
 *   Used if [peakFraction] is null.
 * @property sigma For [Distribution.NORMAL]: Standard deviation for the distribution.
 *
 * @author  Simon Wessel
 * @version 1.1
 * @since   2.7
 *
 * @see GenerationParams
 * @see de.fhkiel.oop.factory.FormFactory
 * @see Distribution
 */
data class DistributionConfig(
    val distribution: Distribution = Distribution.NORMAL,
    val peakFraction: Float? = null,
    val mean:         Float? = null,
    val sigma:        Float? = null
) {
    companion object {
        /**
         * Default distribution configuration for shape sizes.
         * Uses [Distribution.NORMAL] with a [peakFraction] of 0.2.
         */
        val DEFAULT_SIZE = DistributionConfig(
            distribution = Distribution.NORMAL,
            peakFraction = 0.2f,
            mean         = null,
            sigma        = null
        )

        /**
         * Default distribution configuration for shape origins.
         * Uses [Distribution.UNIFORM].
         */
        val DEFAULT_ORIGIN = DistributionConfig(distribution = Distribution.UNIFORM)
    }
}
