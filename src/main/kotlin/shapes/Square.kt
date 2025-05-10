package de.fhkiel.oop.shapes

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.model.Point
import de.fhkiel.oop.model.Shape
import de.fhkiel.oop.utils.FloatExtensions.formatAreaValue
import de.fhkiel.oop.utils.FloatExtensions.formatAttribute1Value
import de.fhkiel.oop.utils.FloatExtensions.formatCoordinateValue
import de.fhkiel.oop.utils.FloatExtensions.formatStrokeWeightValue
import de.fhkiel.oop.utils.RandomUtils.random
import kotlin.math.sqrt

/**
 * Square class that represents a square in 2D space.
 * This class extends the Shape class.
 *
 * Specified parameters are used as given; all others (origin, side length)
 * default to random values within the ranges defined in Config.
 *
 * @property originParam     the top-left corner of the square (default = random Point within [[0, MAX_X]]x[[0, MAX_Y]])
 * @property sideLengthParam the length of each side (default = random ∈ [[0, MAX_SQUARE_SIDE]])
 *
 * @constructor Creates a square with the specified origin and side length.
 *              If either parameter is omitted, a random value in the respective Config range is used.
 *
 * @author  Simon Wessel
 * @version 2.3
 * @since   1.0
 */
class Square(
    originParam:     Point = Point(),
    sideLengthParam: Float = (0f..Config.MAX_SQUARE_SIDE).random(),
) : Shape(originParam) {

    /** Backing field for side length */
    private var _sideLength: Float = sideLengthParam

    /**
     * The side length of the square.
     *
     * @return the side length.
     */
    var sideLength: Float
        /** Returns the side length of the square. */
        get() = _sideLength
        /** Sets the side length of the square. */
        set(v) {
            _sideLength = v
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

                Triple("Fill Color",    fillColor.toString(),
                    Config.PAD_FILL_COLR to Config.PAD_FILL_COLR_VAL),
                Triple("Stroke Color",  strokeColor.toString(),
                    Config.PAD_STRK_COLR to Config.PAD_STRK_COLR_VAL),
                Triple("Stroke Weight", strokeWeight.formatStrokeWeightValue(),
                    Config.PAD_STRK_WGHT to Config.PAD_STRK_WGHT_VAL)
            ))
}
