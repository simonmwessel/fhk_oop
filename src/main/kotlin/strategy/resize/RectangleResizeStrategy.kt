package de.fhkiel.oop.strategy.resize

import de.fhkiel.oop.config.AppConfig
import de.fhkiel.oop.shapes.Rectangle
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.Vector2D
import de.fhkiel.oop.model.BoundingBox

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
        config: AppConfig,
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

        // TODO: into own constraint strategy?
        // Enforce valid ranges
        if      (newW < config.minRectWidth) newW = config.minRectWidth
        else if (newW > config.maxRectWidth) newW = config.maxRectWidth

        if      (newH < config.minRectHeight) newH = config.minRectHeight
        else if (newH > config.maxRectHeight) newH = config.maxRectHeight

        // Apply changes
        rect.origin = Vector2D(config, newX, newY)
        rect.width  = newW
        rect.height = newH

        rect.origin = shape.strategies.constraints?.fold(Vector2D(config, newX, newY)) { accOrigin, strategy ->
            strategy.clampOrigin(accOrigin, rect)
        } ?: Vector2D(config, newX, newY)
    }
}
