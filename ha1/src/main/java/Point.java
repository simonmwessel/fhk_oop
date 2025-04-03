package main.java;

/**
 * Point class that represents a point in 2D space.
 *
 * @author Simon Wessel
 * @version 1.0
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
     * Initializes the point to (0,0).
     */
    public Point() {
        this.x = 0;
        this.y = 0;
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
     * @return a string representation of the point in the format "(x|y)".
     */
    @Override
    public String toString() {
        return "(" + x + "|" + y + ")";
    }
}
