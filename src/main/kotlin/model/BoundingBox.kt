package de.fhkiel.oop.model

import de.fhkiel.oop.Sketch.ResizeMode

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

/**
 * Converts this logical BoundingBox into screen coordinates.
 *
 * @param mode active resize strategy
 * @param sx   horizontal scale (windowW / baseW)
 * @param sy   vertical scale (windowH / baseH)
 * @param us   uniform scale = min(sx, sy)
 * @param offX horizontal offset produced by UNIFORM_SCALE centering
 * @param offY vertical offset produced by UNIFORM_SCALE centering
 */
fun BoundingBox.toScreen(
    mode : ResizeMode,
    sx   : Float,
    sy   : Float,
    us   : Float,
    offX : Float = 0f,
    offY : Float = 0f
): BoundingBox = when (mode) {
    ResizeMode.UNIFORM_SCALE -> BoundingBox(offX + x * us, offY + y * us, width * us, height * us)
    ResizeMode.RELATIVE      -> BoundingBox(       x * sx ,       y * sy, width * us, height * us)
}
