package de.fhkiel.oop.utils

import de.fhkiel.oop.config.AppConfig
import de.fhkiel.oop.config.DefaultConfig
import java.util.Locale
import kotlin.math.absoluteValue

/**
 * Utility functions for formatting [Float] values according to global
 * padding and precision settings in [de.fhkiel.oop.config.AppConfig].
 *
 * Provides methods to:
 * 1. Determine integer digit count via [decimalPlaces].
 * 2. Format any value with custom width/precision via [format].
 * 3. Format common shape attributes ([formatCoordinateValue], [formatAttribute1Value],
 * [formatAttribute2Value], [formatAreaValue], [formatStrokeWeightValue])
 * using corresponding [de.fhkiel.oop.config.AppConfig] constants.
 *
 * ### Example
 * ```kotlin
 * val x = 123.456f
 * println(x.formatCoordinateValue())     // uses Config.PAD_CORD_VAL & PAD_CORD_PRECISION
 * println(x.format(width = 8, precision = 3))
 * ```
 *
 * @author  Simon Wessel
 * @version 1.2
 * @since   2.1
 */
object FloatExtensions {

    /**
     * Extension for Float to enforce an inclusive range check.
     *
     * @param name  Name of the variable for error message.
     * @param lo    Lower bound of the range.
     * @param hi    Upper bound of the range.
     * @return      the validated float value for chaining.
     * @throws IllegalArgumentException if `this` is not in [lo]..[hi].
     */
    fun Float.validateInRange(name: String, lo: Float, hi: Float): Float {
        require(this in lo..hi) { "$name must be in $lo..$hi but was $this" }
        return this
    }

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
     * @param precision number of digits after the decimal vector
     * @param locale    locale for decimal separator (default = [com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDLoader.LOCALE])
     *
     * @return formatted string, e.g. `"%6.2f"` style
     */
    @JvmStatic
    fun Float.format(width: Int, precision: Int, locale: Locale): String =
        String.format(locale, "%${width}.${precision}f", this)

    /**
     * Formats this [Float] as a coordinate value, using
     * [de.fhkiel.oop.config.DefaultConfig.padCordVal] and [de.fhkiel.oop.config.DefaultConfig.padCordPrecision].
     *
     * @receiver the coordinate value
     *
     * @return formatted coordinate string
     */
    @JvmStatic
    fun Float.formatCoordinateValue(config: AppConfig = DefaultConfig): String =
        format(
            width     = config.padCordVal,
            precision = config.padCordPrecision,
            locale    = config.locale
        )

    /**
     * Formats this [Float] as the first attribute value (e.g. radius, width),
     * using [de.fhkiel.oop.config.DefaultConfig.padAttr1Val] and [de.fhkiel.oop.config.DefaultConfig.padAttr1Precision].
     *
     * @receiver the attribute value
     *
     * @return formatted attribute string
     */
    @JvmStatic
    fun Float.formatAttribute1Value(config: AppConfig = DefaultConfig): String =
        format(
            width     = config.padAttr1Val,
            precision = config.padAttr1Precision,
            locale    = config.locale
        )

    /**
     * Formats this [Float] as the second attribute value (e.g. height),
     * using [de.fhkiel.oop.config.DefaultConfig.padAttr2Val] and [de.fhkiel.oop.config.DefaultConfig.padAttr2Precision].
     *
     * @receiver the attribute value
     *
     * @return formatted attribute string
     */
    @JvmStatic
    fun Float.formatAttribute2Value(config: AppConfig = DefaultConfig): String =
        format(
            width     = config.padAttr2Val,
            precision = config.padAttr2Precision,
            locale    = config.locale
        )

    /**
     * Formats this [Float] as an area value, using
     * [de.fhkiel.oop.config.DefaultConfig.padAreaVal] and [de.fhkiel.oop.config.DefaultConfig.padAreaPrecision].
     *
     * @receiver the area value
     *
     * @return formatted area string
     */
    @JvmStatic
    fun Float.formatAreaValue(config: AppConfig = DefaultConfig): String =
        format(
            width     = config.padAreaVal,
            precision = config.padAreaPrecision,
            locale    = config.locale
        )


    /**
     * Formats this [Float] as the stroke weight value, using
     * [de.fhkiel.oop.config.DefaultConfig.padStrkWghtVal] and [de.fhkiel.oop.config.DefaultConfig.padStrkWghtPrecision].
     *
     * @receiver the stroke weight value
     *
     * @return formatted stroke weight string
     */
    @JvmStatic
    fun Float.formatStrokeWeightValue(config: AppConfig = DefaultConfig): String =
        format(
            width     = config.padStrkWghtVal,
            precision = config.padStrkWghtPrecision,
            locale    = config.locale
        )
}
