package de.fhkiel.oop.model

import de.fhkiel.oop.config.Config
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
 * @version 2.4
 * @since   1.5
 */
abstract class BaseShape (
    originParam: Point = Point(),
    styleParam:  Style  = Style()
) {

    /** Backing field for location */
    private var _origin: Point = originParam

    /**
     * Geometric anchor of the shape (centre for circles, top-left for rectangles and squares).
     *
     * @return the location point.
     */
    var origin: Point
        /** Returns the location of the shape. */
        get() = _origin
        /** Sets the location of the shape. */
        set(v) {
            require(v.x >= 0f) { "X coordinate must be >= 0" }
            require(v.y >= 0f) { "Y coordinate must be >= 0" }

            require(v.x <= Config.MAX_X) { "X coordinate must be <= ${Config.MAX_X}" }
            require(v.y <= Config.MAX_Y) { "Y coordinate must be <= ${Config.MAX_Y}" }

            _origin = v
        }

    /** Backing field for the style */
    private var _style: Style = styleParam

    /**
     * The style of the shape.
     *
     * @return the style as [Style].
     */
    var style: Style
        /** Returns the style of the shape. */
        get() = _style
        /** Sets the style of the shape. */
        set(v) {
            require(v.fill.alpha >= 0f) { "Alpha must be >= 0" }
            require(v.fill.alpha <= 255f) { "Alpha must be <= 255" }

            require(v.stroke.alpha >= 0f) { "Alpha must be >= 0" }
            require(v.stroke.alpha <= 255f) { "Alpha must be <= 255" }

            require(v.weight >= 0f) { "Weight must be >= 0" }
            require(v.weight <= Config.MAX_STRK_WEIGHT) { "Weight must be <= ${Config.MAX_STRK_WEIGHT}" }

            _style = v
        }

    /**
     * Abstract method to calculate the area of the shape.
     *
     * @return The area of the shape.
     */
    abstract fun getArea(): Float

    /**
     * Renders the shape using uniform scaling to preserve aspect ratio.
     * The entire scene is scaled around the center of the window.
     *
     * @param g Processing graphics context
     *
     * @see de.fhkiel.oop.Sketch.ResizeMode.UNIFORM_SCALE
     */
    abstract fun drawUniform(g: PApplet)

    /**
     * Renders the shape with relative positioning while preserving shape integrity.
     * Positions are scaled relative to window size, while circles/squares maintain
     * their aspect ratio via [uniformScale].
     *
     * @param g            Processing graphics context
     * @param scaleX       Horizontal scaling factor (windowWidth / baseWidth)
     * @param scaleY       Vertical scaling factor (windowHeight / baseHeight)
     * @param uniformScale Unified scaling factor for circles/squares (min(windowScaleX, windowScaleY))
     *
     * @see de.fhkiel.oop.Sketch.ResizeMode.RELATIVE
     */
    abstract fun drawRelative(
        g: PApplet,
        scaleX: Float,
        scaleY: Float,
        uniformScale: Float
    )

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
