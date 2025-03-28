package main.java;

public class Square {
    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    private Point topLeft;

    public float getSideLength() {
        return sideLength;
    }

    public void setSideLength(float sideLength) {
        this.sideLength = sideLength;
    }

    private float sideLength;

    public Square() {
        this.topLeft = new Point(0,0);
        this.sideLength = 1;
    }

    public Square(Point topLeft, float side) {
        this.topLeft = topLeft;
        this.sideLength = side;
    }

    public static Square fromArea(Point topLeft, float area) {
        return new Square(topLeft, (float)Math.sqrt(area));
    }

    public float calculateArea() {
        return this.sideLength * this.sideLength;
    }

    public String toString() {
        return "Square { topLeft=" + topLeft + ", side=" + sideLength + ", area=" + calculateArea() + " }";
    }
}
