package utils

import main.kotlin.Config
import java.util.Locale
import kotlin.math.absoluteValue

/**
 * Utility functions for formatting Float values.
 *
 * @author  Simon Wessel
 * @version 1.0
 * @since   2.1
 */
object FloatExtensions {

    /**
     * Returns the number of digits in the integer part of this Float.
     */
    @JvmStatic
    fun Float.decimalPlaces(): Int =
        this.toInt()
            .absoluteValue
            .toString()
            .length

    /**
     * Formats this Float with dynamic width and precision.
     *
     * @param width     total minimum width (including dot and decimals)
     * @param precision number of digits after the decimal point
     * @param locale    Locale for decimal separator (default US = dot)
     *
     * @return          formatted String, e.g. "%6.2f"
     */
    @JvmStatic
    fun Float.format(width: Int, precision: Int, locale: Locale = Config.LOCALE): String =
        String.format(locale, "%${width}.${precision}f", this)

    /**
     * Formats this Float as a coordinate value using Config.PAD_CORD_VAL
     * and Config.PAD_CORD_PRECISION as precision.
     */
    @JvmStatic
    fun Float.formatCoordinateValue(): String =
        format(
            width     = Config.PAD_CORD_VAL,
            precision = Config.PAD_CORD_PRECISION,
            locale    = Config.LOCALE
        )

    /**
     * Formats this Float as the first attribute value using Config.PAD_ATTR_1_VAL
     * and Config.PAD_ATTR_1_PRECISION as precision.
     */
    @JvmStatic
    fun Float.formatAttribute1Value(): String =
        format(
            width     = Config.PAD_ATTR_1_VAL,
            precision = Config.PAD_ATTR_1_PRECISION,
            locale    = Config.LOCALE
        )

    /**
     * Formats this Float as the second attribute value using Config.PAD_ATTR_2_VAL
     * and Config.PAD_ATTR_2_PRECISION as precision.
     */
    @JvmStatic
    fun Float.formatAttribute2Value(): String =
        format(
            width     = Config.PAD_ATTR_2_VAL,
            precision = Config.PAD_ATTR_2_PRECISION,
            locale    = Config.LOCALE
        )

    /**
     * Formats this Float as an area value using Config.PAD_AREA_VAL
     * and Config.PAD_AREA_PRECISION as precision.
     */
    @JvmStatic
    fun Float.formatAreaValue(): String =
        format(
            width     = Config.PAD_AREA_VAL,
            precision = Config.PAD_AREA_PRECISION,
            locale    = Config.LOCALE
        )
}
