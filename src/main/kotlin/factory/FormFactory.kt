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
 * Creates random {@link Shape} instances ( {@link Circle}, {@link Rectangle}, {@link Square} ).
 *
 * The class can operate in two different modes:
 * * **Safe mode** (`safe = true`, default) – every generated shape is guaranteed to be fully
 *   contained inside the virtual canvas defined by {@link Config}.
 * * **Legacy mode** (`safe = false`) – reproduces the former behaviour with purely random
 *   coordinates, possibly outside the canvas.
 *
 * Example:
 * ```kotlin
 * val shapes = FormFactory().produce(count = 50)          // safe shapes
 * val debug  = FormFactory().produce(count = 10, safe = false)
 * ```
 *
 * @author  Simon Wessel
 * @version 2.2
 * @since   1.7
 */
class FormFactory {

    /**
     * Creates a list of random shapes.
     *
     * @param count the number of shapes to create
     * @param safe  if `true` (all default) restricts every shape to the canvas; if `false`
     *              falls back to the former unrestricted behaviour
     *
     * @return immutable list of random shapes
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

    /** Legacy variant – recreates the former “anything goes” behaviour. */
    private fun produceLegacy(count: Int): List<Shape> = List(count) {
        when (Random.nextDouble()) {
            in 0.0..0.33  -> Circle()
            in 0.33..0.66 -> Rectangle()
            else          -> Square()
        }
    }
}
