package de.fhkiel.oop.config

import de.fhkiel.oop.utils.Color
import java.util.Locale

/**
 * Interface for global application configuration.
 *
 * Defines canvas size limits, maximum shape dimensions, stroke weight bounds,
 * locale for numeric formatting, and padding/precision constants for
 * string output. This interface allows for different configuration implementations
 * to be injected into various parts of the application.
 */
interface AppConfig {
    /** @property debug Enables or disables debug mode for additional logging and checks. */
    var debug: Boolean
    /** @property maxX Maximum X value for random coordinate generation. */
    val maxX: Float
    /** @property maxY Maximum Y value for random coordinate generation. */
    val maxY: Float
    /** @property minCircleRadius Minimum circle radius value for random generation. */
    val minCircleRadius: Float
    /** @property maxCircleRadius Maximum circle radius value for random generation. */
    val maxCircleRadius: Float
    /** @property minRectWidth Minimum rectangle width value for random generation. */
    val minRectWidth: Float
    /** @property maxRectWidth Maximum rectangle width value for random generation. */
    val maxRectWidth: Float
    /** @property minRectHeight Minimum rectangle height value for random generation. */
    val minRectHeight: Float
    /** @property maxRectHeight Maximum rectangle height value for random generation. */
    val maxRectHeight: Float
    /** @property minSquareSide Minimum square side length value for random generation. */
    val minSquareSide: Float
    /** @property maxSquareSide Maximum square side length value for random generation. */
    val maxSquareSide: Float
    /** @property minStrkWeight Minimum stroke weight value for random generation. */
    val minStrkWeight: Float
    /** @property maxStrkWeight Maximum stroke weight value for random generation. */
    val maxStrkWeight: Float
    /** @property sketchBackgroundColor Background color for the sketch canvas. */
    val sketchBackgroundColor: Color
    /** @property locale Locale used for formatting numbers. */
    val locale: Locale
    /** @property separator Separator string for different attributes in string representations. */
    val separator: String
    /** @property separatorKeyValue Separator string for key-value pairs in string representations. */
    val separatorKeyValue: String
    /** @property prefix Prefix string for overall string output of shapes. */
    val prefix: String
    /** @property suffix Suffix string for overall string output of shapes. */
    val suffix: String
    /** @property padType Padding for the shape type field in string representations. */
    val padType: Int
    /** @property padTypeVal Padding for the shape type value in string representations. */
    val padTypeVal: Int
    /** @property padCord Padding for the shape coordinates field in string representations. */
    val padCord: Int
    /** @property padCordPrecision Precision for shape coordinate decimal values in string representations. */
    val padCordPrecision: Int
    /** @property padCordVal Padding for the shape coordinate decimal values in string representations. */
    val padCordVal: Int
    /** @property padAttr1 Padding for the first shape attribute field in string representations. */
    val padAttr1: Int
    /** @property padAttr1Precision Precision for the first shape attribute value in string representations. */
    val padAttr1Precision: Int
    /** @property padAttr1Val Padding for the first shape attribute value in string representations. */
    val padAttr1Val: Int
    /** @property padAttr2 Padding for the second shape attribute field in string representations. */
    val padAttr2: Int
    /** @property padAttr2Precision Precision for the second shape attribute value in string representations. */
    val padAttr2Precision: Int
    /** @property padAttr2Val Padding for the second shape attribute value in string representations. */
    val padAttr2Val: Int
    /** @property padArea Padding for the shape area field in string representations. */
    val padArea: Int
    /** @property padAreaPrecision Precision for the shape area value in string representations. */
    val padAreaPrecision: Int
    /** @property padAreaVal Padding for the shape area value in string representations. */
    val padAreaVal: Int
    /** @property padFillColr Padding for the fill color field in string representations. */
    val padFillColr: Int
    /** @property padFillColrVal Padding for the fill color value in string representations. */
    val padFillColrVal: Int
    /** @property padStrkColr Padding for the stroke color field in string representations. */
    val padStrkColr: Int
    /** @property padStrkColrVal Padding for the stroke color value in string representations. */
    val padStrkColrVal: Int
    /** @property padStrkWght Padding for the stroke weight field in string representations. */
    val padStrkWght: Int
    /** @property padStrkWghtPrecision Precision for the stroke weight value in string representations. */
    val padStrkWghtPrecision: Int
    /** @property padStrkWghtVal Padding for the stroke weight value in string representations. */
    val padStrkWghtVal: Int
}
