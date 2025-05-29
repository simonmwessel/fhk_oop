package de.fhkiel.oop.model

import de.fhkiel.oop.Sketch
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
 * @version 2.5
 * @since   1.5
 */
abstract class BaseShape (
    originParam: Point = Point(),
    styleParam:  Style = Style()
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
        set(v) { _origin = v }

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
        set(v) { _style = v }

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
     * Checks if a given point is contained within the shape.
     *
     * @param point The [Point] to check.
     * @return `true` if the point is inside or on the boundary of the shape, `false` otherwise.
     */
    abstract fun contains(point: Point): Boolean

    /**
     * Calculates the minimal axis-aligned bounding box that encloses the shape.
     *
     * @return The [BoundingBox] of the shape.
     */
    abstract fun boundingBox(): BoundingBox

    /**
     * Computes the screen bounding box based on the resize mode and scaling factors.
     *
     * This method adjusts the bounding box of the shape according to the specified
     * resize mode and scaling factors, allowing for uniform or relative scaling.
     *
     * @param mode   The resize mode (either UNIFORM_SCALE or RELATIVE).
     * @param sx     Horizontal scaling factor (for RELATIVE mode).
     * @param sy     Vertical scaling factor (for RELATIVE mode).
     * @param us     Uniform scaling factor (for UNIFORM_SCALE mode).
     * @param offX   Optional horizontal offset for the bounding box (default is 0).
     * @param offY   Optional vertical offset for the bounding box (default is 0).
     *
     * @return The adjusted [BoundingBox] for the shape.
     */
    open fun screenBoundingBox(
        mode : Sketch.ResizeMode,
        sx   : Float,
        sy   : Float,
        us   : Float,
        offX : Float = 0f,
        offY : Float = 0f
    ): BoundingBox = when (mode) {
        Sketch.ResizeMode.UNIFORM_SCALE ->
            boundingBox().let { BoundingBox(offX + it.x*us, offY + it.y*us, it.width*us, it.height*us) }

        Sketch.ResizeMode.RELATIVE ->
            boundingBox().let { BoundingBox(it.x*sx, it.y*sy, it.width*us, it.height*us) }
    }

    /**
     * Checks if a point in screen coordinates hits the shape, including its stroke.
     *
     * This method determines if the mouse coordinates (mx, my) fall within the
     * shape's fill area plus half of its stroke thickness, scaled according to the specified resize mode.
     *
     * @param mode The resize mode (either UNIFORM_SCALE or RELATIVE).
     * @param sx   Horizontal scale factor (windowWidth / baseWidth).
     * @param sy   Vertical scale factor (windowHeight / baseHeight).
     * @param us   Uniform scale factor for stroke weight and uniform shape dimensions (min(windowScaleX, windowScaleY)).
     * @param mx   Mouse X coordinate in screen coordinates.
     * @param my   Mouse Y coordinate in screen coordinates.
     * @param offX Optional horizontal offset for centering (default is 0).
     * @param offY Optional vertical offset for centering (default is 0).
     *
     * @return `true` if the mouse coordinates hit the shape (fill or stroke), `false` otherwise.
     */
    open fun hitTestScreen(
        mode : Sketch.ResizeMode,
        sx   : Float,
        sy   : Float,
        us   : Float,
        mx   : Float,
        my   : Float,
        offX : Float = 0f,
        offY : Float = 0f
    ): Boolean {
        val b = screenBoundingBox(mode, sx, sy, us, offX, offY) // Screen bounding box of the fill area
        // style.weight is logical, us scales it to screen. Half of it for each side.
        val screenHalfStroke = style.weight * us / 2f

        return mx >= b.x - screenHalfStroke &&
               mx <= b.x + b.width + screenHalfStroke &&
               my >= b.y - screenHalfStroke &&
               my <= b.y + b.height + screenHalfStroke
    }

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
