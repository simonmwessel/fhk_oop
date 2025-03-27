package main.java;

public class Rectangle {
    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    private Point topLeft;

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    private double width;

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    private double height;

    public Rectangle() {
        this.topLeft = new Point(0,0);
        this.width = 1;
        this.height = 1;
    }

    public Rectangle(Point topLeft, double width, double height) {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    public static Rectangle fromArea(Point topLeft, double area, double length) {
        return new Rectangle(topLeft, area / length, length);
    }

    public double calculateArea() {
        return this.width * this.height;
    }

    public String toString() {
        return "Rectangle { topLeft=" + topLeft + ", width=" + width + ", height=" + height + ", area=" + calculateArea() + " }";
    }
}
