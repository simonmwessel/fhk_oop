package de.fhkiel.oop.strategy.handle

import de.fhkiel.oop.model.Point
import de.fhkiel.oop.model.BoundingBox

/**
 * Places selection handles at the **four corners** of the bounding box:
 * top‐left (TL), top‐right (TR), bottom‐left (BL), bottom‐right (BR).
 *
 * Handle order is consistently:
 *  1. Top‐Left
 *  2. Top‐Right
 *  3. Bottom‐Left
 *  4. Bottom‐Right
 *
 * These points are returned in **world coordinates**. The caller is responsible
 * for mapping them into screen coordinates via a [de.fhkiel.oop.mapper.CoordinateMapper].
 *
 * @author Simon Wessel
 *
 * @see HandleStrategy
 * @see EdgeHandleStrategy
 */
object CornerHandleStrategy : HandleStrategy {
    /**
     * {@inheritDoc}
     *
     * Returns a list of four [Point] instances:
     *  - `(bbox.x,               bbox.y)`               -> top‐left
     *  - `(bbox.x + bbox.width,  bbox.y)`               -> top‐right
     *  - `(bbox.x,               bbox.y + bbox.height)` -> bottom‐left
     *  - `(bbox.x + bbox.width,  bbox.y + bbox.height)` -> bottom‐right
     *
     * @param bbox The axis‐aligned bounding box of a shape (world units).
     *
     * @return A [List] of four [Point]s in the specified order (TL, TR, BL, BR).
     */
    override fun handlePoints(bbox: BoundingBox): List<Point> =
        listOf(
            Point(bbox.x, bbox.y),               // TL
            Point(bbox.x + bbox.width, bbox.y),               // TR
            Point(bbox.x, bbox.y + bbox.height), // BL
            Point(bbox.x + bbox.width, bbox.y + bbox.height)  // BR
        )
}
