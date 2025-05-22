package de.fhkiel.oop.model

/**
 * Represents an axis-aligned bounding box (AABB).
 * An AABB is a rectangle aligned with the coordinate axes that completely encloses a shape.
 * It is defined by its minimum x and y coordinates (the top-left corner) and its width and height.
 *
 * @property x The x-coordinate of the top-left corner of the bounding box.
 * @property y The y-coordinate of the top-left corner of the bounding box.
 * @property width The width of the bounding box.
 * @property height The height of the bounding box.
 *
 * @author  Simon Wessel
 * @version 1.0
 * @since   2.8
 */
data class BoundingBox(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float
)
