package main.java;

public class Square {
    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    private Point topLeft;

    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    private double sideLength;

    public Square() {
        this.topLeft = new Point(0,0);
        this.sideLength = 1;
    }

    public Square(Point topLeft, double side) {
        this.topLeft = topLeft;
        this.sideLength = side;
    }

    public static Square fromArea(Point topLeft, double area) {
        return new Square(topLeft, Math.sqrt(area));
    }

    public double calculateArea() {
        return this.sideLength * this.sideLength;
    }

    public String toString() {
        return "Square { topLeft=" + topLeft + ", side=" + sideLength + ", area=" + calculateArea() + " }";
    }
}
