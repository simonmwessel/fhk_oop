package de.fhkiel.oop.strategy.resize

import de.fhkiel.oop.shapes.Rectangle
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.Vector2D
import de.fhkiel.oop.model.BoundingBox
import de.fhkiel.oop.config.Config

/**
 * Resize strategy for [Rectangle] shapes.
 *
 * Supports independent axis resizing by dragging any corner handle
 * (ordered: TL=0, TR=1, BL=2, BR=3 as per [de.fhkiel.oop.strategy.handle.CornerHandleStrategy]).
 *
 * @see ResizeStrategy
 * @see de.fhkiel.oop.strategy.handle.CornerHandleStrategy
 */
object RectangleResizeStrategy : ResizeStrategy {
    override fun resize(
        shape: BaseShape,
        handleIndex: Int,
        initialBoundingBox: BoundingBox,
        initialOrigin: Vector2D,
        worldMouse: Vector2D
    ) {
        val rect = shape as? Rectangle ?: return
        var newX      = initialOrigin.x
        var newY      = initialOrigin.y
        var newW      = initialBoundingBox.width
        var newH      = initialBoundingBox.height
        val origX     = initialBoundingBox.origin.x
        val origY     = initialBoundingBox.origin.y

        when (handleIndex) {
            // Top‐Left
            0 -> {
                newX = worldMouse.x
                newY = worldMouse.y
                newW = initialBoundingBox.width  + (origX - worldMouse.x)
                newH = initialBoundingBox.height + (origY - worldMouse.y)
            }
            // Top‐Right
            1 -> {
                newY = worldMouse.y
                newW = worldMouse.x - origX
                newH = initialBoundingBox.height + (origY - worldMouse.y)
            }
            // Bottom‐Left
            2 -> {
                newX = worldMouse.x
                newW = initialBoundingBox.width + (origX - worldMouse.x)
                newH = worldMouse.y - origY
            }
            // Bottom‐Right
            3 -> {
                newW = worldMouse.x - origX
                newH = worldMouse.y - origY
            }
        }

        // Enforce valid ranges
        if      (newW < Config.MIN_RECT_WIDTH) newW = Config.MIN_RECT_WIDTH
        else if (newW > Config.MAX_RECT_WIDTH) newW = Config.MAX_RECT_WIDTH

        if      (newH < Config.MIN_RECT_HEIGHT) newH = Config.MIN_RECT_HEIGHT
        else if (newH > Config.MAX_RECT_HEIGHT) newH = Config.MAX_RECT_HEIGHT

        // Apply changes
        rect.origin = Vector2D(newX, newY)
        rect.width  = newW
        rect.height = newH

        rect.origin = shape.strategies.constraints?.fold(Vector2D(newX, newY)) { accOrigin, strategy ->
            strategy.clampOrigin(accOrigin, rect)
        } ?: Vector2D(newX, newY)
    }
}
