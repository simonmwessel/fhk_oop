package de.fhkiel.oop.strategy.constraint

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
 *  - `box.x + box.width <= config.maxX`
 *  - `box.y + box.height <= config.maxX`
 *
 * @author
 * @see BaseShape.boundingBoxAt
 * @see de.fhkiel.oop.config.AppConfig.maxX
 * @see de.fhkiel.oop.config.AppConfig.maxY
 */
object CanvasBoundsConstraint : ConstraintStrategy {
    override fun clampOrigin(desiredOrigin: Vector2D, shape: BaseShape): Vector2D {
        // TODO: Bounding box mapping fix for correct clamping with relative mapper
        val newBox = shape.boundingBoxAt(desiredOrigin)
        var newX = desiredOrigin.x
        var newY = desiredOrigin.y
        val config = shape.config

        if (newBox.width > config.maxX) {
            newX = (config.maxX - newBox.width) / 2f - newBox.origin.x + desiredOrigin.x
        } else {
            if (newBox.origin.x < 0) newX -= newBox.origin.x
            if (newBox.origin.x + newBox.width > config.maxX) {
                newX -= (newBox.origin.x + newBox.width - config.maxX)
            }
        }

        if (newBox.height > config.maxY) {
            newY = (config.maxY - newBox.height) / 2f - newBox.origin.y + desiredOrigin.y
        } else {
            if (newBox.origin.y < 0) newY -= newBox.origin.y
            if (newBox.origin.y + newBox.height > config.maxY) {
                newY -= (newBox.origin.y + newBox.height - config.maxY)
            }
        }

        return Vector2D(config, newX, newY)
    }
}
