package de.fhkiel.oop.model

import de.fhkiel.oop.Sketch
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
class SelectableShape(val inner: BaseShape) : BaseShape(inner.origin, inner.style) {
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
            val isCentered = inner is de.fhkiel.oop.shapes.Circle
            drawHandles(inner.boundingBox(), g, 1f, 1f, 1f, isCentered)
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
            val isCentered = inner is de.fhkiel.oop.shapes.Circle
            drawHandles(inner.boundingBox(), g, scaleX, scaleY, uniformScale, isCentered)
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
     * Computes the screen bounding box of this shape in the specified resize mode.
     *
     * Delegates the computation to the wrapped [inner] shape.
     *
     * @param mode the resize mode (either [Sketch.ResizeMode.UNIFORM_SCALE] or [Sketch.ResizeMode.RELATIVE])
     * @param sx horizontal scaling factor (for [Sketch.ResizeMode.RELATIVE])
     * @param sy vertical scaling factor (for [Sketch.ResizeMode.RELATIVE])
     * @param us uniform scaling factor (for [Sketch.ResizeMode.UNIFORM_SCALE])
     * @param offX horizontal offset for the bounding box (default is 0)
     * @param offY vertical offset for the bounding box (default is 0)
     *
     * @return the bounding box in screen coordinates
     *
     * @see Sketch.ResizeMode
     * @see BoundingBox.toScreen
     * @see de.fhkiel.oop.shapes.Circle.boundingBox
     * @see de.fhkiel.oop.shapes.Rectangle.boundingBox
     * @see de.fhkiel.oop.shapes.Square.boundingBox
     */
    override fun screenBoundingBox(
        mode : Sketch.ResizeMode,
        sx   : Float,
        sy   : Float,
        us   : Float,
        offX : Float,
        offY : Float
    ): BoundingBox = inner.screenBoundingBox(mode, sx, sy, us, offX, offY)

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
 * @param uniformScale uniform scaling factor (for handle size and box dimensions)
 * @param centeredOrigin true if the shape's logical origin (that is scaled by scaleX/Y)
 *   is the center of its bounding box (e.g., [de.fhkiel.oop.shapes.Circle]).
 *   false if it's the top-left of the bounding box (e.g., [de.fhkiel.oop.shapes.Rectangle]).
 */
private fun drawHandles(
    box: BoundingBox,
    g: PApplet,
    scaleX: Float,
    scaleY: Float,
    uniformScale: Float,
    centeredOrigin: Boolean
) {
    val finalScreenBoxX: Float
    val finalScreenBoxY: Float

    // Calculate the screen dimensions of the bounding box for the handles.
    val screenBoxWidth: Float
    val screenBoxHeight: Float

    if (centeredOrigin) {
        // For shapes like Circle, where box.x = origin.x - radius, box.width = 2 * radius.
        // The logical center of the shape is (box.x + box.width / 2, box.y + box.height / 2).
        val logicalCenterX = box.x + box.width / 2f
        val logicalCenterY = box.y + box.height / 2f

        // Scaled center of the shape based on its primary origin scaling.
        val screenCenterX = logicalCenterX * scaleX
        val screenCenterY = logicalCenterY * scaleY

        // Half-width/height of the bounding box, scaled uniformly (represents screen radius for a circle).
        val screenHalfWidth = (box.width / 2f) * uniformScale
        val screenHalfHeight = (box.height / 2f) * uniformScale

        finalScreenBoxX = screenCenterX - screenHalfWidth
        finalScreenBoxY = screenCenterY - screenHalfHeight

        // For centered shapes like Circle, their bounding box (which is a square)
        // and thus the selection handles' bounding box should scale uniformly.
        screenBoxWidth  = box.width * uniformScale
        screenBoxHeight = box.height * uniformScale
    } else {
        // For shapes like Rectangle/Square (top-left origin), where box.x is the shape's origin.x.
        finalScreenBoxX = box.x * scaleX
        finalScreenBoxY = box.y * scaleY

        // For Rectangles/Squares, the selection handles' bounding box
        // should match the shape's visual scaling.
        screenBoxWidth  = box.width  * uniformScale
        screenBoxHeight = box.height * uniformScale
    }

    // Calculate the size of the handles, scaled uniformly.
    val handleSize = 8f * uniformScale

    g.pushStyle()
    g.noStroke()
    g.fill(0f)

    // Define the X and Y coordinates for the corners of the uniformly scaled screen bounding box.
    val screenCornerXs = listOf(finalScreenBoxX, finalScreenBoxX + screenBoxWidth)
    val screenCornerYs = listOf(finalScreenBoxY, finalScreenBoxY + screenBoxHeight)

    // Draw a handle at each corner.
    for (cornerX in screenCornerXs) {
        for (cornerY in screenCornerYs) {
            // Calculate top-left of the handle square to center it on the corner.
            val handleTopLeftX = cornerX - handleSize / 2
            val handleTopLeftY = cornerY - handleSize / 2
            g.rect(handleTopLeftX, handleTopLeftY, handleSize, handleSize)
        }
    }
    g.popStyle()
}
