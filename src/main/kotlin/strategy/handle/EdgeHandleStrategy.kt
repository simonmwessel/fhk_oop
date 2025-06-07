package de.fhkiel.oop.strategy.handle

import de.fhkiel.oop.model.Vector2D
import de.fhkiel.oop.model.BoundingBox

/**
 * Places selection handles at the **midpoints of each edge** of the bounding box:
 *
 *  1. North (top edge midpoint)
 *  2. South (bottom edge midpoint)
 *  3. West  (left edge midpoint)
 *  4. East  (right edge midpoint)
 *
 * The returned vectors are in **world coordinates**. The caller can then convert
 * them to screen coordinates via a [de.fhkiel.oop.mapper.CoordinateMapper].
 *
 * @author Simon Wessel
 *
 * @see HandleStrategy
 * @see CornerHandleStrategy
 */
object EdgeHandleStrategy : HandleStrategy {
    /**
     * {@inheritDoc}
     *
     * Let `cx = bbox.x + bbox.width / 2f` be the horizontal center,
     * and `cy = bbox.y + bbox.height / 2f` be the vertical center of `bbox`.
     * Then returns the four vectors in the order:
     *  1. North `Vector2D(cx, bbox.y)`
     *  2. South `Vector2D(cx, bbox.y + bbox.height)`
     *  3. West  `Vector2D(bbox.x, cy)`
     *  4. East  `Vector2D(bbox.x + bbox.width, cy)`
     *
     * @param bbox The axis‐aligned bounding box of a shape (in world units).
     * @return A [List] of four [Vector2D]s (N, S, W, E).
     */
    override fun handleVectors(bbox: BoundingBox): List<Vector2D> {
        val cx = bbox.x + bbox.width  / 2f
        val cy = bbox.y + bbox.height / 2f
        return listOf(
            Vector2D(cx, bbox.y),               // N
            Vector2D(cx, bbox.y + bbox.height), // S
            Vector2D(bbox.x, cy),               // W
            Vector2D(bbox.x + bbox.width, cy)   // E
        )
    }
}
