package main.kotlin

/**
 * Main class to demonstrate the functionality of the Circle, Rectangle, and Square classes.
 *
 * @author Simon Wessel
 * @version 1.0
 * @since 1.0
 */
object Main {

    /**
     * Main method to demonstrate the functionality of the classes.
     *
     * @param args Command line arguments (not used).
     */
    @JvmStatic
    fun main(args: Array<String>) {

        println("--- Shapes with kotlin ---")

        // Circles
        val circle1 = Circle()
        val circle2 = Circle(Point(0f, 0f), 5f)
        val circle3 = Circle.fromArea(Point(0f, 0f), 50f)

        println("C 1: $circle1")
        println("C 2: $circle2")
        println("C 3: $circle3")

        println()

        // Rectangles
        val rectangle1 = Rectangle()
        val rectangle2 = Rectangle(Point(0f, 0f), 5f, 10f)
        val rectangle3 = Rectangle.fromArea(Point(0f, 0f), 50f, 5f)

        println("R 1: $rectangle1")
        println("R 2: $rectangle2")
        println("R 3: $rectangle3")

        println()

        // Squares
        val square1 = Square()
        val square2 = Square(Point(0f, 0f), 5f)
        val square3 = Square.fromArea(Point(0f, 0f), 25f)

        println("S 1: $square1")
        println("S 2: $square2")
        println("S 3: $square3")

        println()

        // FormFactory
        val formFactory = FormFactory()
        val shapes = formFactory.produce(10)

        for (shape in shapes) {
            println(shape)
        }
    }
}
