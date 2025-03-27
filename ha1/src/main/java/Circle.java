package main.java;

public class Circle {
    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    private Point center;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    private double radius;

    public Circle() {
        this.center = new Point(0,0);
        this.radius = 1;
    }

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public static Circle fromArea(Point center, double area) {
        return new Circle(center, Math.sqrt(area / Math.PI));
    }

    public double calculateArea() {
        return Math.PI * this.radius * this.radius;
    }

    public String toString() {
        return "Circle { center=" + center + ", radius=" + radius + ", area=" + calculateArea() + " }";
    }
}
