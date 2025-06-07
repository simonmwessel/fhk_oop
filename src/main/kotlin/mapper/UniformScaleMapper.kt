package de.fhkiel.oop.mapper

import de.fhkiel.oop.model.BoundingBox
import de.fhkiel.oop.model.Vector2D
import processing.core.PApplet
import kotlin.math.min

/**
 * Uniformly scales the world‐space rectangle defined by `[0..worldW] × [0..worldH]`
 * **inside** the window, maintaining aspect ratio. If the window’s aspect ratio
 * does not match the world’s, letter‐boxing (margins on two sides) is introduced.
 *
 * This mapper computes:
 *  1. A single scale factor `f = min(applet.width/worldW, applet.height/worldH)`,
 *     so that both X and Y expand/shrink by the same ratio.
 *  2. Offsets `offX` and `offY` to center the world content in the window.
 *
 * Usage:
 *  - `worldToScreen(worldX, worldY)` → `(worldX * f + offX, worldY * f + offY)`
 *  - `screenToWorld(screenX, screenY)` → `((screenX - offX) / f, (screenY - offY) / f)`
 *  - `worldScalarToScreen(dist)` → `dist * f`
 *  - `screenScalarToWorld(sizePx)` → `sizePx / f`
 *
 * @param applet The Processing sketch instance, used to query `width` and `height`.
 * @param worldW Logical width of the world coordinate system.
 * @param worldH Logical height of the world coordinate system.
 *
 * @author Simon Wessel
 *
 * @see CoordinateMapper
 * @see RelativeScaleMapper
 */
class UniformScaleMapper(
    private val applet: PApplet,
    private val worldW: Float,
    private val worldH: Float
) : CoordinateMapper {

    /**
     * Computes the uniform pixel‐per‐world unit scale factor.
     *
     * Defined as `min(applet.width/worldW, applet.height/worldH)`.
     * Ensures that the entire world fits inside the window without cropping,
     * preserving aspect ratio.
     *
     * @return The current uniform scale factor `f`.
     */
    private val f get() = min(applet.width / worldW, applet.height / worldH)

    /**
     * Horizontal letter‐box offset (pixels).
     * Defined as `(applet.width - worldW * f) / 2f` to center content horizontally.
     *
     * @return Current X‐axis offset in pixels.
     */
    private val offX get() = (applet.width  - worldW * f) / 2f

    /**
     * Vertical letter‐box offset (pixels).
     * Defined as `(applet.height - worldH * f) / 2f` to center content vertically.
     *
     * @return Current Y‐axis offset in pixels.
     */
    private val offY get() = (applet.height - worldH * f) / 2f

    /**
     * {@inheritDoc}
     *
     * Inverts the uniform, centered mapping:
     *
     * ```
     * screenX = worldX * f + offX
     * screenY = worldY * f + offY
     * ```
     *
     * @param vector The vector in world coordinates (world units).
     *
     * @return A new [Vector2D] in screen coordinates (pixels).
     *
     * @see screenToWorld
     * @see Vector2D
     */
    override fun worldToScreen(vector: Vector2D): Vector2D =
        Vector2D(vector.x * f + offX, vector.y * f + offY)

    /**
     * {@inheritDoc}
     *
     * Inverts the uniform, centered mapping:
     * ```
     * worldX = (screenX - offX) / f
     * worldY = (screenY - offY) / f
     * ```
     *
     * @param vector The vector in screen coordinates (pixels).
     *
     * @return A new [Vector2D] in world coordinates (world units).
     *
     * @see worldToScreen
     * @see Vector2D
     */
    override fun screenToWorld(vector: Vector2D): Vector2D =
        Vector2D((vector.x - offX) / f, (vector.y - offY) / f)

    /**
     * {@inheritDoc}
     *
     * Maps a scalar distance (world units) into pixels using the uniform scale `f`:
     * ```
     * lengthPx = dist * f
     * ```
     *
     * @param dist Distance in world units.
     * @return Equivalent length in pixels.
     *
     * @see screenScalarToWorld
     */
    override fun worldScalarToScreen(dist: Float): Float = dist * f

    /**
     * {@inheritDoc}
     *
     * Inverts the uniform scalar mapping:
     * ```
     * sizeWorld = sizePx / f
     * ```
     *
     * @param sizePx Length in pixels.
     * @return Equivalent distance in world units.
     *
     * @see worldScalarToScreen
     */
    override fun screenScalarToWorld(sizePx: Float): Float = sizePx / f

    /**
     * {@inheritDoc}
     *
     * Converts a world‐space [BoundingBox] into screen coordinates,
     * applying the uniform scale and offsets.
     *
     * @param boundingBox The bounding box in world coordinates.
     * @return A new [BoundingBox] in screen coordinates.
     *
     * @see screenBoundingBoxToWorld
     */
    override fun worldBoundingBoxToScreen(boundingBox: BoundingBox): BoundingBox =
        BoundingBox(
            x      = boundingBox.x * f + offX,
            y      = boundingBox.y * f + offY,
            width  = boundingBox.width * f,
            height = boundingBox.height * f)

    /**
     * {@inheritDoc}
     *
     * Converts a screen‐space [BoundingBox] back into world coordinates,
     * reversing the uniform scale and offsets.
     *
     * @param boundingBox The bounding box in screen coordinates.
     * @return A new [BoundingBox] in world coordinates.
     *
     * @see worldBoundingBoxToScreen
     */
    override fun screenBoundingBoxToWorld(boundingBox: BoundingBox): BoundingBox =
        BoundingBox(
            x      = (boundingBox.x - offX) / f,
            y      = (boundingBox.y - offY) / f,
            width  = boundingBox.width / f,
            height = boundingBox.height / f)
}
