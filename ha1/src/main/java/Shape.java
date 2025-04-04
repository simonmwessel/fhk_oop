package main.java;

/**
 * Shape class that represents a generic shape in 2D space.
 *
 * @author Simon Wessel
 * @version 1.0
 * @since 1.1
 */
public abstract class Shape {

    /** @return returns the location of the shape */
    public Point getLocation() {
        return location;
    }

    /** @param point sets the location of the shape */
    public void setLocation(Point point) {
        this.location = point;
    }

    /** Contains the location of the shape */
    private Point location;

    /**
     * Default constructor for Shape.
     * Initializes the location to (0,0).
     */
    public Shape() {
        this.location = new Point(0, 0);
    }

    /**
     * Constructor for Shape with specified location.
     *
     * @param point The location of the shape.
     */
    public Shape(Point point) {
        this.location = point;
    }

    /**
     * Abstract method to calculate the area of the shape.
     *
     * @return The area of the shape.
     */
    public abstract float getArea();

    /**
     * Abstract method to calculate the perimeter of the shape.
     *
     * @return The perimeter of the shape.
     */
    public abstract String toString();
}
