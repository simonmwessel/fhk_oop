package de.fhkiel.oop.config

import de.fhkiel.oop.strategy.handle.CornerHandleStrategy
import de.fhkiel.oop.strategy.handle.EdgeHandleStrategy
import de.fhkiel.oop.strategy.handle.HandleStrategy
import de.fhkiel.oop.strategy.constraint.CanvasBoundsConstraint
import de.fhkiel.oop.strategy.constraint.ConstraintStrategy
import de.fhkiel.oop.strategy.resize.CircleResizeStrategy
import de.fhkiel.oop.strategy.resize.RectangleResizeStrategy
import de.fhkiel.oop.strategy.resize.ResizeStrategy
import de.fhkiel.oop.strategy.resize.SquareResizeStrategy

/**
 * Configuration for shape strategies, defining how selection handles are placed
 * around shapes like circles and rectangles.
 *
 * @property handleStrategy The strategy used to determine handle positions.
 * @property resizeStrategy The strategy used for resizing the shape.
 * @property constraints    Optional list of constraints applied during movement or resizing.
 *
 * @author Simon Wessel
 */
data class ShapeStrategyConfig(
    val handleStrategy: HandleStrategy,
    val resizeStrategy: ResizeStrategy,
    val constraints:    List<ConstraintStrategy>?
) {
    companion object {

        /**
         * Default configuration for circle shapes.
         * Uses [EdgeHandleStrategy] to place handles at edge midpoints.
         * Uses [CircleResizeStrategy] for resizing, maintaining aspect ratio.
         * Uses [CanvasBoundsConstraint] to restrict movement to canvas bounds.
         */
        val CIRCLE = ShapeStrategyConfig(
            handleStrategy = EdgeHandleStrategy,
            resizeStrategy = CircleResizeStrategy,
            constraints    = listOf(CanvasBoundsConstraint)
        )

        /**
         * Default configuration for rectangle shapes.
         * Uses [CornerHandleStrategy] to place handles at the corners.
         * Uses [RectangleResizeStrategy] for resizing.
         * Uses [CanvasBoundsConstraint] to restrict movement to canvas bounds.
         */
        val RECTANGLE = ShapeStrategyConfig(
            handleStrategy = CornerHandleStrategy,
            resizeStrategy = RectangleResizeStrategy,
            constraints    = listOf(CanvasBoundsConstraint)
        )

        /**
         * Default configuration for square shapes.
         * Uses [CornerHandleStrategy] to place handles at the corners.
         * Uses [SquareResizeStrategy] for resizing, maintaining aspect ratio.
         * Uses [CanvasBoundsConstraint] to restrict movement to canvas bounds.
         */
        val SQUARE = ShapeStrategyConfig(
            handleStrategy = CornerHandleStrategy,
            resizeStrategy = SquareResizeStrategy,
            constraints    = listOf(CanvasBoundsConstraint)
        )
    }
}
