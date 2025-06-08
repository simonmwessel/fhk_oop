package de.fhkiel.oop.config

import de.fhkiel.oop.utils.Color
import de.fhkiel.oop.utils.FloatExtensions.decimalPlaces
import java.util.Locale

/**
 * Global configuration for all shapes and formatting routines.
 *
 * Defines canvas size limits, maximum shape dimensions, stroke weight bounds,
 * locale for numeric formatting, and padding/precision constants for
 * string output.
 *
 * All numeric ranges and padding values feed into:
 * - [de.fhkiel.oop.model.Vector2D]
 * - [de.fhkiel.oop.model.Shape]
 * - [de.fhkiel.oop.shapes.Circle], [de.fhkiel.oop.shapes.Rectangle], [de.fhkiel.oop.shapes.Square]
 * - [de.fhkiel.oop.utils.FloatExtensions] methods
 *
 * @property MAX_X                   Maximum X value for random coordinate generation.
 * @property MAX_Y                   Maximum Y value for random coordinate generation.
 * @property MAX_CIRCLE_RADIUS       Maximum circle radius value for random generation.
 * @property MAX_RECT_WIDTH          Maximum rectangle width value for random generation.
 * @property MAX_RECT_HEIGHT         Maximum rectangle height value for random generation.
 * @property MAX_RECT_HEIGHT         Maximum rectangle height value for random generation.
 * @property MAX_SQUARE_SIDE         Maximum square side length value for random generation.
 * @property MIN_STRK_WEIGHT         Minimum stroke weight value for random generation.
 * @property MAX_STRK_WEIGHT         Maximum stroke weight value for random generation.
 * @property SKETCH_BACKGROUND_COLOR Background color for sketch canvas.
 * @property LOCALE                  Locale for formatting numbers.
 * @property SEPARATOR               Separator for different attributes.
 * @property SEPARATOR_KEY_VALUE     Separator for key-value pairs.
 * @property PREFIX                  Prefix for string output.
 * @property SUFFIX                  Suffix for string output.
 * @property PAD_TYPE                Padding for the shape type.
 * @property PAD_TYPE_VAL            Padding for the shape type value.
 * @property PAD_CORD                Padding for the shape coordinates.
 * @property PAD_CORD_PRECISION      Padding for the shape coordinates decimal value precision.
 * @property PAD_CORD_VAL            Padding for the shape coordinates decimal value.
 * @property PAD_ATTR_1              Padding for the first shape attribute.
 * @property PAD_ATTR_1_PRECISION    Padding for the first shape attribute value precision.
 * @property PAD_ATTR_1_VAL          Padding for the first shape attribute value.
 * @property PAD_ATTR_2              Padding for the second shape attribute.
 * @property PAD_ATTR_2_PRECISION    Padding for the second shape attribute value precision.
 * @property PAD_ATTR_2_VAL          Padding for the second shape attribute value.
 * @property PAD_AREA                Padding for the shape area.
 * @property PAD_AREA_PRECISION      Padding for the shape area value precision.
 * @property PAD_AREA_VAL            Padding for the shape area value.
 * @property PAD_FILL_COLR           Padding for the fill color.
 * @property PAD_FILL_COLR_VAL       Padding for the fill color value.
 * @property PAD_STRK_COLR           Padding for the stroke color.
 * @property PAD_STRK_COLR_VAL       Padding for the stroke color value.
 * @property PAD_STRK_WGHT           Padding for the stroke weight.
 * @property PAD_STRK_WGHT_PRECISION Padding for the stroke weight value precision.
 * @property PAD_STRK_WGHT_VAL       Padding for the stroke weight value.
 *
 * @author  Simon Wessel
 * @version 1.1
 * @since   2.1
 */
object Config {

    /** Enable debug mode for additional logging and checks. */
    var DEBUG:                 Boolean = false

    /** Maximum X value for random coordinate generation. */
    const val MAX_X:             Float = 1800f

    /** Maximum Y value for random coordinate generation. */
    const val MAX_Y:             Float = 1200f

    /** Minimum circle radius value for random generation. */
    const val MIN_CIRCLE_RADIUS: Float = 10f

    /** Maximum circle radius value for random generation. */
    val MAX_CIRCLE_RADIUS:       Float = minOf(MAX_X, MAX_Y) / 2

    /** Minimum rectangle width value for random generation. */
    const val MIN_RECT_WIDTH:    Float = 10f

    /** Maximum rectangle width value for random generation. */
    const val MAX_RECT_WIDTH:    Float = MAX_X

    /** Minimum rectangle height value for random generation. */
    const val MIN_RECT_HEIGHT:   Float = 10f

    /** Maximum rectangle width value for random generation. */
    const val MAX_RECT_HEIGHT:   Float = MAX_Y

    /** Minimum square side length value for random generation. */
    const val MIN_SQUARE_SIDE:   Float = 10f

    /** Maximum square side length value for random generation. */
    val MAX_SQUARE_SIDE:         Float = minOf(MAX_X, MAX_Y)

    /** Minimum stroke weight value for random generation */
    const val MIN_STRK_WEIGHT:   Float = .5f

    /** Maximum stroke weight value for random generation */
    const val MAX_STRK_WEIGHT:   Float = 10f

    /** Background color for sketch canvas */
    val SKETCH_BACKGROUND_COLOR: Color = Color(0x2B, 0x2B, 0x2B)

    // Constants for padding when building toString output

    /** Locale for formatting numbers */
    val LOCALE: Locale = Locale.GERMANY

    /** Separator for different attributes */
    const val SEPARATOR:               String = " | "
    /** Separator for key-value pairs */
    const val SEPARATOR_KEY_VALUE:     String = " : "
    /** Prefix for string output */
    const val PREFIX:                  String = " { "
    /** Suffix for string output */
    const val SUFFIX:                  String = " } "

    /** Padding for the shape type */
    const val PAD_TYPE:                Int = 4
    /** Padding for the shape type value */
    const val PAD_TYPE_VAL:            Int = 9

    /** Padding for the shape coordinates */
    const val PAD_CORD:                Int = 1
    /** Padding for the shape coordinates decimal value precision */
    const val PAD_CORD_PRECISION:      Int = 2
    /** Padding for the shape coordinates decimal value */
    val PAD_CORD_VAL:                  Int = 1 + maxOf(MAX_X.decimalPlaces(), MAX_Y.decimalPlaces()) + PAD_CORD_PRECISION

    /** Padding for the first shape attribute */
    const val PAD_ATTR_1:              Int = 6
    /** Padding for the first shape attribute value precision */
    const val PAD_ATTR_1_PRECISION:    Int = 2
    /** Padding for the first shape attribute value */
    val PAD_ATTR_1_VAL:                Int = 1 + maxOf(MAX_CIRCLE_RADIUS, MAX_RECT_WIDTH, MAX_SQUARE_SIDE).decimalPlaces() + PAD_ATTR_1_PRECISION

    /** Padding for the second shape attribute */
    const val PAD_ATTR_2:              Int = 6
    /** Padding for the second shape attribute value precision */
    const val PAD_ATTR_2_PRECISION:    Int = 2
    /** Padding for the second shape attribute value */
    val PAD_ATTR_2_VAL:                Int = 1 + maxOf(MAX_RECT_HEIGHT, MAX_SQUARE_SIDE).decimalPlaces() + PAD_ATTR_2_PRECISION

    /** Padding for the shape area */
    const val PAD_AREA:                Int = 4
    /** Padding for the shape area value precision */
    const val PAD_AREA_PRECISION:      Int = 2
    /** Padding for the shape area value */
    val PAD_AREA_VAL:                  Int = 1 + (MAX_X * MAX_Y).decimalPlaces() + PAD_AREA_PRECISION

    /** Padding for the fill color */
    const val PAD_FILL_COLR:           Int = 10
    /** Padding for the fill color value */
    const val PAD_FILL_COLR_VAL:       Int = 7

    /** Padding for the stroke color */
    const val PAD_STRK_COLR:           Int = 12
    /** Padding for the stroke color value */
    const val PAD_STRK_COLR_VAL:       Int = 7

    /** Padding for the stroke weight */
    const val PAD_STRK_WGHT:           Int = 13
    /** Padding for the stroke weight value precision */
    const val PAD_STRK_WGHT_PRECISION: Int = 2
    /** Padding for the stroke weight value */
    val PAD_STRK_WGHT_VAL:             Int = 1 + MAX_STRK_WEIGHT.decimalPlaces() + PAD_STRK_WGHT_PRECISION
}
