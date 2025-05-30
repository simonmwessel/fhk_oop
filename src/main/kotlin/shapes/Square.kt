package de.fhkiel.oop.shapes

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.BoundingBox
import de.fhkiel.oop.model.Point
import de.fhkiel.oop.model.Shape
import de.fhkiel.oop.model.Style
import de.fhkiel.oop.utils.FloatExtensions.formatAreaValue
import de.fhkiel.oop.utils.FloatExtensions.formatAttribute1Value
import de.fhkiel.oop.utils.FloatExtensions.formatCoordinateValue
import de.fhkiel.oop.utils.FloatExtensions.formatStrokeWeightValue
import de.fhkiel.oop.utils.FloatExtensions.validateInRange
import de.fhkiel.oop.utils.RandomUtils.random
import processing.core.PApplet
import kotlin.Float
import kotlin.math.sqrt

/**
 * A square defined by its top-left corner and side length.
 *
 * @property origin     The top-left corner of the square.
 * @property sideLength Length of each side ∈ ([Float.MIN_VALUE]..[Config.MAX_SQUARE_SIDE]).
 *
 * @constructor Creates a [Square] with given parameters.
 * Missing values default to random via [ClosedFloatingPointRange.random].
 *
 * @param originParam     top-left point or random if omitted
 * @param sideLengthParam side length or random ∈ ([Float.MIN_VALUE]..[Config.MAX_SQUARE_SIDE]) if omitted
 * @param styleParam      Initial style (random colours & weight by default).
 *
 * @see Style
 * @see Shape
 * @see Point
 * @see Config
 *
 * @author  Simon Wessel
 * @version 2.7
 * @since   1.0
 */
class Square(
    originParam:     Point = Point(),
    sideLengthParam: Float = (Float.MIN_VALUE..Config.MAX_SQUARE_SIDE).random(),
    styleParam:      Style = Style()
) : BaseShape(originParam, styleParam) {

    /**
     * Backing field for side length
     *
     * @throws IllegalArgumentException if outside the allowed range.
     */
    private var _sideLength: Float = sideLengthParam.validateInRange(
        "sideLength",
        Float.MIN_VALUE,
        Config.MAX_SQUARE_SIDE
    )

    /**
     * The side length of the square.
     *
     * @return the side length.
     */
    var sideLength: Float
        /** Returns the side length of the square. */
        get() = _sideLength
        /**
         * Sets the side length of the square.
         *
         * @throws IllegalArgumentException if outside the allowed range.
         */
        set(v) {
            _sideLength = v.validateInRange(
                "sideLength",
                Float.MIN_VALUE,
                Config.MAX_SQUARE_SIDE
            )
        }

    companion object {
        /**
         * Static method to create a Square from a given area.
         *
         * @param topLeft The top left corner of the square.
         * @param area    The area of the square.
         *
         * @return A new Square object with the specified area.
         */
        fun fromArea(topLeft: Point, area: Float): Square =
            Square(topLeft, sqrt(area.toDouble()).toFloat())
    }

    /**
     * Calculates the area of the square.
     *
     * @return The area of the square.
     */
    override fun getArea(): Float = sideLength * sideLength

    /**
     * {@inheritDoc}
     *
     * For a square, this checks if the [point]'s x-coordinate is within the square's x-range
     * (from [origin].x to [origin].x + [sideLength]) and the y-coordinate is within the square's y-range
     * (from [origin].y to [origin].y + [sideLength]).
     */
    override fun contains(point: Point): Boolean =
        point.x >= origin.x
     && point.x <= origin.x + sideLength
     && point.y >= origin.y
     && point.y <= origin.y + sideLength

    /**
     * {@inheritDoc}
     *
     * For a square, the bounding box is the square itself, defined by its [origin] and [sideLength].
     */
    override fun boundingBox(): BoundingBox =
        BoundingBox(
            x = origin.x,
            y = origin.y,
            width  = sideLength,
            height = sideLength
        )

    /**
     * Draws the square with uniform scaling - maintains perfect squareness
     * while centering the entire composition.
     *
     * @param g Processing graphics context
     *
     * @see Shape.drawUniform
     */
    override fun drawUniform(g: PApplet) = withStyle(g) {
        rect(origin.x, origin.y, sideLength, sideLength)
    }

    /**
     * Draws the square with relative positioning - scales position coordinates
     * while maintaining perfect squareness through [uniformScale].
     *
     * @param g            Processing graphics context
     * @param scaleX       Horizontal scaling factor (windowWidth / baseWidth)
     * @param scaleY       Vertical scaling factor (windowHeight / baseHeight)
     * @param uniformScale Unified scaling factor for circles/squares (min(windowScaleX, windowScaleY))
     *
     * @see Shape.drawRelative
     */
    override fun drawRelative(
        g: PApplet,
        scaleX: Float,
        scaleY: Float,
        uniformScale: Float
    ) = withStyle(g) {
        val x    = origin.x   * scaleX
        val y    = origin.y   * scaleY
        val side = sideLength * uniformScale

        rect(x, y, side, side)
    }

    /**
     * Returns a string representation of the square
     *
     * @return A string representation of the square.
     */
    override fun toString(): String =
        buildString(
            listOf(
                Triple("Type", this::class.simpleName!!,           Config.PAD_TYPE   to Config.PAD_TYPE_VAL),
                Triple("X",    origin.x.formatCoordinateValue(),   Config.PAD_CORD   to Config.PAD_CORD_VAL),
                Triple("Y",    origin.y.formatCoordinateValue(),   Config.PAD_CORD   to Config.PAD_CORD_VAL),
                Triple("Side", sideLength.formatAttribute1Value(), Config.PAD_ATTR_1 to Config.PAD_ATTR_1_VAL),
                Triple("",     "",                                 (Config.PAD_ATTR_2 + Config.SEPARATOR_KEY_VALUE.length + Config.PAD_ATTR_2_VAL) to 0),
                Triple("Area", getArea().formatAreaValue(),        Config.PAD_AREA   to Config.PAD_AREA_VAL),

                Triple("Fill Color",    style.fill.toString(),
                    Config.PAD_FILL_COLR to Config.PAD_FILL_COLR_VAL),
                Triple("Stroke Color",  style.stroke.toString(),
                    Config.PAD_STRK_COLR to Config.PAD_STRK_COLR_VAL),
                Triple("Stroke Weight", style.weight.formatStrokeWeightValue(),
                    Config.PAD_STRK_WGHT to Config.PAD_STRK_WGHT_VAL)
            ))
}
