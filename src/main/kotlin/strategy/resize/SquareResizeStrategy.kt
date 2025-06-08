package de.fhkiel.oop.strategy.resize

import de.fhkiel.oop.shapes.Square
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.Vector2D
import de.fhkiel.oop.model.BoundingBox
import de.fhkiel.oop.config.Config
import kotlin.math.abs
import kotlin.math.max

/**
 * Resize strategy for [Square] shapes.
 *
 * When a corner handle is dragged, this strategy moves the dragged corner
 * and keeps the opposite corner fixed ("sticky"), preserving a 1:1 aspect ratio.
 * Uses Config.MIN_SQUARE_SIDE and Config.MAX_SQUARE_SIDE to clamp the new side length.
 *
 * @see ResizeStrategy
 */
object SquareResizeStrategy : ResizeStrategy {
    override fun resize(
        shape: BaseShape,
        handleIndex: Int,
        initialBoundingBox: BoundingBox,
        initialOrigin: Vector2D,
        worldMouse: Vector2D
    ) {
        val square = shape as? Square ?: return

        val side = initialBoundingBox.width
        val origX = initialBoundingBox.origin.x
        val origY = initialBoundingBox.origin.y

        // Determine the opposite (sticky) corner
        val anchorX = when (handleIndex) {
            0, 2 -> origX + side // TL & BL => anchor on right
            1, 3 -> origX        // TR & BR => anchor on left
            else -> origX
        }
        val anchorY = when (handleIndex) {
            0, 1 -> origY + side // TL & TR => anchor on bottom
            2, 3 -> origY        // BL & BR => anchor on top
            else -> origY
        }

        // Compute desired side length based on mouse position
        val dx = worldMouse.x - anchorX
        val dy = worldMouse.y - anchorY
        val rawSide = max(abs(dx), abs(dy))

        // Clamp side to configured min/max
        val newSide = rawSide.coerceIn(Config.MIN_SQUARE_SIDE, Config.MAX_SQUARE_SIDE)

        // Compute new top-left origin
        val newOriginX = if (anchorX <= origX) anchorX else anchorX - newSide
        val newOriginY = if (anchorY <= origY) anchorY else anchorY - newSide

        // Apply resized values
        square.width  = newSide
        square.height = newSide

        // Apply constraints
        square.origin = shape.strategies.constraints?.fold(Vector2D(newOriginX, newOriginY)) { accOrigin, strategy ->
            strategy.clampOrigin(accOrigin, square)
        } ?: Vector2D(newOriginX, newOriginY)
    }
}
