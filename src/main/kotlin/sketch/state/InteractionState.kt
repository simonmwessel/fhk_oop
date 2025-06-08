package de.fhkiel.oop.sketch.state

import de.fhkiel.oop.model.BoundingBox
import de.fhkiel.oop.model.InteractiveShape
import de.fhkiel.oop.model.Vector2D

/**
 * State of user interactions: dragging and resizing shapes.
 *
 * @property draggingShape The shape currently being dragged, if any.
 * @property dragOffset The offset between mouse position and shape origin during drag.
 * @property resizingShape The shape currently being resized, if any.
 * @property resizingHandleIndex The index of the handle being dragged for resizing (0 to 3, or -1 if none).
 * @property initialBoundingBox The bounding box of the shape at the start of a resize operation.
 * @property initialOriginForResize The original position of the shape at the start of a resize operation.
 *
 * @author Simon Wessel
 *
 * @see InteractiveShape
 * @see BoundingBox
 */
class InteractionState {
    /**
     * Backing field for the shape currently being dragged.
     *
     * This is used to track which shape is being moved by the user.
     */
    private var _draggingShape: InteractiveShape? = null

    /**
     * Returns the currently dragged shape, if any.
     *
     * @return the shape being dragged, or null.
     */
    var draggingShape: InteractiveShape?
        get() = _draggingShape
        /**
         * Sets the currently dragged shape.
         */
        set(value) {
            _draggingShape = value
        }

    /**
     * Backing field for the shape currently being dragged.
     *
     * This is used to track which shape is being moved by the user.
     */
    private var _dragOffset: Vector2D = Vector2D()

    /**
     * Returns the drag offset between mouse and shape origin.
     *
     * @return vector offset during drag.
     */
    var dragOffset: Vector2D
        get() = _dragOffset
        /**
         * Sets the drag offset.
         */
        set(value) {
            _dragOffset = value
        }

    /**
     * Backing field for the shape currently being dragged.
     *
     * This is used to track which shape is being moved by the user.
     */
    private var _resizingShape: InteractiveShape? = null

    /**
     * Returns the shape currently being resized, if any.
     *
     * @return the shape under resize, or null.
     */
    var resizingShape: InteractiveShape?
        get() = _resizingShape
        /**
         * Sets the currently resizing shape.
         */
        set(value) {
            _resizingShape = value
        }

    /**
     * Index of the handle being dragged for resizing.
     *
     * Valid values are 0 to 3, corresponding to the four corners of a rectangle.
     * A value of –1 indicates no handle is currently being dragged.
     */
    private var _resizingHandleIndex: Int = -1

    /**
     * Returns the index (0..3) of the handle being dragged.
     *
     * @return handle index, or –1 if none.
     */
    var resizingHandleIndex: Int
        get() = _resizingHandleIndex
        /**
         * Sets the handle index for resizing.
         *
         * @throws IllegalArgumentException if value < –1.
         */
        set(value) {
            require(value >= -1) { "Handle index must be ≥ –1" }
            _resizingHandleIndex = value
        }

    /**
     * Backing field for the initial bounding box of a shape being resized.
     *
     * This is used to calculate the new size and position of the shape
     * based on the initial dimensions before resizing started.
     */
    private var _initialBoundingBox: BoundingBox? = null

    /**
     * Returns the bounding box at the start of a resize.
     *
     * @return initial bounding box, or null.
     */
    var initialBoundingBox: BoundingBox?
        get() = _initialBoundingBox
        /**
         * Sets the initial bounding box for a resize operation.
         */
        set(value) {
            _initialBoundingBox = value
        }

    /**
     * Backing field for the initial origin of a shape being resized.
     *
     * This is used to track the original position of the shape
     * before any resizing operation begins.
     */
    private var _initialOriginForResize: Vector2D? = null

    /**
     * Returns the original origin at the start of a resize.
     *
     * @return initial origin vector, or null.
     */
    var initialOriginForResize: Vector2D?
        get() = _initialOriginForResize
        /**
         * Sets the original origin for a resize operation.
         */
        set(value) {
            _initialOriginForResize = value
        }
}
