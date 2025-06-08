package de.fhkiel.oop.model

import de.fhkiel.oop.config.ShapeStrategyConfig
import de.fhkiel.oop.mapper.CoordinateMapper
import de.fhkiel.oop.strategy.handle.HandleStrategy
import processing.core.PApplet

/**
 * Decorator that adds *selection* behavior to any [BaseShape].
 *
 * When a `ManipulatableShape` is marked as selected (`isSelected = true`), it draws
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
 * @constructor Creates a new `ManipulatableShape` that wraps the given [inner] shape.
 *
 * @see BaseShape
 * @see CoordinateMapper
 * @see HandleStrategy
 * @see de.fhkiel.oop.strategy.handle.CornerHandleStrategy
 * @see de.fhkiel.oop.strategy.handle.EdgeHandleStrategy
 *
 * @author Simon Wessel
 */
class InteractiveShape(val inner: BaseShape) : BaseShape(inner.config, inner.origin, inner.style) {

    /**
     * The strategy used to determine where handles should be placed.
     * Defaults to the [HandleStrategy] defined in inner.strategies.
     */
    private var _strategies: ShapeStrategyConfig = inner.strategies

    /**
     * The strategies used for shape manipulation, such as move constraints.
     * This property is delegated to the inner shape's strategies.
     */
    override var strategies: ShapeStrategyConfig
        /**
         * Gets the strategies from the inner shape.
         *
         * @return The current strategies.
         */
        get() = _strategies
        /** Sets the strategies for the inner shape. */
        set(v) {
            _strategies = v
            inner.strategies = v
        }

    /**
     * Whether this shape is currently selected.
     * If `true`, handles will be drawn during the next `draw(...)` call.
     */
    private var _isSelected: Boolean = false

    /**
     * Flag indicating whether the shape is selected.
     * If `true`, handles will be drawn during the next `draw(...)` call.
     */
    var isSelected: Boolean
        /**
         * Gets the selection state.
         *
         * @return `true` if the shape is selected, `false` otherwise.
         */
        get() = _isSelected
        /** Sets the selection state. */
        set(value) {
            _isSelected = value
        }

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
     *     - All handle‐vector in world‐space via `inner.handleStrategy().handleVectors(...)`.
     *     - Converts each handle vector to a pixel offset by:
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
     * @see BaseShape.draw
     * @see BaseShape.boundingBoxAt
     * @see HandleStrategy
     * @see CoordinateMapper.worldScalarToScreen
     */
    override fun draw(g: PApplet, mapper: CoordinateMapper) {
        inner.draw(g, mapper)
        if (!isSelected) return

        val shapeOrigin  = inner.origin
        val worldBox     = inner.boundingBoxAt(shapeOrigin)
        val handleSidePx = mapper.worldScalarToScreen(10f)

        g.pushStyle()
        g.noStroke()
        g.fill(0f)

        for (handleOrigin in inner.strategies.handleStrategy.handleVectorOrigins(config, worldBox)) {
            // Compute the delta from the shape’s origin to the handle in world‐space.
            val dxWorld = handleOrigin.x - shapeOrigin.x
            val dyWorld = handleOrigin.y - shapeOrigin.y

            // Convert those world‐space deltas into screen‐space lengths.
            // Uses uniform scaling (so handles stay square).
            val dxScreen = mapper.worldScalarToScreen(dxWorld)
            val dyScreen = mapper.worldScalarToScreen(dyWorld)

            // Map the shape’s origin itself into screen‐space.
            val screenOrigin = mapper.worldToScreen(shapeOrigin)

            // Compute the final screen‐position of the handle by
            // offsetting from the shape‐origin in pixels.
            val screenX = screenOrigin.x + dxScreen
            val screenY = screenOrigin.y + dyScreen

            // Draw a centered square of size handleSidePx at that position.
            g.rect(
                screenX - handleSidePx / 2f,
                screenY - handleSidePx / 2f,
                handleSidePx,
                handleSidePx
            )
        }
        g.popStyle()
    }

    /**
     * {@inheritDoc}
     *
     * Delegates to the wrapped shape’s [boundingBoxAt], passing along
     * the candidate origin.
     *
     * @param candidateOrigin The hypothetical origin in world coordinates.
     * @return The wrapped shape’s bounding box at that origin.
     * @see BaseShape.boundingBoxAt
     */
    override fun boundingBoxAt(candidateOrigin: Vector2D): BoundingBox =
        inner.boundingBoxAt(candidateOrigin)

    /**
     * {@inheritDoc}
     *
     * Maps the wrapped shape’s bounding box to screen coordinates,
     * including the stroke width as a margin.
     *
     * @param mapper The coordinate mapper to use for conversion.
     * @param candidateOrigin The top-left corner candidate in world coordinates.
     *
     * @return A [BoundingBox] in screen coordinates that includes the stroke margin.
     */
    override fun screenBoundingBoxAt(mapper: CoordinateMapper, candidateOrigin: Vector2D): BoundingBox =
        inner.screenBoundingBoxAt(mapper, candidateOrigin)

    /**
     * Returns a list of handle‐sized bounding boxes in screen‐pixels,
     * useful for hit‐testing drag‐on‐handle events.
     *
     * @param mapper CoordinateMapper from world→screen.
     * @return each box centered on a handle at size 10f world‐units.
     */
    fun getHandleScreenBounds(mapper: CoordinateMapper): List<BoundingBox> {
        val worldBox      = inner.boundingBoxAt(inner.origin)
        val handleSizeWU  = 10f
        val sidePx        = mapper.worldScalarToScreen(handleSizeWU)
        val originScreen  = mapper.worldToScreen(inner.origin)

        return inner.strategies.handleStrategy
            .handleVectorOrigins(config, worldBox)
            .map { worldHandle ->
                val dxPx = mapper.worldScalarToScreen(worldHandle.x - inner.origin.x)
                val dyPx = mapper.worldScalarToScreen(worldHandle.y - inner.origin.y)
                val cx   = originScreen.x + dxPx
                val cy   = originScreen.y + dyPx
                BoundingBox(
                    origin = Vector2D(inner.config, cx - sidePx/2f, cy - sidePx/2f),
                    width  = sidePx,
                    height = sidePx
                )
            }
    }

    /**
     * {@inheritDoc}
     *
     * Delegates to inner.hitTestScreen, testing if `(mx, my)` in screen pixels
     * lies inside the shape’s filled area or stroke.
     *
     * @param mapper The [CoordinateMapper] for mapping hit‐test coordinates.
     * @param mx     Mouse X‐coordinate in pixels.
     * @param my     Mouse Y‐coordinate in pixels.
     * @return `true` if the vector hits the shape; `false` otherwise.
     *
     * @see BaseShape.hitTestScreen
     */
    override fun hitTestScreen(mapper: CoordinateMapper, mx: Float, my: Float): Boolean =
        inner.hitTestScreen(mapper, mx, my)

    /**
     * {@inheritDoc}
     *
     * Delegates to inner.toString, returning the string representation
     * (including shape type, origin, size attributes, and style).
     *
     * @return A [String] describing the wrapped shape.
     *
     * @see BaseShape.toString
     */
    override fun toString(): String = inner.toString()
}
