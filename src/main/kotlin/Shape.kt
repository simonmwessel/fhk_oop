package main.kotlin

import kotlin.random.Random

/**
 * Shape class that represents a generic shape in 2D space.
 * This class serves as a base class for specific shapes like Circle, Rectangle, and Square.
 *
 * @property location     Position of the shape.
 * @property strokeColor  ARGB color used for the border.
 * @property fillColor    ARGB color used for filling the shape.
 * @property strokeWeight Thickness of the border line.
 *
 * @author  Simon Wessel
 * @version 2.2
 * @since   1.5
 */
abstract class Shape(locationParam: Point) {

    /** Backing field for location */
    private var _location: Point = locationParam

    /**
     * The location of the shape.
     *
     * @return the location point.
     */
    var location: Point
        /** Returns the location of the shape. */
        get() = _location
        /** Sets the location of the shape. */
        set(v) {
            _location = v
        }

    /** Backing field for stroke color */
    private var _strokeColor: Int = 0xFF000000.toInt()

    /**
     * The stroke (border) color of the shape.
     *
     * @return the stroke color as ARGB int.
     */
    var strokeColor: Int
        /** Returns the stroke (border) color. */
        get() = _strokeColor
        /** Sets the stroke (border) color. */
        set(v) {
            _strokeColor = v
        }

    /** Backing field for fill color */
    private var _fillColor: Int = 0xFFFFFFFF.toInt()

    /**
     * The fill color of the shape.
     *
     * @return the fill color as ARGB int.
     */
    var fillColor: Int
        /** Returns the fill color. */
        get() = _fillColor
        /** Sets the fill color. */
        set(v) {
            _fillColor = v
        }

    /** Backing field for stroke weight */
    private var _strokeWeight: Float = 1.0f

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
     * Secondary constructor for Shape without parameters.
     * Initializes the location, stroke-, and fill color and stroke weight with a random value.
     */
    constructor() : this(Point()) {
        strokeColor  = (0x000000..0xFFFFFF).random() or (0xFF shl 24)
        fillColor    = (0x000000..0xFFFFFF).random() or (0xFF shl 24)
        strokeWeight = Random.nextFloat() * 4.5f + 0.5f
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
     *      - padKeyWidth:   number of characters to pad the key using padEnd()
     *      - padValueWidth: number of characters to pad the value using padStart()
     *
     * The final output is constructed as:
     *  1. Config.PREFIX
     *  2. columns.joinToString(Config.SEPARATOR) { … }  // each column formatted and joined
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