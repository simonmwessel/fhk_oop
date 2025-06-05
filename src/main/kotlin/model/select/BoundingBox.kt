package de.fhkiel.oop.model.select


/**
 * Represents an axis‐aligned bounding box (AABB) in **world‐space** or **screen‐space**.
 * An AABB is a rectangle aligned with the X‐ and Y‐axes that fully encloses a shape.
 * It is defined by its top‐left corner `(x, y)` and its `width` and `height`.
 *
 * This class is used both for:
 *  - **World‐space** bounding boxes (used by shapes for hit‐testing and handle placement).
 *  - **Screen‐space** bounding boxes (returned by `BaseShape.screenBoundingBox(mapper)`).
 *
 * @property x      The X‐coordinate of the top‐left corner (in world or pixel units).
 * @property y      The Y‐coordinate of the top‐left corner (in world or pixel units).
 * @property width  The width of the bounding box (in the same unit as x/y).
 * @property height The height of the bounding box (in the same unit as x/y).
 *
 * @author Simon Wessel
 */
data class BoundingBox(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float
)
