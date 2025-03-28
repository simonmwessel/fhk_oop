package main.java;

public class Circle {
    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    private Point center;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    private float radius;

    public Circle() {
        this.center = new Point(0,0);
        this.radius = 1;
    }

    public Circle(Point center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    public static Circle fromArea(Point center, float area) {
        return new Circle(center, (float)Math.sqrt(area / Math.PI));
    }

    public float calculateArea() {
        return (float)Math.PI * this.radius * this.radius;
    }

    public String toString() {
        return "Circle { center=" + center + ", radius=" + radius + ", area=" + calculateArea() + " }";
    }
}
