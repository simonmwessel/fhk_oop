package de.fhkiel.oop.strategy.handle

import de.fhkiel.oop.model.Vector2D
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
 * These vectors are returned in **world coordinates**. The caller is responsible
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
     * Returns a list of four [Vector2D] instances:
     *  - `(bbox.x,               bbox.y)`               -> top‐left
     *  - `(bbox.x + bbox.width,  bbox.y)`               -> top‐right
     *  - `(bbox.x,               bbox.y + bbox.height)` -> bottom‐left
     *  - `(bbox.x + bbox.width,  bbox.y + bbox.height)` -> bottom‐right
     *
     * @param bbox The axis‐aligned bounding box of a shape (world units).
     *
     * @return A [List] of four [Vector2D]s in the specified order (TL, TR, BL, BR).
     */
    override fun handleVectors(bbox: BoundingBox): List<Vector2D> =
        listOf(
            Vector2D(bbox.x, bbox.y),               // TL
            Vector2D(bbox.x + bbox.width, bbox.y),               // TR
            Vector2D(bbox.x, bbox.y + bbox.height), // BL
            Vector2D(bbox.x + bbox.width, bbox.y + bbox.height)  // BR
        )
}
