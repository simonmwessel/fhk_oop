package de.fhkiel.oop.utils

import kotlin.random.Random

/**
 * Utility object for generating random values.
 *
 * @author  Simon Wessel
 * @version 1.0
 * @since   2.3
 */
object RandomUtils {
    /**
     * Returns a random Float in this closed range.
     */
    @JvmStatic
    fun ClosedFloatingPointRange<Float>.random(): Float =
        Random.nextFloat() * (endInclusive - start) + start

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