package de.fhkiel.oop.strategy.handle

import de.fhkiel.oop.model.Point
import de.fhkiel.oop.model.BoundingBox

/**
 * Strategy interface that defines **where selection handles** should be placed
 * for a given world‐space [BoundingBox]. A handle is a small square or marker
 * drawn at specific points around a shape (e.g., corners or edge‐midpoints).
 *
 * Clients ask for a list of handle‐points in **world coordinates**; they can then
 * convert those points into screen coordinates via a [de.fhkiel.oop.mapper.CoordinateMapper].
 *
 * Implementations include:
 *  - [CornerHandleStrategy]: returns four corner points (top‐left, top‐right, bottom‐left, bottom‐right).
 *  - [EdgeHandleStrategy]: returns four edge‐midpoint points (north, south, west, east).
 *
 * @author Simon Wessel
 *
 * @see CornerHandleStrategy
 * @see EdgeHandleStrategy
 */
fun interface HandleStrategy {
    /**
     * Returns all world‐space points at which handles should be drawn.
     *
     * @param bbox The axis‐aligned bounding box of a shape (in world units).
     * @return A [List] of [Point] objects representing handle positions in world space.
     *
     * @see BoundingBox
     */
    fun handlePoints(bbox: BoundingBox): List<Point>
}
