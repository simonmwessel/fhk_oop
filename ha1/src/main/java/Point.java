package main.java;

import java.util.Random;

/**
 * Point class that represents a point in 2D space.
 *
 * @author Simon Wessel
 * @version 1.3
 * @since 1.0
 */
public class Point {
    /** @return returns the x coordinate of the point */
    public float getX() {
        return x;
    }

    /** @param x sets the x coordinate of the point */
    public void setX(float x) {
        this.x = x;
    }

    /** Contains the x coordinate of the point */
    private float x;

    /** @return returns the y coordinate of the point */
    public float getY() {
        return y;
    }

    /** @param y sets the y coordinate of the point */
    public void setY(float y) {
        this.y = y;
    }

    /** Contains the y coordinate of the point */
    private float y;

    /**
     * Default constructor for Point.
     * Initializes the point with random x and y coordinates between 0 and 100.
     */
    public Point() {
        Random random = new Random();
        this.x = random.nextFloat() * 100;
        this.y = random.nextFloat() * 100;
    }

    /**
     * Constructor for Point with specified x and y coordinates.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     */
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a string representation of the point.
     *
     * @return a string representation of the point in the format "(   x.xx |   y.yy)".
     */
    @Override
    public String toString() {
        return String.format("x: %6.2f, y: %6.2f", x, y);
    }
}
