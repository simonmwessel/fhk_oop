package de.fhkiel.oop

import de.fhkiel.oop.factory.FormFactory
import de.fhkiel.oop.model.Point
import de.fhkiel.oop.shapes.Circle
import de.fhkiel.oop.shapes.Rectangle
import de.fhkiel.oop.shapes.Square
import processing.core.PApplet

/**
 * Demonstrates usage of the shape classes and launches the [Sketch].
 *
 * Prints examples of:
 * 1. Default random instances ([Circle], [Rectangle], [Square])
 * 2. Parameterized constructors and factory methods (e.g. [Circle.fromArea])
 * 3. Batch generation via [FormFactory.produce]
 * Finally, starts the Processing sketch by invoking [PApplet.main] on [Sketch].
 *
 * @author  Simon Wessel
 * @version 2.7
 * @since   1.0
 */
object Main {

    /**
     * Application entry point.
     *
     * @param args command-line arguments (ignored)
     *
     * @see Circle
     * @see Rectangle
     * @see Square
     * @see FormFactory
     * @see Sketch
     */
    @JvmStatic
    fun main(args: Array<String>) {
        println("Hardcoded test shapes:")

        // Circles
        println(Circle())
        println(Circle(Point(0f, 0f), 5f))
        println(Circle.fromArea(Point(0f, 0f), 50f))
        println()

        // Rectangles
        println(Rectangle())
        println(Rectangle(Point(0f, 0f), 5f, 10f))
        println(Rectangle.fromArea(Point(0f, 0f), 50f, 5f))
        println()

        // Squares
        println(Square())
        println(Square(Point(0f, 0f), 5f))
        println(Square.fromArea(Point(0f, 0f), 25f))
        println()

        // FormFactory
        println("Testing FormFactory:")
        for (shape in FormFactory().produce(10)) println(shape)

        // Start Processing sketch
        PApplet.main("de.fhkiel.oop.Sketch")
    }
}
