package de.fhkiel.oop

import de.fhkiel.oop.factory.FormFactory
import de.fhkiel.oop.model.Point
import de.fhkiel.oop.shapes.Circle
import de.fhkiel.oop.shapes.Rectangle
import de.fhkiel.oop.shapes.Square
import processing.core.PApplet

/**
 * Main class to demonstrate the functionality of the Circle, Rectangle, and Square classes.
 *
 * @author  Simon Wessel
 * @version 2.3
 * @since   1.0
 */
object Main {

    /**
     * Main method to demonstrate the functionality of the classes.
     *
     * @param args Command line arguments (not used).
     */
    @JvmStatic
    fun main(args: Array<String>) {
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
        for (shape in FormFactory().produce(10)) println(shape)

        PApplet.main("de.fhkiel.oop.Sketch")
    }
}
