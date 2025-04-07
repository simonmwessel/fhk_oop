package main.java;

/**
 * Main class to demonstrate the functionality of the Circle, Rectangle, and Square classes.
 *
 * @author Simon Wessel
 * @version 1.2
 * @since 1.0
 */
public class Main {
    /**
     * Main method to demonstrate the functionality of the classes.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Circles
        Circle circle1 = new Circle();
        Circle circle2 = new Circle(new Point(0,0), 5);
        Circle circle3 = Circle.fromArea(new Point(0,0), 50);

        System.out.println("Circle 1: " + circle1);
        System.out.println("Circle 2: " + circle2);
        System.out.println("Circle 3: " + circle3);

        System.out.println();

        // Rectangles
        Rectangle rectangle1 = new Rectangle();
        Rectangle rectangle2 = new Rectangle(new Point(0,0), 5, 10);
        Rectangle rectangle3 = Rectangle.fromArea(new Point(0,0), 50, 5);

        System.out.println("Rectangle 1: " + rectangle1);
        System.out.println("Rectangle 2: " + rectangle2);
        System.out.println("Rectangle 3: " + rectangle3);

        System.out.println();

        // Squares
        Square square1 = new Square();
        Square square2 = new Square(new Point(0,0), 5);
        Square square3 = Square.fromArea(new Point(0,0), 25);

        System.out.println("Square 1: " + square1);
        System.out.println("Square 2: " + square2);
        System.out.println("Square 3: " + square3);
    }
}
