package de.fhkiel.oop.model.select

import de.fhkiel.oop.model.Point

/**
 * Places selection handles at the **midpoints of each edge** of the bounding box:
 *
 *  1. North (top edge midpoint)
 *  2. South (bottom edge midpoint)
 *  3. West  (left edge midpoint)
 *  4. East  (right edge midpoint)
 *
 * The returned points are in **world coordinates**. The caller can then convert
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
     * Then returns the four midpoints in the order:
     *  1. North `Point(cx, bbox.y)`
     *  2. South `Point(cx, bbox.y + bbox.height)`
     *  3. West  `Point(bbox.x, cy)`
     *  4. East  `Point(bbox.x + bbox.width, cy)`
     *
     * @param bbox The axis‐aligned bounding box of a shape (in world units).
     * @return A [List] of four [Point]s (N, S, W, E).
     */
    override fun handlePoints(bbox: BoundingBox): List<Point> {
        val cx = bbox.x + bbox.width  / 2f
        val cy = bbox.y + bbox.height / 2f
        return listOf(
            Point(cx, bbox.y),               // N
            Point(cx, bbox.y + bbox.height), // S
            Point(bbox.x, cy),               // W
            Point(bbox.x + bbox.width, cy)   // E
        )
    }
}
