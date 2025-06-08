package de.fhkiel.oop.shapes

import de.fhkiel.oop.config.AppConfig
import de.fhkiel.oop.config.DefaultConfig
import de.fhkiel.oop.config.ShapeStrategyConfig
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.Vector2D
import de.fhkiel.oop.model.Shape
import de.fhkiel.oop.model.Style
import de.fhkiel.oop.utils.RandomUtils.random
import kotlin.Float
import kotlin.math.sqrt

/**
 * A square defined by its top-left corner and side length.
 *
 * @property origin The top-left corner of the square.
 * @property width  Width in units ∈ ([de.fhkiel.oop.config.DefaultConfig.minSquareSide]..[de.fhkiel.oop.config.DefaultConfig.maxSquareSide]).
 * @property height Height in units ∈ ([de.fhkiel.oop.config.DefaultConfig.minSquareSide]..[de.fhkiel.oop.config.DefaultConfig.maxSquareSide]).
 *
 * @constructor Creates a [Square] with given parameters.
 * Missing values default to random via [ClosedFloatingPointRange.random].
 *
 * @param originParam     top-left vector or random if omitted
 * @param sideLengthParam side length or random ∈ ([de.fhkiel.oop.config.DefaultConfig.minSquareSide]..[de.fhkiel.oop.config.DefaultConfig.maxSquareSide]) if omitted
 * @param styleParam      Initial style (random colours & weight by default).
 * @param strategiesParam Shape strategies configuration for the square.
 *
 * @see Shape
 * @see BaseShape
 * @see Rectangle
 * @see Style
 * @see Vector2D
 * @see AppConfig
 * @see DefaultConfig
 *
 * @author Simon Wessel
 */
class Square(
    config: AppConfig = DefaultConfig,
    originParam:        Vector2D = Vector2D(),
    sideLengthParam:    Float = (config.minSquareSide..config.maxSquareSide).random(),
    styleParam:         Style = Style(),
    strategiesParam:    ShapeStrategyConfig = ShapeStrategyConfig.SQUARE
) : Rectangle(
    config,
    originParam,
    sideLengthParam,
    sideLengthParam,
    styleParam,
    strategiesParam
) {

    /**
     * Width of the square, which is equal to the height.
     *
     * @return The width of the square, which is the same as the height.
     */
    override var width: Float
        /** returns the width and height of the square */
        get() = super.width
        /**
         * Sets the width and height to the same value
         *
         * @throws IllegalArgumentException if the value is outside the allowed range.
         */
        set(v) {
            super.width  = v
            super.height = v
        }

    /**
     * Height of the square, which is equal to the width.
     *
     * @return The height of the square, which is the same as the width.
     */
    override var height: Float
        /** returns the height and width of the square */
        get() = super.height
        /**
         * Sets the height and width to the same value
         *
         * @throws IllegalArgumentException if the value is outside the allowed range.
         */
        set(v) {
            super.width  = v
            super.height = v
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
        fun fromArea(topLeft: Vector2D, area: Float, config: AppConfig = DefaultConfig): Square =
            Square(config, topLeft, sqrt(area.toDouble()).toFloat())
    }

    /**
     * Returns a string representation of the rectangle
     *
     * @return A string representation of the rectangle.
     */
    override fun toString(): String =
        // Needs to be overridden to print the correct type ("Square" instead of "Rectangle")
        super.toString()
}
