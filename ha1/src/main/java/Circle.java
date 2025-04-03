package main.java;

/**
 * Circle class that represents a circle in 2D space.
 *
 * @author Simon Wessel
 * @version 1.0
 * @since 1.0
 * */
public class Circle {
    /** @return returns the center of the circle */
    public Point getCenter() {
        return center;
    }

    /** @param center sets the center of the circle */
    public void setCenter(Point center) {
        this.center = center;
    }

    /** Contains the center location of the circle */
    private Point center;

    /** @return returns the radius of the circle */
    public float getRadius() {
        return radius;
    }

    /** @param radius sets the radius of the circle */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /** Contains the radius of the circle */
    private float radius;

    /**
     * Default constructor for Circle.
     * Initializes the center to (0,0) and radius to 1.
     */
    public Circle() {
        this.center = new Point(0,0);
        this.radius = 1;
    }

    /**
     * Constructor for Circle with specified center and radius.
     *
     * @param center The center of the circle.
     * @param radius The radius of the circle.
     */
    public Circle(Point center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Static method to create a Circle from a given area.
     *
     * @param center The center of the circle.
     * @param area The area of the circle.
     * @return A new Circle object with the specified area.
     */
    public static Circle fromArea(Point center, float area) {
        return new Circle(center, (float)Math.sqrt(area / Math.PI));
    }

    /**
     * Calculates the area of the circle.
     *
     * @return The area of the circle.
     */
    public float getArea() {
        return (float)Math.PI * this.radius * this.radius;
    }

    /**
     * Returns a string representation of the Circle object.
     *
     * @return A string representation of the Circle.
     */
    @Override
    public String toString() {
        return "Circle { center=" + center + ", radius=" + radius + ", area=" + this.getArea() + " }";
    }
}
