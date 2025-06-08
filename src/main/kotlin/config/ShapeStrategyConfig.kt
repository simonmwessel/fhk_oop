package de.fhkiel.oop.config

import de.fhkiel.oop.strategy.handle.CornerHandleStrategy
import de.fhkiel.oop.strategy.handle.EdgeHandleStrategy
import de.fhkiel.oop.strategy.handle.HandleStrategy
import de.fhkiel.oop.strategy.move.CanvasBoundsMoveConstraint
import de.fhkiel.oop.strategy.move.MoveConstraintStrategy
import de.fhkiel.oop.strategy.resize.CircleResizeStrategy
import de.fhkiel.oop.strategy.resize.RectangleResizeStrategy
import de.fhkiel.oop.strategy.resize.ResizeStrategy
import de.fhkiel.oop.strategy.resize.SquareResizeStrategy

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
    val moveConstraint: MoveConstraintStrategy,
    val resizeStrategy: ResizeStrategy,
) {
    companion object {

        /**
         * Default configuration for circle shapes.
         * Uses [EdgeHandleStrategy] to place handles at edge midpoints.
         * Uses [CanvasBoundsMoveConstraint] to restrict movement to canvas bounds.
         * Uses [CircleResizeStrategy] for resizing, maintaining aspect ratio.
         */
        val CIRCLE = ShapeStrategyConfig(
            handleStrategy = EdgeHandleStrategy,
            moveConstraint = CanvasBoundsMoveConstraint,
            resizeStrategy = CircleResizeStrategy
        )

        /**
         * Default configuration for rectangle shapes.
         * Uses [CornerHandleStrategy] to place handles at the corners.
         * Uses [CanvasBoundsMoveConstraint] to restrict movement to canvas bounds.
         * Uses [RectangleResizeStrategy] for resizing.
         */
        val RECTANGLE = ShapeStrategyConfig(
            handleStrategy = CornerHandleStrategy,
            moveConstraint = CanvasBoundsMoveConstraint,
            resizeStrategy = RectangleResizeStrategy
        )

        /**
         * Default configuration for square shapes.
         * Uses [CornerHandleStrategy] to place handles at the corners.
         * Uses [CanvasBoundsMoveConstraint] to restrict movement to canvas bounds.
         * Uses [SquareResizeStrategy] for resizing, maintaining aspect ratio.
         */
        val SQUARE = ShapeStrategyConfig(
            handleStrategy = CornerHandleStrategy,
            moveConstraint = CanvasBoundsMoveConstraint,
            resizeStrategy = SquareResizeStrategy
        )
    }
}
