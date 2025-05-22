package de.fhkiel.oop.model

import processing.core.PApplet

/**
 * Decorator that adds selection behavior to a [BaseShape].
 *
 * This class wraps a [BaseShape] instance and extends its functionality by allowing it to be marked as selected.
 * When a `SelectableShape` is selected, it will draw selection handles (small squares at the corners of its bounding box)
 * in addition to drawing the wrapped shape itself.
 *
 * All other shape properties and behaviors (area calculation, point containment, bounding box, string representation, drawing)
 * are delegated to the wrapped [inner] shape.
 *
 * @property inner The [BaseShape] instance that this `SelectableShape` decorates.
 * @property isSelected A boolean flag indicating whether the shape is currently selected. Defaults to `false`.
 *
 * @constructor Creates a new `SelectableShape` that wraps the specified [BaseShape].
 *
 * @author  Simon Wessel
 * @version 1.0
 * @since   2.8
 *
 * @see BaseShape
 * @see Shape
 * @see Point
 * @see Style
 * @see BoundingBox
 */
class SelectableShape(private val inner: BaseShape) : BaseShape(inner.origin, inner.style) {
    /**
     * Whether this shape is currently selected.
     *
     * This property can be toggled to mark the shape as selected or deselected.
     */
    var isSelected: Boolean = false

    /**
     * Computes the geometric area of the shape.
     *
     * Delegates the computation to the wrapped [inner] shape.
     *
     * @return the area in square‐units
     */
    override fun getArea(): Float =
        inner.getArea()

    /**
     * Draws the shape, and selection handles if selected.
     *
     * Delegates the drawing of the shape to the wrapped [inner] shape.
     * If the shape is selected, small square handles are drawn at the corners of its bounding box.
     *
     * @param g the Processing sketch context
     */
    override fun drawUniform(g: PApplet) {
        inner.drawUniform(g)
        if (isSelected) {
            drawHandles(inner.boundingBox(), g, 1f, 1f, 1f)
        }
    }

    /**
     * Draws the shape using relative scaling, and selection handles if selected.
     *
     * Delegates the drawing of the shape to the wrapped [inner] shape.
     * If the shape is selected, small square handles are drawn at the corners of its bounding box.
     *
     * @param g the Processing sketch context
     * @param scaleX horizontal scaling factor
     * @param scaleY vertical scaling factor
     * @param uniformScale uniform scaling factor (for stroke weights and handle size)
     */
    override fun drawRelative(
        g: PApplet,
        scaleX: Float,
        scaleY: Float,
        uniformScale: Float
    ) {
        inner.drawRelative(g, scaleX, scaleY, uniformScale)
        if (isSelected) {
            drawHandles(inner.boundingBox(), g, scaleX, scaleY, uniformScale)
        }
    }

    /**
     * Tests whether [point] lies inside or on the boundary of this shape.
     *
     * Delegates the containment test to the wrapped [inner] shape.
     *
     * @param point the test point in canvas‐coordinates
     * @return true if inside or on border
     */
    override fun contains(point: Point): Boolean =
        inner.contains(point)

    /**
     * Computes the minimal axis‐aligned bounding box enclosing this shape.
     *
     * Delegates the computation to the wrapped [inner] shape.
     *
     * @return the bounding box
     */
    override fun boundingBox(): BoundingBox =
        inner.boundingBox()

    /**
     * Returns a string representation of this shape.
     *
     * Delegates to the wrapped [inner] shape’s `toString()` method.
     */
    override fun toString(): String =
        inner.toString()
}

/**
 * Draws small square handles at the corners of [box] to indicate selection.
 *
 * @param box the bounding box of the shape
 * @param g the Processing context
 * @param scaleX horizontal scaling factor
 * @param scaleY vertical scaling factor
 * @param uniformScale uniform scaling factor (for handle size)
 */
private fun drawHandles(
    box: BoundingBox,
    g: PApplet,
    scaleX: Float,
    scaleY: Float,
    uniformScale: Float
) {
    val (x, y, w, h) = box
    val handleSize = 8f * uniformScale

    g.pushStyle()
    g.noStroke()
    g.fill(0f)

    val xs = listOf(x, x + w)
    val ys = listOf(y, y + h)
    for (cx in xs) {
        for (cy in ys) {
            val dx = cx * scaleX - handleSize / 2
            val dy = cy * scaleY - handleSize / 2
            g.rect(dx, dy, handleSize, handleSize)
        }
    }
    g.popStyle()
}
