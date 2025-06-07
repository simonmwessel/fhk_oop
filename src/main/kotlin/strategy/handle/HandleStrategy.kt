package de.fhkiel.oop.strategy.handle

import de.fhkiel.oop.model.Vector2D
import de.fhkiel.oop.model.BoundingBox

/**
 * Strategy interface that defines **where selection handles** should be placed
 * for a given world‐space [BoundingBox]. A handle is a small square or marker
 * drawn at specific vectors around a shape (e.g., corners or edge‐midpoints).
 *
 * Clients ask for a list of handle‐vectors in **world coordinates**; they can then
 * convert those vectors into screen coordinates via a [de.fhkiel.oop.mapper.CoordinateMapper].
 *
 * Implementations include:
 *  - [CornerHandleStrategy]: returns four corner vectors (top‐left, top‐right, bottom‐left, bottom‐right).
 *  - [EdgeHandleStrategy]: returns four edge‐midpoint vectors (north, south, west, east).
 *
 * @author Simon Wessel
 *
 * @see CornerHandleStrategy
 * @see EdgeHandleStrategy
 */
fun interface HandleStrategy {
    /**
     * Returns all world‐space vectors at which handles should be drawn.
     *
     * @param bbox The axis‐aligned bounding box of a shape (in world units).
     * @return A [List] of [Vector2D] objects representing handle positions in world space.
     *
     * @see BoundingBox
     */
    fun handleVectors(bbox: BoundingBox): List<Vector2D>
}
