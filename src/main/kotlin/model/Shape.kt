package de.fhkiel.oop.model

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.utils.Color
import de.fhkiel.oop.utils.RandomUtils.random
import de.fhkiel.oop.utils.RandomUtils.randomColor

/**
 * Abstract base for all 2D shapes.
 *
 * Provides common properties and random defaults:
 * - [origin]:       position in the canvas
 * - [fillColor]:    ARGB fill color
 * - [strokeColor]:  ARGB stroke color
 * - [strokeWeight]: border thickness
 *
 * @property origin       top-left or center point of the shape
 * @property fillColor    interior color as [Color]
 * @property strokeColor  border color as [Color]
 * @property strokeWeight border thickness ∈ ([Config.MIN_STRK_WEIGHT]..[Config.MAX_STRK_WEIGHT])
 *
 * @constructor Creates a [Shape] with the specified properties.
 *             Omitted params are randomized.
 *
 * @param originParam       initial [Point] or random if omitted
 * @param fillColorParam    initial fill [Color] or random if omitted
 * @param strokeColorParam  initial stroke [Color] or random if omitted
 * @param strokeWeightParam initial stroke weight or random ∈ ([Config.MIN_STRK_WEIGHT]..[Config.MAX_STRK_WEIGHT])
 *
 * @author  Simon Wessel
 * @version 2.3
 * @since   1.5
 */
abstract class Shape(
    originParam:       Point = Point(),
    fillColorParam:    Color = randomColor(),
    strokeColorParam:  Color = randomColor(),
    strokeWeightParam: Float = (Config.MIN_STRK_WEIGHT..Config.MAX_STRK_WEIGHT).random()
) {

    /** Backing field for location */
    private var _origin: Point = originParam

    /**
     * The location of the shape.
     *
     * @return the location point.
     */
    var origin: Point
        /** Returns the location of the shape. */
        get() = _origin
        /** Sets the location of the shape. */
        set(v) {
            _origin = v
        }

    /** Backing field for fill color */
    private var _fillColor: Color = fillColorParam

    /**
     * The fill color of the shape.
     *
     * @return the fill color as ARGB int.
     */
    var fillColor: Color
        /** Returns the fill color. */
        get() = _fillColor
        /** Sets the fill color. */
        set(v) {
            _fillColor = v
        }

    /** Backing field for stroke color */
    private var _strokeColor: Color = strokeColorParam

    /**
     * The stroke (border) color of the shape.
     *
     * @return the stroke color as ARGB int.
     */
    var strokeColor: Color
        /** Returns the stroke (border) color. */
        get() = _strokeColor
        /** Sets the stroke (border) color. */
        set(v) {
            _strokeColor = v
        }

    /** Backing field for stroke weight */
    private var _strokeWeight: Float = strokeWeightParam

    /**
     * The stroke weight (border thickness) of the shape.
     *
     * @return the stroke weight.
     */
    var strokeWeight: Float
        /** Returns the stroke weight. */
        get() = _strokeWeight
        /** Sets the stroke weight. */
        set(v) {
            _strokeWeight = v
        }

    /**
     * Abstract method to calculate the area of the shape.
     *
     * @return The area of the shape.
     */
    abstract fun getArea(): Float

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
