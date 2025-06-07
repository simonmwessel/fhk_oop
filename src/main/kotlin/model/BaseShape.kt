package de.fhkiel.oop.model

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.config.ShapeStrategyConfig
import de.fhkiel.oop.mapper.CoordinateMapper
import processing.core.PApplet

/**
 * Base class for all 2-D forms.
 *
 * It now stores **exactly two** mutable properties:
 *
 * * [origin] – geometric anchor point (semantics defined by subclasses)
 * * [style]  – visual appearance, grouped in a single [Style] value object
 *
 * There are **no** proxy getters such as `fillColor`.
 * Every caller must access `shape.style.fill`, `shape.style.weight`, … directly.
 *
 * @constructor The primary constructor allows callers to override both origin
 *              and style; omitting either parameter falls back to defaults.
 *
 * @param originParam Initial origin of the shape (defaults to random point).
 * @param styleParam  Initial style (random colours & weight by default).
 *
 * @see Style
 * @see Point
 *
 * @author  Simon Wessel
 */
abstract class BaseShape (
    originParam: Point = Point(),
    styleParam:  Style = Style()
): Shape {

    /** Backing field for location */
    private var _origin: Point = originParam

    /**
     * Geometric anchor of the shape (centre for circles, top-left for rectangles and squares).
     *
     * @return the location point.
     */
    override var origin: Point
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
     * This method should be implemented by subclasses to define how the shape is rendered.
     *
     * @param g      The PApplet to draw on.
     * @param mapper The coordinate mapper to use for drawing.
     */
    abstract override fun draw(g: PApplet, mapper: CoordinateMapper)

    /**
     * Returns a BoundingBox that represents the full occupied area
     * of this shape if its origin were [candidateOrigin].
     */
    abstract override fun boundingBoxAt(candidateOrigin: Point): BoundingBox

    /**
     * Checks if a point in screen coordinates hits the shape, including its stroke.
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
    protected inline fun withStyle(g: PApplet, block: PApplet.() -> Unit) {
        g.run {
            pushStyle()
            fill(style.fill.red, style.fill.green, style.fill.blue, style.fill.alpha)
            stroke(style.stroke.red, style.stroke.green, style.stroke.blue, style.stroke.alpha)
            strokeWeight(style.weight)
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
        Config.PREFIX +
        columns.joinToString(Config.SEPARATOR) { (k,v,p) ->
            if (k.isEmpty()) "".padEnd(p.first)
            else              k.padEnd(p.first) + Config.SEPARATOR_KEY_VALUE + v.padStart(p.second)
        } +
        Config.SUFFIX
}
