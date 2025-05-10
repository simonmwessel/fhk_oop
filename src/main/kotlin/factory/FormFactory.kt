package de.fhkiel.oop.factory

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.model.Point
import de.fhkiel.oop.model.Shape
import de.fhkiel.oop.shapes.Circle
import de.fhkiel.oop.shapes.Rectangle
import de.fhkiel.oop.shapes.Square
import de.fhkiel.oop.utils.RandomUtils.random
import de.fhkiel.oop.utils.RandomUtils.Distribution
import kotlin.random.Random

/**
 * Factory for creating random [Shape] instances: [Circle], [Rectangle], [Square].
 *
 * Two configuration axes:
 * 1. **Bounds handling**
 *    - `safe = true` (default): every shape is fully contained within the canvas defined by [Config].
 *    - `safe = false`: falls back to legacy behaviour with unrestricted random coordinates.
 *
 * 2. **Probability distributions**
 *    - `sizeDist` governs distribution of size parameters (e.g. radius, width, side length).
 *    - `originDist` governs distribution of origin coordinates.
 *    Both default to `NORMAL` (sizes) and `UNIFORM` (origins) and use [ClosedFloatingPointRange.random].
 *
 * ### Example
 * ```kotlin
 * val factory = FormFactory()
 * val shapes  = factory.produce(
 *     count      = 50,
 *     safe       = true,
 *     sizeDist   = Distribution.NORMAL,
 *     originDist = Distribution.UNIFORM
 * )
 * ```
 *
 * @author  Simon Wessel
 * @version 2.2
 * @since   1.7
 */
class FormFactory {

    /**
     * Produces a list of random shapes.
     *
     * @param count      Number of shapes to create.
     * @param safe       If `true`, restricts shapes to the canvas; if `false`, uses legacy mode.
     * @param sizeDist   Distribution for size parameters.
     * @param originDist Distribution for origin coordinates.
     *
     * @return Immutable [List] of freshly generated [Shape]s.
     */
    fun produce(
        count: Int,
        safe: Boolean = true,
        sizeDist:   Distribution = Distribution.NORMAL,
        originDist: Distribution = Distribution.UNIFORM
    ): List<Shape> =
        if (safe) produceInBounds(count, sizeDist, originDist)
        else       produceLegacy(count)

    /** Builds a [Circle] whose centre **and** radius fit completely inside the canvas. */
    fun circle(
        sizeDist:   Distribution,
        originDist: Distribution
    ): Circle {
        val r = (0f..Config.MAX_CIRCLE_RADIUS).random(dist = sizeDist)
        val x = (r..Config.MAX_X - r).random(dist = originDist)
        val y = (r..Config.MAX_Y - r).random(dist = originDist)
        return Circle(Point(x, y), r)
    }

    /** Builds a [Rectangle] whose top-left corner, width and height stay within the canvas. */
    fun rectangle(
        sizeDist:   Distribution,
        originDist: Distribution
    ): Rectangle {
        val w = (0f..Config.MAX_RECT_WIDTH).random(dist = sizeDist)
        val h = (0f..Config.MAX_RECT_HEIGHT).random(dist = sizeDist)
        val x = (0f..Config.MAX_X - w).random(dist = originDist)
        val y = (0f..Config.MAX_Y - h).random(dist = originDist)
        return Rectangle(Point(x, y), w, h)
    }

    /** Builds a [Square] that is fully contained in the canvas. */
    fun square(
        sizeDist:   Distribution,
        originDist: Distribution
    ): Square {
        val s = (0f..Config.MAX_SQUARE_SIDE).random(dist = sizeDist)
        val x = (0f..Config.MAX_X - s).random(dist = originDist)   // ← originDist!
        val y = (0f..Config.MAX_Y - s).random(dist = originDist)
        return Square(Point(x, y), s)
    }

    /** Safe variant – used by [produce] when `safe == true`. */
    private fun produceInBounds(
        count: Int,
        sizeDist:   Distribution,
        originDist: Distribution
    ): List<Shape> = List(count) {
        when (Random.nextDouble()) {
            in 0.0..0.33  -> circle(sizeDist, originDist)
            in 0.33..0.66 -> rectangle(sizeDist, originDist)
            else          -> square(sizeDist, originDist)
        }
    }

    /**
     * Internal helper for legacy mode (`safe = false`).
     *
     * @param count Number of shapes.
     */
    private fun produceLegacy(count: Int): List<Shape> = List(count) {
        when (Random.nextDouble()) {
            in 0.0..0.33  -> Circle()
            in 0.33..0.66 -> Rectangle()
            else          -> Square()
        }
    }
}
