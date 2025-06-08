package de.fhkiel.oop.model


/**
 * Represents an axis‐aligned bounding box (AABB) in **world‐space** or **screen‐space**.
 * An AABB is a rectangle aligned with the X‐ and Y‐axes that fully encloses a shape.
 * It is defined by its top‐left corner `(x, y)` as origin and its `width` and `height`.
 *
 * This class is used both for:
 *  - **World‐space** bounding boxes (used by shapes for hit‐testing and handle placement).
 *  - **Screen‐space** bounding boxes (returned by `BaseShape.screenBoundingBox(mapper)`).
 *
 * @property origin The top‐left corner of the bounding box, represented as a [Vector2D].
 * @property width  The width of the bounding box (in the same unit as x/y).
 * @property height The height of the bounding box (in the same unit as x/y).
 *
 * @author Simon Wessel
 */
data class BoundingBox(
    val origin: Vector2D,
    val width: Float,
    val height: Float
)
