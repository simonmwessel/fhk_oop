package de.fhkiel.oop.strategy.move

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.Point

/**
 * A [MoveConstraintStrategy] that clamps a shape’s origin so that its
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
object CanvasBoundsMoveConstraint : MoveConstraintStrategy {
    override fun clampOrigin(desiredOrigin: Point, shape: BaseShape): Point {
        val newBox = shape.boundingBoxAt(desiredOrigin)

        val dx = when {
            newBox.x < 0f                           -> -newBox.x
            newBox.x + newBox.width > Config.MAX_X  -> Config.MAX_X - (newBox.x + newBox.width)
            else                                    -> 0f
        }
        val dy = when {
            newBox.y < 0f                           -> -newBox.y
            newBox.y + newBox.height > Config.MAX_Y -> Config.MAX_Y - (newBox.y + newBox.height)
            else                                    -> 0f
        }

        return Point(desiredOrigin.x + dx, desiredOrigin.y + dy)
    }
}
