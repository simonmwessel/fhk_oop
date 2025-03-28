package main.java;

public class Rectangle {
    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    private Point topLeft;

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    private float width;

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    private float height;

    public Rectangle() {
        this.topLeft = new Point(0,0);
        this.width = 1;
        this.height = 1;
    }

    public Rectangle(Point topLeft, float width, float height) {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    public static Rectangle fromArea(Point topLeft, float area, float length) {
        return new Rectangle(topLeft, area / length, length);
    }

    public float calculateArea() {
        return this.width * this.height;
    }

    public String toString() {
        return "Rectangle { topLeft=" + topLeft + ", width=" + width + ", height=" + height + ", area=" + calculateArea() + " }";
    }
}
