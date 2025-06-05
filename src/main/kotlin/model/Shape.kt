package de.fhkiel.oop.model

import de.fhkiel.oop.mapper.CoordinateMapper
import de.fhkiel.oop.model.select.BoundingBox
import de.fhkiel.oop.model.select.HandleStrategy
import processing.core.PApplet

/**
 * Sealed **interface** that defines the public contract for every drawable
 * 2‑D shape. Only the *direct* sub‑types of a sealed interface must live in the same
 * package.
 *
 * @property origin The geometric anchor point of the shape.
 * @property style  The visual appearance of the shape (fill/stroke colours & stroke weight).
 *
 * @see Style
 * @see Point
 * @see BaseShape
 *
 * @author  Simon Wessel
 * @version 1.0
 * @since   2.5
 */
sealed interface Shape {

    /** Geometric anchor point – semantics depend on the concrete subtype. */
    var origin: Point

    /** Visual appearance (fill/stroke colours & stroke weight). */
    var style: Style

    /** Mathematical area of the shape (square units). */
    fun getArea(): Float

    /**
     * Draws the shape on the given [PApplet] using the provided [CoordinateMapper].
     *
     * @param g      The PApplet to draw on.
     * @param mapper The coordinate mapper to use for drawing.
     */
    fun draw(g: PApplet, mapper: CoordinateMapper)

    /**
     * Computes the minimal axis‐aligned bounding box enclosing this shape.
     *
     * @return the bounding box
     */
    fun boundingBox(): BoundingBox

    /**
     * Computes the screen bounding box of this shape using the provided [CoordinateMapper].
     *
     * This method converts the world coordinates of the shape to screen coordinates
     * and returns the bounding box in screen space.
     *
     * @param mapper The coordinate mapper to convert world coordinates to screen coordinates.
     *
     * @return The bounding box in screen space.
     */
    fun screenBoundingBox(mapper: CoordinateMapper): BoundingBox

    /**
     * Checks if the given screen coordinates (mx, my) are within the shape.
     *
     * This method uses the provided [CoordinateMapper] to convert the screen coordinates
     * to world coordinates before performing the hit test.
     *
     * @param mapper The coordinate mapper to convert screen coordinates to world coordinates.
     * @param mx The x-coordinate in screen space.
     * @param my The y-coordinate in screen space.
     *
     * @return `true` if the point is within the shape, `false` otherwise.
     */
    fun hitTestScreen(mapper: CoordinateMapper, mx: Float, my: Float): Boolean

    /**
     * Configures the handle strategy for this shape.
     * [de.fhkiel.oop.model.select.CornerHandleStrategy] for handling corner points,
     * [de.fhkiel.oop.model.select.EdgeHandleStrategy] for handling edge points,
     *
     * @return the handle strategy to use for this shape
     */
    fun handleStrategy(): HandleStrategy
}
