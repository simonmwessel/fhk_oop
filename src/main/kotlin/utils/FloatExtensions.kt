package de.fhkiel.oop.utils

import de.fhkiel.oop.config.Config
import java.util.Locale
import kotlin.math.absoluteValue

/**
 * Utility functions for formatting [Float] values according to global
 * padding and precision settings in [Config].
 *
 * Provides methods to:
 * 1. Determine integer digit count via [decimalPlaces].
 * 2. Format any value with custom width/precision via [format].
 * 3. Format common shape attributes ([formatCoordinateValue], [formatAttribute1Value],
 * [formatAttribute2Value], [formatAreaValue], [formatStrokeWeightValue])
 * using corresponding [Config] constants.
 *
 * ### Example
 * ```kotlin
 * val x = 123.456f
 * println(x.formatCoordinateValue())     // uses Config.PAD_CORD_VAL & PAD_CORD_PRECISION
 * println(x.format(width = 8, precision = 3))
 * ```
 *
 * @author  Simon Wessel
 * @version 1.1
 * @since   2.1
 */
object FloatExtensions {

    /**
     * Returns the number of digits in the integer part of this [Float].
     *
     * @receiver the float to inspect
     *
     * @return count of digits in `toInt().absoluteValue`
     */
    @JvmStatic
    fun Float.decimalPlaces(): Int =
        this.toInt()
            .absoluteValue
            .toString()
            .length

    /**
     * Formats this [Float] with given total width and number of decimal places.
     *
     * @receiver   the float to format
     *
     * @param width     total minimum width (including dot and decimals)
     * @param precision number of digits after the decimal point
     * @param locale    locale for decimal separator (default = [Config.LOCALE])
     *
     * @return formatted string, e.g. `"%6.2f"` style
     */
    @JvmStatic
    fun Float.format(width: Int, precision: Int, locale: Locale = Config.LOCALE): String =
        String.format(locale, "%${width}.${precision}f", this)


    /**
     * Formats this [Float] as a coordinate value, using
     * [Config.PAD_CORD_VAL] and [Config.PAD_CORD_PRECISION].
     *
     * @receiver the coordinate value
     *
     * @return formatted coordinate string
     */
    @JvmStatic
    fun Float.formatCoordinateValue(): String =
        format(
            width     = Config.PAD_CORD_VAL,
            precision = Config.PAD_CORD_PRECISION,
            locale    = Config.LOCALE
        )

    /**
     * Formats this [Float] as the first attribute value (e.g. radius, width),
     * using [Config.PAD_ATTR_1_VAL] and [Config.PAD_ATTR_1_PRECISION].
     *
     * @receiver the attribute value
     *
     * @return formatted attribute string
     */
    @JvmStatic
    fun Float.formatAttribute1Value(): String =
        format(
            width     = Config.PAD_ATTR_1_VAL,
            precision = Config.PAD_ATTR_1_PRECISION,
            locale    = Config.LOCALE
        )

    /**
     * Formats this [Float] as the second attribute value (e.g. height),
     * using [Config.PAD_ATTR_2_VAL] and [Config.PAD_ATTR_2_PRECISION].
     *
     * @receiver the attribute value
     *
     * @return formatted attribute string
     */
    @JvmStatic
    fun Float.formatAttribute2Value(): String =
        format(
            width     = Config.PAD_ATTR_2_VAL,
            precision = Config.PAD_ATTR_2_PRECISION,
            locale    = Config.LOCALE
        )

    /**
     * Formats this [Float] as an area value, using
     * [Config.PAD_AREA_VAL] and [Config.PAD_AREA_PRECISION].
     *
     * @receiver the area value
     *
     * @return formatted area string
     */
    @JvmStatic
    fun Float.formatAreaValue(): String =
        format(
            width     = Config.PAD_AREA_VAL,
            precision = Config.PAD_AREA_PRECISION,
            locale    = Config.LOCALE
        )


    /**
     * Formats this [Float] as the stroke weight value, using
     * [Config.PAD_STRK_WGHT_VAL] and [Config.PAD_STRK_WGHT_PRECISION].
     *
     * @receiver the stroke weight value
     *
     * @return formatted stroke weight string
     */
    @JvmStatic
    fun Float.formatStrokeWeightValue(): String =
        format(
            width     = Config.PAD_STRK_WGHT_VAL,
            precision = Config.PAD_STRK_WGHT_PRECISION,
            locale    = Config.LOCALE
        )
}
