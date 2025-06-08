package de.fhkiel.oop.strategy.resize

import de.fhkiel.oop.shapes.Circle
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.Vector2D
import de.fhkiel.oop.model.BoundingBox
import de.fhkiel.oop.config.Config
import de.fhkiel.oop.strategy.move.CanvasBoundsMoveConstraint
import kotlin.math.hypot

/**
 * Resize strategy for [Circle] shapes.
 *
 * When any edge‐handle is dragged, updates the circle’s radius so that
 * the dragged point remains on its perimeter, preserving circularity.
 *
 * @see ResizeStrategy
 * @see de.fhkiel.oop.mapper.CoordinateMapper
 */
object CircleResizeStrategy : ResizeStrategy {
    override fun resize(
        shape: BaseShape,
        handleIndex: Int,
        initialBoundingBox: BoundingBox,
        initialOrigin: Vector2D,
        worldMouse: Vector2D
    ) {
        val circle = shape as? Circle ?: return
        // Compute new radius = distance from center to current mouse
        val dx = worldMouse.x - circle.origin.x
        val dy = worldMouse.y - circle.origin.y
        var newRad = hypot(dx.toDouble(), dy.toDouble()).toFloat()

        if      (newRad < Config.MIN_CIRCLE_RADIUS) newRad = Config.MIN_CIRCLE_RADIUS
        else if (newRad > Config.MAX_CIRCLE_RADIUS) newRad = Config.MAX_CIRCLE_RADIUS

        circle.radius = newRad

        circle.origin = CanvasBoundsMoveConstraint
            .clampOrigin(circle.origin, circle)
    }
}
