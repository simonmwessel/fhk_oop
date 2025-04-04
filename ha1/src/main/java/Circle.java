package main.java;

/**
 * Circle class that represents a circle in 2D space.
 *
 * @author Simon Wessel
 * @version 1.1
 * @since 1.0
 * */
public class Circle extends Shape {
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
        super();
        this.radius = 1;
    }

    /**
     * Constructor for Circle with specified center and radius.
     *
     * @param center The center of the circle.
     * @param radius The radius of the circle.
     */
    public Circle(Point center, float radius) {
        super(center);
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
    @Override
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
        return "Circle { center=" + this.getLocation() + ", radius=" + this.radius + ", area=" + this.getArea() + " }";
    }
}
