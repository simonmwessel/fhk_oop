package de.fhkiel.oop.strategy.constraint

import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.Vector2D

interface ConstraintStrategy {
    /**
     * Decides *where* a shape is allowed to move. Given a raw target origin,
     * the strategy returns a possibly adjusted origin so that the shape’s
     * bounding box (as reported by boundingBoxAt) never violates its rules.
     *
     * @param desiredOrigin  The candidate origin in world coordinates.
     * @param shape          The concrete shape to be moved.
     * @return               A clamped/adjusted origin for that shape.
     */
    fun clampOrigin(desiredOrigin: Vector2D, shape: BaseShape): Vector2D
}
