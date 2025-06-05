package de.fhkiel.oop.model.select

import de.fhkiel.oop.mapper.CoordinateMapper
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.Point
import processing.core.PApplet


/**
 * Decorator that adds *selection* behavior to any [de.fhkiel.oop.model.BaseShape].
 *
 * When a `SelectableShape` is marked as selected (`isSelected = true`), it draws
 * small square "handles" around the wrapped shape's bounding box. Both positions
 * and sizes of these handles are computed such that they move and scale *exactly*
 * with the shape, regardless of the current [CoordinateMapper] in use.
 *
 * All other shape‐related behavior (area, bounding box, hit testing, drawing)
 * is delegated directly to the wrapped [inner] shape.
 *
 * @property inner      The [BaseShape] being decorated. All operations are forwarded to it.
 * @property isSelected Flag indicating selection state; when `true`, handles are rendered.
 *
 * @constructor Creates a new `SelectableShape` that wraps the given [inner] shape.
 *
 * @see de.fhkiel.oop.model.BaseShape
 * @see de.fhkiel.oop.mapper.CoordinateMapper
 * @see HandleStrategy
 * @see CornerHandleStrategy
 * @see EdgeHandleStrategy
 *
 * @author Simon Wessel
 */
class SelectableShape(val inner: BaseShape) : BaseShape(inner.origin, inner.style) {
    /**
     * Whether this shape is currently selected.
     * If `true`, handles will be drawn during the next `draw(...)` call.
     */
    var isSelected: Boolean = false

    /**
     * {@inheritDoc}
     *
     * Delegates area calculation to [inner].
     *
     * @return The area of the wrapped shape (in world‐space units).
     */
    override fun getArea(): Float =
        inner.getArea()

    /**
     * {@inheritDoc}
     *
     * 1. First calls `inner.draw(g, mapper)` to render the shape itself.
     * 2. If [isSelected] is `true`, computes:
     *     - The shape’s on‐screen bounding box via `inner.screenBoundingBox(mapper)`.
     *     - The shape’s world‐space bounding box via `inner.boundingBox()`.
     *     - All handle‐points in world‐space via `inner.handleStrategy().handlePoints(...)`.
     *     - Converts each handle point to a pixel offset by:
     *         a. Computing `(dxWorld, dyWorld) = (pt.x - worldBox.x, pt.y - worldBox.y)`.
     *         b. Mapping `(dxWorld, dyWorld)` to `(dxScreen, dyScreen)` using
     *            `mapper.worldScalarToScreen(...)` (uniform scaling).
     *         c. Adding these deltas to `(screenBox.x, screenBox.y)`.
     *     - Computes handle size in pixels by `mapper.worldScalarToScreen(HANDLE_SIZE_WORLD_UNITS)`.
     *     - Draws a small filled square centered at each handle location.
     *
     * Using this approach ensures that:
     *  - Handles **move** in lockstep with the shape.
     *  - Handles **scale** uniformly (stay square) even if window is resized.
     *
     * @param g      The Processing PApplet context (used for drawing).
     * @param mapper The [CoordinateMapper] for converting world→screen.
     *
     * @see de.fhkiel.oop.model.BaseShape.draw
     * @see de.fhkiel.oop.model.BaseShape.boundingBox
     * @see de.fhkiel.oop.model.BaseShape.screenBoundingBox
     * @see HandleStrategy
     * @see CoordinateMapper.worldScalarToScreen
     */
    override fun draw(g: PApplet, mapper: CoordinateMapper) {
        inner.draw(g, mapper)

        if (!isSelected) return

        val screenBox = inner.screenBoundingBox(mapper)

        val worldBox = inner.boundingBox()
        val worldHandlePoints: List<Point> = inner.handleStrategy().handlePoints(worldBox)

        g.pushStyle()
        g.noStroke()
        g.fill(0f)

        val handleSidePx = mapper.worldScalarToScreen(10f)

        for (worldPt in worldHandlePoints) {
            val dxWorld = worldPt.x - worldBox.x
            val dyWorld = worldPt.y - worldBox.y

            val sx = screenBox.x + mapper.worldScalarToScreen(dxWorld)
            val sy = screenBox.y + mapper.worldScalarToScreen(dyWorld)

            g.rect(
                sx - handleSidePx / 2f,
                sy - handleSidePx / 2f,
                handleSidePx,
                handleSidePx
            )
        }

        g.popStyle()
    }

    /**
     * {@inheritDoc}
     *
     * Delegates to inner.boundingBox, returning the minimal axis‐aligned
     * bounding box that encloses the wrapped shape, expressed in world‐space.
     *
     * @return A [BoundingBox] (world‐space).
     *
     * @see de.fhkiel.oop.model.BaseShape.boundingBox
     */
    override fun boundingBox(): BoundingBox =
        inner.boundingBox()

    /**
     * {@inheritDoc}
     *
     * Delegates to inner.screenBoundingBox, returning a [BoundingBox]
     * in **screen pixels**, computed via [mapper].
     *
     * @param mapper The [CoordinateMapper] to convert world↔screen.
     * @return A [BoundingBox] (screen‐space).
     *
     * @see de.fhkiel.oop.model.BaseShape.screenBoundingBox
     */
    override fun screenBoundingBox(mapper: CoordinateMapper): BoundingBox =
        inner.screenBoundingBox(mapper)

    /**
     * {@inheritDoc}
     *
     * Delegates to inner.hitTestScreen, testing if `(mx, my)` in screen pixels
     * lies inside the shape’s filled area or stroke.
     *
     * @param mapper The [CoordinateMapper] for mapping hit‐test coordinates.
     * @param mx     Mouse X‐coordinate in pixels.
     * @param my     Mouse Y‐coordinate in pixels.
     * @return `true` if the point hits the shape; `false` otherwise.
     *
     * @see de.fhkiel.oop.model.BaseShape.hitTestScreen
     */
    override fun hitTestScreen(mapper: CoordinateMapper, mx: Float, my: Float): Boolean =
        inner.hitTestScreen(mapper, mx, my)

    /**
     * {@inheritDoc}
     *
     * Delegates to inner.handleStrategy, returning the [HandleStrategy] used
     * to compute world‐space handle points (e.g. corners or edge‐midpoints).
     *
     * @return The [HandleStrategy] of the wrapped shape.
     *
     * @see HandleStrategy
     */
    override fun handleStrategy(): HandleStrategy = inner.handleStrategy()

    /**
     * {@inheritDoc}
     *
     * Delegates to inner.toString, returning the string representation
     * (including shape type, origin, size attributes, and style).
     *
     * @return A [String] describing the wrapped shape.
     *
     * @see de.fhkiel.oop.model.BaseShape.toString
     */
    override fun toString(): String = inner.toString()
}
