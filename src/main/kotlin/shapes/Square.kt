package de.fhkiel.oop.shapes

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.mapper.CoordinateMapper
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.select.BoundingBox
import de.fhkiel.oop.model.select.CornerHandleStrategy
import de.fhkiel.oop.model.select.HandleStrategy
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
     * Returns the bounding box of the square.
     * The bounding box is defined by the top-left corner and the side length.
     *
     * @return The [BoundingBox] of the square.
     */
    override fun boundingBox(): BoundingBox = BoundingBox(origin.x, origin.y, sideLength, sideLength)

    /**
     * Returns the handle strategy for this shape.
     * For a square, the [CornerHandleStrategy] is used to handle corner points.
     *
     * @return The [HandleStrategy] to use for this shape.
     */
    override fun handleStrategy(): HandleStrategy = CornerHandleStrategy

    /**
     * Draws the circle on the given [PApplet] using the provided [CoordinateMapper].
     *
     * @param g      The PApplet to draw on.
     * @param mapper The coordinate mapper to use for drawing.
     */
    override fun draw(g: PApplet, mapper: CoordinateMapper) = withStyle(g) {
        val (x, y)     = mapper.worldToScreen(this@Square.origin.x, this@Square.origin.y)
        val sideLength = mapper.worldScalarToScreen(this@Square.sideLength)
        rect(x, y, sideLength, sideLength)
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
