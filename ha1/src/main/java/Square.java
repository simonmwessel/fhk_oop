package main.java;

import java.util.Random;

/**
 * Square class that represents a square in 2D space.
 *
 * @author Simon Wessel
 * @version 1.2
 * @since 1.0
 */
public class Square extends Shape {

    /** Contains the side length of the square */
    private float sideLength;

    /** @return returns the side length of the square */
    public float getSideLength() {
        return sideLength;
    }

    /** @param sideLength sets the side length of the square */
    public void setSideLength(float sideLength) {
        this.sideLength = sideLength;
    }

    /**
     * Default constructor for Square.
     * Initializes the top left corner with a random point and side length with a random value between 0 and 10.
     */
    public Square() {
        super(new Point());
        Random random = new Random();
        this.sideLength = random.nextFloat() * 10;
    }

    /**
     * Constructor for Square with specified top left corner and side length.
     *
     * @param topLeft The top left corner of the square.
     * @param side The side length of the square.
     */
    public Square(Point topLeft, float side) {
        super(topLeft);
        this.sideLength = side;
    }

    /**
     * Static method to create a Square from a given area.
     *
     * @param topLeft The top left corner of the square.
     * @param area The area of the square.
     * @return A new Square object with the specified area.
     */
    public static Square fromArea(Point topLeft, float area) {
        return new Square(topLeft, (float)Math.sqrt(area));
    }

    /**
     * Calculates the area of the square.
     *
     * @return The area of the square.
     */
    public float getArea() {
        return this.sideLength * this.sideLength;
    }

    /**
     * Returns a string representation of the square.
     *
     * @return A string representation of the square.
     */
    @Override
    public String toString() {
        return "Square { topLeft=" + this.getLocation() + ", side=" + this.sideLength + ", area=" + this.getArea() + " }";
    }
}
