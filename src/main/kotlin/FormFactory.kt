package main.kotlin

import kotlin.random.Random

/**
 * FormFactory class that creates an array of random shapes.
 *
 * @author  Simon Wessel
 * @version 2.1
 * @since   1.7
 */
class FormFactory {

    /**
     * Produces an array of random shapes.
     *
     * @param count the number of shapes to produce
     * @return an array of random shapes
     */
    fun produce(count: Int): List<Shape> = List(count) {
        when (Random.nextDouble()) {
            in 0.0..0.33  -> Circle()
            in 0.33..0.66 -> Rectangle()
            else          -> Square()
        }
    }
}
