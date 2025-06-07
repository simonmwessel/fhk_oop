package de.fhkiel.oop.mapper

import de.fhkiel.oop.model.BoundingBox
import de.fhkiel.oop.model.Vector2D

/**
 * Strategy interface that converts between **world** coordinates (model space)
 * and **screen** coordinates (pixel space). Implementations must guarantee
 * that scalar distances are mapped without distortion (i.e., uniform scaling
 * for lengths irrespective of axis).
 *
 * The four methods in this interface cover:
 *  1. `worldToScreen(worldX, worldY)`: Map a 2D vector from world to screen.
 *  2. `screenToWorld(screenX, screenY)`: Map a 2D vector from screen to world.
 *  3. `worldScalarToScreen(dist)`: Map a scalar distance (in world units) to pixels.
 *  4. `screenScalarToWorld(sizePx)`: Map a scalar distance (in pixels) back to world units.
 *
 * Typically, a concrete mapper (e.g. [RelativeScaleMapper] or [UniformScaleMapper])
 * will determine whether to stretch to fill the window or letter‐box the scene.
 *
 * @author Simon Wessel
 *
 * @see RelativeScaleMapper
 * @see UniformScaleMapper
 */
sealed interface CoordinateMapper {
    /**
     * Maps a vector given in world‐space into screen‐space (pixels).
     *
     * @param vector The vector in world coordinates (world units).
     *
     * @return A new [Vector2D] in screen coordinates (pixels).
     *
     * @see screenToWorld
     * @see worldScalarToScreen
     */
    fun worldToScreen(vector: Vector2D): Vector2D

    /**
     * Maps a vector given in screen‐space (pixels) back into world‐space.
     *
     * @param vector The vector in screen coordinates (pixels).
     *
     * @return A new [Vector2D] in world coordinates (world units).
     *
     * @see worldToScreen
     * @see screenScalarToWorld
     */
    fun screenToWorld(vector: Vector2D): Vector2D

    /**
     * Maps a scalar distance (in world units) into screen‐space length (in pixels),
     * ensuring that distances scale uniformly (no axis distortion).
     *
     * @param dist Distance in world units.
     * @return Corresponding length in pixels.
     *
     * @see screenScalarToWorld
     */
    fun worldScalarToScreen(dist: Float): Float

    /**
     * Maps a scalar distance (in pixels) back into world‐space.
     *
     * @param sizePx Length in pixels.
     * @return Corresponding distance in world units.
     *
     * @see worldScalarToScreen
     */
    fun screenScalarToWorld(sizePx: Float): Float

    /**
     * Converts a world‐space [BoundingBox] into screen coordinates.
     *
     * @param boundingBox The bounding box in world coordinates.
     *
     * @return A new [BoundingBox] in screen coordinates.
     *
     * @see screenBoundingBoxToWorld
     * @see BoundingBox
     */
    fun worldBoundingBoxToScreen(boundingBox: BoundingBox): BoundingBox

    /**
     * Converts a screen‐space [BoundingBox] back into world coordinates.
     *
     * @param boundingBox The bounding box in screen coordinates.
     *
     * @return A new [BoundingBox] in world coordinates.
     *
     * @see worldBoundingBoxToScreen
     * @see BoundingBox
     */
    fun screenBoundingBoxToWorld(boundingBox: BoundingBox): BoundingBox
}
