package de.fhkiel.oop.config

import de.fhkiel.oop.strategy.handle.CornerHandleStrategy
import de.fhkiel.oop.strategy.handle.EdgeHandleStrategy
import de.fhkiel.oop.strategy.handle.HandleStrategy
import de.fhkiel.oop.strategy.move.CanvasBoundsMoveConstraint
import de.fhkiel.oop.strategy.move.MoveConstraintStrategy

/**
 * Configuration for shape strategies, defining how selection handles are placed
 * around shapes like circles and rectangles.
 *
 * @property handleStrategy The strategy used to determine handle positions.
 *
 * @author Simon Wessel
 */
data class ShapeStrategyConfig(
    val handleStrategy: HandleStrategy,
    val moveConstraint: MoveConstraintStrategy
) {
    companion object {

        /**
         * Default configuration for circle shapes.
         * Uses [EdgeHandleStrategy] to place handles at edge midpoints.
         */
        val CIRCLE = ShapeStrategyConfig(
            handleStrategy = EdgeHandleStrategy,
            moveConstraint = CanvasBoundsMoveConstraint
        )

        /**
         * Default configuration for rectangle shapes.
         * Uses [CornerHandleStrategy] to place handles at the corners.
         */
        val RECTANGLE = ShapeStrategyConfig(
            handleStrategy = CornerHandleStrategy,
            moveConstraint = CanvasBoundsMoveConstraint
        )
    }
}
