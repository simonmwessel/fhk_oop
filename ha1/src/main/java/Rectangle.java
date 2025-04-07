package main.java;

import java.util.Random;

/**
 * Rectangle class that represents a rectangle in 2D space.
 *
 * @author Simon Wessel
 * @version 1.2
 * @since 1.0
 */
public class Rectangle extends Shape {

    /** Contains the width of the rectangle */
    private float width;

    /** @return returns the width of the rectangle */
    public float getWidth() {
        return width;
    }

    /** @param width sets the width of the rectangle */
    public void setWidth(float width) {
        this.width = width;
    }

    /** Contains the height of the rectangle */
    private float height;

    /** @return returns the height of the rectangle */
    public float getHeight() {
        return height;
    }

    /** @param height sets the height of the rectangle */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Default constructor for Rectangle.
     * Initializes the top left corner with a random point and sets width and height with random values.
     */
    public Rectangle() {
        super(new Point());
        Random random = new Random();
        this.width = random.nextFloat() * 20;
        this.height = random.nextFloat() * 20;
    }

    /**
     * Constructor for Rectangle with specified top left corner, width, and height.
     *
     * @param topLeft The top left corner of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     */
    public Rectangle(Point topLeft, float width, float height) {
        super(topLeft);
        this.width = width;
        this.height = height;
    }

    /**
     * Static method to create a Rectangle from a given area and length.
     *
     * @param topLeft The top left corner of the rectangle.
     * @param area The area of the rectangle.
     * @param length The length of one side of the rectangle.
     * @return A new Rectangle object with the specified area and length.
     */
    public static Rectangle fromArea(Point topLeft, float area, float length) {
        return new Rectangle(topLeft, area / length, length);
    }

    /**
     * Calculates the area of the rectangle.
     *
     * @return The area of the rectangle.
     */
    public float getArea() {
        return this.width * this.height;
    }

    /**
     * Returns a string representation of the rectangle.
     *
     * @return A string representation of the rectangle.
     */
    @Override
    public String toString() {
        return "Rectangle { topLeft=" + this.getLocation() + ", width=" + this.width + ", height=" + this.height + ", area=" + this.getArea() + " }";
    }
}
