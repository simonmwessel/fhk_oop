package de.fhkiel.oop.mapper

import de.fhkiel.oop.model.BoundingBox
import de.fhkiel.oop.model.Vector2D
import processing.core.PApplet
import kotlin.math.min

/**
 * Maps world coordinates in **[[0..worldW]] x [[0..worldH]]** to fill the entire window,
 * stretching separately in X and Y. However, when converting **scalar distances**,
 * it picks the **minimum** of window‐width/world‐width and window‐height/world‐height
 * to ensure no distortion for lengths (i.e., uniform length‐scaling).
 *
 * In other words:
 *  1. **Position mapping** (`worldToScreen`):
 *     - `screenX = worldX * (applet.width / worldW)`
 *     - `screenY = worldY * (applet.height / worldH)`
 *  2. **Scalar mapping** (`worldScalarToScreen`):
 *     - `us = min(applet.width/worldW, applet.height/worldH)`
 *     - `lengthPx = dist * us`
 *
 * This mode is useful when you want your shapes to occupy the full width *and* height
 * of the window (potentially stretching the scene), but still preserve true world‐unit
 * distances when drawing strokes or uniform handles.
 *
 * @param applet   The Processing sketch instance (for `width` and `height`).
 * @param worldW   Logical width of the world coordinate system.
 * @param worldH   Logical height of the world coordinate system.
 * @param worldMin The minimum of logical width and height (`min(worldW, worldH)`),
 *                 used for uniform scalar conversions.
 *
 * @author Simon Wessel
 *
 * @see CoordinateMapper
 * @see UniformScaleMapper
 */
class RelativeScaleMapper(
    private val applet: PApplet,
    private val worldW: Float,
    private val worldH: Float,
    private val worldMin: Float
) : CoordinateMapper {

    /**
     * Scale factor along the X‐axis (pixels per world‐unit).
     *
     * Computed as `applet.width / worldW`.
     *
     * @return Current horizontal stretch factor.
     */
    val sx: Float
        get() = applet.width.toFloat() / worldW

    /**
     * Scale factor along the Y‐axis (pixels per world‐unit).
     *
     * Computed as `applet.height / worldH`.
     *
     * @return Current vertical stretch factor.
     */
    val sy: Float
        get() = applet.height.toFloat() / worldH

    /**
     * Uniform scale factor (pixels per world‐unit) used for mapping lengths
     * without distortion. Always picks the smaller of `sx` and `sy`.
     *
     * Computed as `min(applet.width/worldW, applet.height/worldH)`.
     *
     * @return Current uniform scale factor.
     */
    val us: Float
        get() = min(applet.width.toFloat(), applet.height.toFloat()) / worldMin

    /**
     * {@inheritDoc}
     *
     * Maps each coordinate axis independently:
     * ```
     * screenX = worldX * sx
     * screenY = worldY * sy
     * ```
     * @param vector The vector in world coordinates (world units).
     *
     * @return A new [Vector2D] in screen coordinates (pixels).
     *
     * @see screenToWorld
     */
    override fun worldToScreen(vector: Vector2D): Vector2D =
        Vector2D(vector.x * sx, vector.y * sy)

    /**
     * {@inheritDoc}
     *
     * Inverts the independent axis mapping:
     * ```
     * worldX = screenX / sx
     * worldY = screenY / sy
     * ```
     *
     * @param vector The vector in screen coordinates (pixels).
     *
     * @return A new [Vector2D] in world coordinates (world units).
     *
     * @see worldToScreen
     */
    override fun screenToWorld(vector: Vector2D): Vector2D =
        Vector2D(vector.x / sx, vector.y / sy)

    /**
     * {@inheritDoc}
     *
     * Uses the **uniform** scale factor `us` to map a world‐space length to pixels:
     * ```
     * lengthPx = dist * us
     * ```
     * Ensures that a 1:1 world‐unit remains 1:1 in pixel length when used
     * for strokes or square handles, regardless of window aspect ratio.
     *
     * @param dist Distance in world units.
     * @return Equivalent length in pixels.
     *
     * @see screenScalarToWorld
     */
    override fun worldScalarToScreen(dist: Float): Float {
        return dist * us
    }

    /**
     * {@inheritDoc}
     *
     * Inverts the uniform length mapping:
     * ```
     * sizeWorld = sizePx / us
     * ```
     *
     * @param sizePx Length in pixels.
     * @return Equivalent distance in world units.
     *
     * @see worldScalarToScreen
     */
    override fun screenScalarToWorld(sizePx: Float): Float {
        return sizePx / us
    }

    /**
     * {@inheritDoc}
     *
     * Converts a world‐space [BoundingBox] into screen coordinates by applying
     * the independent axis mappings:
     * ```
     * screenBox.x = worldBox.x * sx
     * screenBox.y = worldBox.y * sy
     * screenBox.width = worldBox.width * sx
     * screenBox.height = worldBox.height * sy
     * ```
     *
     * @param boundingBox The bounding box in world coordinates.
     * @return A new [BoundingBox] in screen coordinates.
     *
     * @see screenBoundingBoxToWorld
     * @see BoundingBox
     */
    override fun worldBoundingBoxToScreen(boundingBox: BoundingBox): BoundingBox =
        BoundingBox(
            x      = boundingBox.x * sx,
            y      = boundingBox.y * sy,
            width  = boundingBox.width * sx,
            height = boundingBox.height * sy
        )

    /**
     * {@inheritDoc}
     *
     * Converts a screen‐space [BoundingBox] back into world coordinates by
     * inverting the independent axis mappings:
     * ```
     * worldBox.x = screenBox.x / sx
     * worldBox.y = screenBox.y / sy
     * worldBox.width = screenBox.width / sx
     * worldBox.height = screenBox.height / sy
     * ```
     *
     * @param boundingBox The bounding box in screen coordinates.
     * @return A new [BoundingBox] in world coordinates.
     *
     * @see worldBoundingBoxToScreen
     * @see BoundingBox
     */
    override fun screenBoundingBoxToWorld(boundingBox: BoundingBox): BoundingBox =
        BoundingBox(
            x      = boundingBox.x / sx,
            y      = boundingBox.y / sy,
            width  = boundingBox.width / sx,
            height = boundingBox.height / sy
        )
}
