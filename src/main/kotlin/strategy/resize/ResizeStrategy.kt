package de.fhkiel.oop.strategy.resize


import de.fhkiel.oop.config.AppConfig
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.Vector2D
import de.fhkiel.oop.model.BoundingBox

/**
 * Strategy interface defining how a shape should be resized when one of its handles is dragged.
 *
 * @see CircleResizeStrategy
 * @see SquareResizeStrategy
 * @see RectangleResizeStrategy
 */
fun interface ResizeStrategy {
    /**
     * Resize the given [shape] based on dragging handle [handleIndex].
     *
     * @param shape              The shape to resize.
     * @param handleIndex        Index of the handle being dragged (ordered as per its HandleStrategy).
     * @param initialBoundingBox The shape’s bounding box in world‐space at drag start.
     * @param initialOrigin      The shape’s origin in world‐space at drag start.
     * @param worldMouse         Current mouse position in world coordinates.
     */
    fun resize(
        config: AppConfig,
        shape: BaseShape,
        handleIndex: Int,
        initialBoundingBox: BoundingBox,
        initialOrigin: Vector2D,
        worldMouse: Vector2D
    )
}
