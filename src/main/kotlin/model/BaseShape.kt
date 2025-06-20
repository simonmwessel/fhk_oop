package de.fhkiel.oop.model

import de.fhkiel.oop.config.AppConfig
import de.fhkiel.oop.config.DefaultConfig
import de.fhkiel.oop.config.ShapeStrategyConfig
import de.fhkiel.oop.mapper.CoordinateMapper
import processing.core.PApplet

/**
 * Base class for all 2-D forms.
 *
 * It now stores **exactly two** mutable properties:
 *
 * * [origin] – geometric anchor vector (semantics defined by subclasses)
 * * [style]  – visual appearance, grouped in a single [Style] value object
 *
 * There are **no** proxy getters such as `fillColor`.
 * Every caller must access `shape.style.fill`, `shape.style.weight`, … directly.
 *
 * @constructor The primary constructor allows callers to override both origin
 *              and style; omitting either parameter falls back to defaults.
 *
 * @param originParam Initial origin of the shape (defaults to random vector).
 * @param styleParam  Initial style (random colours & weight by default).
 *
 * @see Style
 * @see Vector2D
 *
 * @author  Simon Wessel
 */
abstract class BaseShape (
    open val config: AppConfig = DefaultConfig,
    originParam: Vector2D = Vector2D(),
    styleParam:  Style = Style()
): Shape {

    /** Backing field for location */
    private var _origin: Vector2D = originParam

    /**
     * Geometric anchor of the shape (centre for circles, top-left for rectangles and squares).
     *
     * @return the location vector.
     */
    override var origin: Vector2D
        /** Returns the location of the shape. */
        get() = _origin
        /** Sets the location of the shape. */
        set(v) { _origin = v }

    /** Backing field for the style */
    private var _style: Style = styleParam

    /**
     * The style of the shape.
     *
     * @return the style as [Style].
     */
    override var style: Style
        /** Returns the style of the shape. */
        get() = _style
        /** Sets the style of the shape. */
        set(v) { _style = v }

    /**
     * The strategies used for shape manipulation, such as move constraints.
     *
     * @return the strategies as [ShapeStrategyConfig].
     */
    abstract override var strategies: ShapeStrategyConfig

    /**
     * Abstract method to calculate the area of the shape.
     *
     * @return The area of the shape.
     */
    abstract override fun getArea(): Float

    /**
     * Draws the shape on the given [PApplet] using the provided [CoordinateMapper].
     *
     * If [de.fhkiel.oop.config.DefaultConfig.debug] is enabled, it also draws the origin and bounding box
     *
     * @param g      The PApplet to draw on.
     * @param mapper The coordinate mapper to use for drawing.
     */
    override fun draw(g: PApplet, mapper: CoordinateMapper) {
        if (config.debug) {
            drawOrigin(g, mapper)
            drawBoundingBox(g, mapper)
            drawBoundingBoxOrigin(g, mapper)
        }
    }

    /**
     * Draws the origin of the shape on the given PApplet.
     *
     * This method visualizes the origin as a small circle at the shape's origin point.
     *
     * @param g      The PApplet to draw on.
     * @param mapper The coordinate mapper to convert world coordinates to screen coordinates.
     */
    private fun drawOrigin(g: PApplet, mapper: CoordinateMapper) =
        g.apply {
            pushStyle(); noStroke(); fill(0xFFFF0000.toInt())
            mapper.worldToScreen(origin).let { ellipse(it.x, it.y, 4f, 4f) }
            g.popStyle()
        }

    /**
     * Draws the bounding box of the shape on the given PApplet.
     *
     * This method visualizes the bounding box as a rectangle around the shape's occupied area.
     *
     * @param g      The PApplet to draw on.
     * @param mapper The coordinate mapper to convert world coordinates to screen coordinates.
     */
    private fun drawBoundingBox(g: PApplet, m: CoordinateMapper) =
        g.apply {
            pushStyle(); noFill(); stroke(0f); strokeWeight(1f)
            screenBoundingBoxAt(m).let { rect(it.origin.x, it.origin.y, it.width, it.height) }
            popStyle()
        }

    /**
     * Draws the bounding box origin on the given PApplet.
     *
     * This method visualizes the origin of the bounding box as a small circle
     * at the center of the bounding box.
     *
     * @param g The PApplet to draw on.
     * @param m The coordinate mapper to convert world coordinates to screen coordinates.
     */
    private fun drawBoundingBoxOrigin(g: PApplet, m: CoordinateMapper) =
        g.apply {
            pushStyle(); noStroke(); fill(0xFFFF0000.toInt())
            screenBoundingBoxAt(m).let { ellipse(it.origin.x + it.width / 2, it.origin.y + it.height / 2, 4f, 4f) }
            popStyle()
        }

    /**
     * Returns a BoundingBox that represents the full occupied area
     * of this shape if its origin were [candidateOrigin].
     */
    abstract override fun boundingBoxAt(candidateOrigin: Vector2D): BoundingBox
    // TODO: Include stroke width in the world bounding box calculation

    /**
     * Returns a BoundingBox that represents the full occupied area
     * of this shape if its origin were [candidateOrigin], in screen coordinates.
     *
     * @param mapper The coordinate mapper to convert world coordinates to screen coordinates.
     * @param candidateOrigin The hypothetical origin in world coordinates.
     * @return A BoundingBox that covers the shape’s full extent at that origin, in screen coordinates.
     */
    abstract override fun screenBoundingBoxAt(mapper: CoordinateMapper, candidateOrigin: Vector2D): BoundingBox
    // TODO: Exclude stroke width in the screen bounding box calculation when included in the world bounding box

    /**
     * Checks if a vector in screen coordinates hits the shape, including its stroke.
     *
     * @return `true` if the mouse coordinates hit the shape (fill or stroke), `false` otherwise.
     */
    abstract override fun hitTestScreen(mapper: CoordinateMapper, mx: Float, my: Float): Boolean

    /**
     * Executes the provided drawing block on the given PApplet instance with the current style applied.
     *
     * This helper method wraps the pushStyle/popStyle pattern and allows direct use of drawing
     * functions (`ellipse`, `rect`, `line`, etc.) within the `block` on the PApplet.
     *
     * @param g     The PApplet instance to draw on
     * @param block A block of drawing operations in the context of `g`
     */
    protected inline fun withStyle(g: PApplet, mapper: CoordinateMapper, block: PApplet.() -> Unit) {
        g.run {
            pushStyle()
            fill(style.fill.red, style.fill.green, style.fill.blue, style.fill.alpha)
            stroke(style.stroke.red, style.stroke.green, style.stroke.blue, style.stroke.alpha)
            g.strokeWeight(mapper.worldScalarToScreen(style.weight))
            block()
            popStyle()
        }
    }

    /**
     * Abstract method to calculate the perimeter of the shape.
     *
     * @return The perimeter of the shape.
     */
    abstract override fun toString(): String

    /**
     * Builds a complete, formatted line by combining a list of columns with configured
     * prefix, separators and suffix.
     *
     * Each column is represented by a Triple of:
     *  - key:   the column heading (empty String for blank placeholders)
     *  - value: the column content as a String
     *  - pad:   a Pair of two Ints (padKeyWidth, padValueWidth) defining:
     *              - padKeyWidth:   number of characters to pad the key using padEnd()
     *              - padValueWidth: number of characters to pad the value using padStart()
     *
     * The final output is constructed as:
     *  1. Config.PREFIX
     *  2. ```columns.joinToString(Config.SEPARATOR) { ... }  // each column formatted and joined```
     *  3. Config.SUFFIX
     *
     * @param columns List of Triple(key, value, Pair(padKeyWidth, padValueWidth)) defining
     *                how each column is labeled, its content, and its padding.
     *
     * @return A single String containing PREFIX + all padded & separated columns + SUFFIX.
     */
    protected fun buildString(columns: List<Triple<String, String, Pair<Int,Int>>>): String =
        config.prefix +
        columns.joinToString(config.separator) { (k,v,p) ->
            if (k.isEmpty()) "".padEnd(p.first)
            else              k.padEnd(p.first) + config.separatorKeyValue + v.padStart(p.second)
        } +
        config.suffix
}
