package de.fhkiel.oop

import de.fhkiel.oop.config.GenerationParams
import de.fhkiel.oop.factory.FormFactory
import de.fhkiel.oop.model.Vector2D
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
 * @version 2.8
 * @since   1.0
 */
object Main {

    /**
     * Application entry vector.
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
        println(Circle(Vector2D(0f, 0f), 10f))
        println(Circle.fromArea(Vector2D(0f, 0f), 400f))
        println()

        // Rectangles
        println(Rectangle())
        println(Rectangle(Vector2D(0f, 0f), 10f, 15f))
        println(Rectangle.fromArea(Vector2D(0f, 0f), 400f, 10f))
        println()

        // Squares
        println(Square())
        println(Square(Vector2D(0f, 0f), 10f))
        println(Square.fromArea(Vector2D(0f, 0f), 400f))
        println()

        // FormFactory
        println("Testing FormFactory:")
        for (shape in FormFactory().produce(GenerationParams.RANDOM_20)) println(shape)

        // Start Processing sketch
        PApplet.main("de.fhkiel.oop.Sketch")
    }
}
