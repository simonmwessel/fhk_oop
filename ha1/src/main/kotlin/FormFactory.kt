package main.kotlin

/**
 * FormFactory class that creates an array of random shapes.
 *
 * @author Simon Wessel
 * @version 1.0
 * @since 1.0
 */
class FormFactory {

    /**
     * Produces an array of random shapes.
     *
     * @param count the number of shapes to produce
     * @return an array of random shapes
     */
    fun produce(count: Int): Array<Shape> {
        val shapes = Array<Shape>(count) { Circle() }
        for (i in 0 until count) {
            val rand = Math.random()
            if (rand < 0.33) {
                shapes[i] = Circle()
            } else if (rand < 0.66) {
                shapes[i] = Rectangle()
            } else {
                shapes[i] = Square()
            }
        }
        return shapes
    }
}
