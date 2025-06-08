package de.fhkiel.oop.sketch.state

import de.fhkiel.oop.model.BaseShape

/**
 * Holds the shapes to draw each frame.
 *
 * @property shapes List of shapes, may be empty or null.
 *
 * @author Simon Wessel
 */
class ShapeState {
    private var _shapes: List<BaseShape>? = emptyList()

    /**
     * Returns the list of shapes.
     *
     * @return current shapes, or null.
     */
    var shapes: List<BaseShape>?
        get() = _shapes
        /**
         * Sets the list of shapes.
         */
        set(value) {
            _shapes = value
        }
}
