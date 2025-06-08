package de.fhkiel.oop.strategy.constraint

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.Vector2D

/**
 * A [ConstraintStrategy] that clamps a shape’s origin so that its
 * world‐space bounding box (including half the stroke border on each side)
 * never exceeds the canvas limits: `[0..MAX_X] × [0..MAX_Y]`.
 *
 * It queries the raw geometric box via [BaseShape.boundingBoxAt],
 * expands it by **style.weight/2** to include the stroke border,
 * and then adjusts the origin to ensure:
 *  - `box.x >= 0`, `box.y >= 0`
 *  - `box.x + box.width <= Config.MAX_X`
 *  - `box.y + box.height <= Config.MAX_Y`
 *
 * @author
 * @see BaseShape.boundingBoxAt
 * @see Config.MAX_X, Config.MAX_Y
 */
object CanvasBoundsConstraint : ConstraintStrategy {
    override fun clampOrigin(desiredOrigin: Vector2D, shape: BaseShape): Vector2D {
        // TODO: Bounding box mapping fix for correct clamping with relative mapper
        val newBox = shape.boundingBoxAt(desiredOrigin)
        var newX = desiredOrigin.x
        var newY = desiredOrigin.y

        if (newBox.width > Config.MAX_X) {
            newX = (Config.MAX_X - newBox.width) / 2f - newBox.origin.x + desiredOrigin.x
        } else {
            if (newBox.origin.x < 0) newX -= newBox.origin.x
            if (newBox.origin.x + newBox.width > Config.MAX_X) {
                newX -= (newBox.origin.x + newBox.width - Config.MAX_X)
            }
        }

        if (newBox.height > Config.MAX_Y) {
            newY = (Config.MAX_Y - newBox.height) / 2f - newBox.origin.y + desiredOrigin.y
        } else {
            if (newBox.origin.y < 0) newY -= newBox.origin.y
            if (newBox.origin.y + newBox.height > Config.MAX_Y) {
                newY -= (newBox.origin.y + newBox.height - Config.MAX_Y)
            }
        }

        return Vector2D(newX, newY)
    }
}
