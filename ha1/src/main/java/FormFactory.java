package main.java;

/**
 * FormFactory class that creates an array of random shapes.
 *
 * @author Simon Wessel
 * @version 1.0
 * @since 1.3
 */
public class FormFactory {

    /**
     * Produces an array of random shapes.
     *
     * @param count the number of shapes to produce
     * @return an array of random shapes
     */
    public Shape[] produce(int count) {
        Shape[] shapes = new Shape[count];
        for (int i = 0; i < count; i++) {
            double random = Math.random();
            if (random < 0.33) {
                shapes[i] = new Circle();
            } else if (random < 0.66) {
                shapes[i] = new Rectangle();
            } else {
                shapes[i] = new Square();
            }
        }
        return shapes;
    }
}