package de.fhkiel.oop.config

import de.fhkiel.oop.utils.Color
import de.fhkiel.oop.utils.FloatExtensions.decimalPlaces
import java.util.Locale

/**
 * Default implementation of the [AppConfig] interface.
 *
 * This object provides the concrete values for all configuration properties
 * defined in [AppConfig], effectively mirroring the original `Config.kt` object.
 * It serves as the standard configuration set for the application unless overridden.
 */
object DefaultConfig : AppConfig {
    /** @property debug Enables or disables debug mode for additional logging and checks. */
    override var debug: Boolean = false

    /** @property maxX Maximum X value for random coordinate generation. */
    override val maxX: Float = 1800f
    /** @property maxY Maximum Y value for random coordinate generation. */
    override val maxY: Float = 1200f

    /** @property minCircleRadius Minimum circle radius value for random generation. */
    override val minCircleRadius: Float = 10f
    /** @property maxCircleRadius Maximum circle radius value for random generation. */
    override val maxCircleRadius: Float = minOf(maxX, maxY) / 2

    /** @property minRectWidth Minimum rectangle width value for random generation. */
    override val minRectWidth: Float = 10f
    /** @property maxRectWidth Maximum rectangle width value for random generation. */
    override val maxRectWidth: Float = maxX
    /** @property minRectHeight Minimum rectangle height value for random generation. */
    override val minRectHeight: Float = 10f
    /** @property maxRectHeight Maximum rectangle height value for random generation. */
    override val maxRectHeight: Float = maxY

    /** @property minSquareSide Minimum square side length value for random generation. */
    override val minSquareSide: Float = 10f
    /** @property maxSquareSide Maximum square side length value for random generation. */
    override val maxSquareSide: Float = minOf(maxX, maxY)

    /** @property minStrkWeight Minimum stroke weight value for random generation. */
    override val minStrkWeight: Float = .5f
    /** @property maxStrkWeight Maximum stroke weight value for random generation. */
    override val maxStrkWeight: Float = 10f

    /** @property sketchBackgroundColor Background color for the sketch canvas. */
    override val sketchBackgroundColor: Color = Color(0x2B, 0x2B, 0x2B)

    /** @property locale Locale used for formatting numbers. */
    override val locale: Locale = Locale.GERMANY

    /** @property separator Separator string for different attributes in string representations. */
    override val separator: String = " | "
    /** @property separatorKeyValue Separator string for key-value pairs in string representations. */
    override val separatorKeyValue: String = " : "
    /** @property prefix Prefix string for overall string output of shapes. */
    override val prefix: String = " { "
    /** @property suffix Suffix string for overall string output of shapes. */
    override val suffix: String = " } "

    /** @property padType Padding for the shape type field in string representations. */
    override val padType: Int = 4
    /** @property padTypeVal Padding for the shape type value in string representations. */
    override val padTypeVal: Int = 9

    /** @property padCord Padding for the shape coordinates field in string representations. */
    override val padCord: Int = 1
    /** @property padCordPrecision Precision for shape coordinate decimal values in string representations. */
    override val padCordPrecision: Int = 2
    /** @property padCordVal Padding for the shape coordinate decimal values in string representations. */
    override val padCordVal: Int = 1 + maxOf(maxX.decimalPlaces(), maxY.decimalPlaces()) + padCordPrecision

    /** @property padAttr1 Padding for the first shape attribute field in string representations. */
    override val padAttr1: Int = 6
    /** @property padAttr1Precision Precision for the first shape attribute value in string representations. */
    override val padAttr1Precision: Int = 2
    /** @property padAttr1Val Padding for the first shape attribute value in string representations. */
    override val padAttr1Val: Int = 1 + maxOf(maxCircleRadius, maxRectWidth, maxSquareSide).decimalPlaces() + padAttr1Precision

    /** @property padAttr2 Padding for the second shape attribute field in string representations. */
    override val padAttr2: Int = 6
    /** @property padAttr2Precision Precision for the second shape attribute value in string representations. */
    override val padAttr2Precision: Int = 2
    /** @property padAttr2Val Padding for the second shape attribute value in string representations. */
    override val padAttr2Val: Int = 1 + maxOf(maxRectHeight, maxSquareSide).decimalPlaces() + padAttr2Precision

    /** @property padArea Padding for the shape area field in string representations. */
    override val padArea: Int = 4
    /** @property padAreaPrecision Precision for the shape area value in string representations. */
    override val padAreaPrecision: Int = 2
    /** @property padAreaVal Padding for the shape area value in string representations. */
    override val padAreaVal: Int = 1 + (maxX * maxY).decimalPlaces() + padAreaPrecision

    /** @property padFillColr Padding for the fill color field in string representations. */
    override val padFillColr: Int = 10
    /** @property padFillColrVal Padding for the fill color value in string representations. */
    override val padFillColrVal: Int = 7

    /** @property padStrkColr Padding for the stroke color field in string representations. */
    override val padStrkColr: Int = 12
    /** @property padStrkColrVal Padding for the stroke color value in string representations. */
    override val padStrkColrVal: Int = 7

    /** @property padStrkWght Padding for the stroke weight field in string representations. */
    override val padStrkWght: Int = 13
    /** @property padStrkWghtPrecision Precision for the stroke weight value in string representations. */
    override val padStrkWghtPrecision: Int = 2
    /** @property padStrkWghtVal Padding for the stroke weight value in string representations. */
    override val padStrkWghtVal: Int = 1 + maxStrkWeight.decimalPlaces() + padStrkWghtPrecision
}
