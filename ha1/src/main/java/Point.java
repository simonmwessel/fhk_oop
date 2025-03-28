package main.java;

public class Point {
    float x, y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + "|" + y + ")";
    }
}
