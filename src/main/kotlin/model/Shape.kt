package de.fhkiel.oop.model

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

    /** Draws the shape using *uniform* scaling so aspect ratio is preserved. */
    fun drawUniform(g: PApplet)

    /**
     * Draws the shape with separate X/Y scaling factors plus *uniformScale*
     * so that circles remain circles and squares remain squares when the
     * window is resized non‑uniformly.
     */
    fun drawRelative(
        g: PApplet,
        scaleX: Float,
        scaleY: Float,
        uniformScale: Float
    )
}
