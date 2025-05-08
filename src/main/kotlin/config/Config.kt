package de.fhkiel.oop.config

import de.fhkiel.oop.utils.FloatExtensions.decimalPlaces
import java.util.Locale
import kotlin.math.pow

/**
 * Global configuration for all Shape classes.
 *
 * Holds maximum values for coordinate and size generation,
 * as well as formatting options for string output.
 *
 * @property MAX_X                Maximum X value for random coordinate generation.
 * @property MAX_Y                Maximum Y value for random coordinate generation.
 * @property MAX_DECIMAL_PLACES   Maximum decimal places for coordinate, calculated from MAX_X and MAX_Y.
 * @property LOCALE               Locale for formatting numbers.
 * @property SEPARATOR            Separator for different attributes.
 * @property SEPARATOR_KEY_VALUE  Separator for key-value pairs.
 * @property PREFIX               Prefix for string output.
 * @property SUFFIX               Suffix for string output.
 * @property PAD_TYPE             Padding for the shape type.
 * @property PAD_TYPE_VAL         Padding for the shape type value.
 * @property PAD_CORD             Padding for the shape coordinates.
 * @property PAD_CORD_PRECISION   Padding for the shape coordinates decimal value precision.
 * @property PAD_CORD_VAL         Padding for the shape coordinates decimal value.
 * @property PAD_ATTR_1           Padding for the first shape attribute.
 * @property PAD_ATTR_1_PRECISION Padding for the first shape attribute value precision.
 * @property PAD_ATTR_1_VAL       Padding for the first shape attribute value.
 * @property PAD_ATTR_2           Padding for the second shape attribute.
 * @property PAD_ATTR_2_PRECISION Padding for the second shape attribute value precision.
 * @property PAD_ATTR_2_VAL       Padding for the second shape attribute value.
 * @property PAD_AREA             Padding for the shape area.
 * @property PAD_AREA_PRECISION   Padding for the shape area value precision.
 * @property PAD_AREA_VAL         Padding for the shape area value.
 *
 * @author  Simon Wessel
 * @version 1.0
 * @since   2.1
 */
object Config {

    /** Maximum X value for random coordinate generation. */
    const val MAX_X: Float = 100f

    /** Maximum Y value for random coordinate generation. */
    const val MAX_Y: Float = 100f

    /** Maximum decimal places for coordinate. */
    val MAX_DECIMAL_PLACES: Int =
        maxOf(
            MAX_X.decimalPlaces(),
            MAX_Y.decimalPlaces()
        )

    // Constants for padding when building toString output

    /** Locale for formatting numbers */
    val LOCALE: Locale = Locale.GERMANY

    /** Separator for different attributes */
    const val SEPARATOR:            String = " | "
    /** Separator for key-value pairs */
    const val SEPARATOR_KEY_VALUE:  String = " : "
    /** Prefix for string output */
    const val PREFIX:               String = "{ "
    /** Suffix for string output */
    const val SUFFIX:               String = " }"

    /** Padding for the shape type */
    const val PAD_TYPE:             Int = 4
    /** Padding for the shape type value */
    const val PAD_TYPE_VAL:         Int = 9

    /** Padding for the shape coordinates */
    const val PAD_CORD:             Int = 1
    /** Padding for the shape coordinates decimal value precision */
    const val PAD_CORD_PRECISION:   Int = 2
    /** Padding for the shape coordinates decimal value */
    val PAD_CORD_VAL:               Int = MAX_DECIMAL_PLACES + PAD_CORD_PRECISION

    /** Padding for the first shape attribute */
    const val PAD_ATTR_1:           Int = 6
    /** Padding for the first shape attribute value precision */
    const val PAD_ATTR_1_PRECISION: Int = 2
    /** Padding for the first shape attribute value */
    val PAD_ATTR_1_VAL:             Int = MAX_DECIMAL_PLACES + PAD_ATTR_1_PRECISION

    /** Padding for the second shape attribute */
    const val PAD_ATTR_2:           Int = 6
    /** Padding for the second shape attribute value precision */
    const val PAD_ATTR_2_PRECISION: Int = 2
    /** Padding for the second shape attribute value */
    val PAD_ATTR_2_VAL:             Int = MAX_DECIMAL_PLACES + PAD_ATTR_2_PRECISION

    /** Padding for the shape area */
    const val PAD_AREA:             Int = 4
    /** Padding for the shape area value precision */
    const val PAD_AREA_PRECISION:   Int = 2
    /** Padding for the shape area value */
    val PAD_AREA_VAL:               Int = (MAX_DECIMAL_PLACES.toDouble().pow(2) + PAD_AREA_PRECISION).toInt()
}