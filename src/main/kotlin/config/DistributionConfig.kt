package de.fhkiel.oop.config

import de.fhkiel.oop.utils.RandomUtils.Distribution

data class DistributionConfig(
    val distribution: Distribution = Distribution.NORMAL,
    val peakFraction: Float? = null,
    val mean:         Float? = null,
    val sigma:        Float? = null
) {
    companion object {
        val DEFAULT_SIZE = DistributionConfig(
            distribution = Distribution.NORMAL,
            peakFraction = 0.2f,
            mean         = null,
            sigma        = null
        )

        val DEFAULT_ORIGIN = DistributionConfig(distribution = Distribution.UNIFORM)
    }
}
