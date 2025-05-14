package de.fhkiel.oop.shapes

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.model.Point
import de.fhkiel.oop.model.Shape
import de.fhkiel.oop.utils.FloatExtensions.formatAreaValue
import de.fhkiel.oop.utils.FloatExtensions.formatAttribute1Value
import de.fhkiel.oop.utils.FloatExtensions.formatCoordinateValue
import de.fhkiel.oop.utils.FloatExtensions.formatStrokeWeightValue
import de.fhkiel.oop.utils.RandomUtils.random
import processing.core.PApplet
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * A circle defined by a center point and a radius.
 *
 * @property origin The center of the circle.
 * @property radius Radius length in units ∈ (0f..[Config.MAX_CIRCLE_RADIUS]).
 *
 * @constructor Creates a [Circle] with given parameters.
 * Missing values default to random via [ClosedFloatingPointRange.random].
 *
 * @param originParam center point or random if omitted
 * @param radiusParam radius or random ∈ (0f..[Config.MAX_CIRCLE_RADIUS]) if omitted
 *
 * @author  Simon Wessel
 * @version 2.3
 * @since   1.0
 */
class Circle(
    originParam: Point = Point(),
    radiusParam: Float = (0f..Config.MAX_CIRCLE_RADIUS).random()
)  : Shape(originParam) {

    /** Backing field for radius */
    private var _radius: Float = radiusParam

    /**
     * The radius of the circle.
     *
     * @return the radius.
     */
    var radius: Float
        /** Returns the radius of the circle. */
        get() = _radius
        /** Sets the radius of the circle. */
        set(v) {
            _radius = v
        }

    companion object {
        /**
         * Static method to create a Circle from a given area.
         *
         * @param center The center of the circle.
         * @param area   The area of the circle.
         *
         * @return A new Circle object with the specified area.
         */
        fun fromArea(center: Point, area: Float): Circle =
            Circle(center, sqrt((area / Math.PI)).toFloat())
    }

    /**
     * Calculates the area of the circle.
     *
     * @return The area of the circle.
     */
    override fun getArea(): Float = (Math.PI * radius.pow(2)).toFloat()

    /**
     * Renders the circle on the Processing canvas with configured visual attributes.
     *
     * 1. Saves current drawing style with [PApplet.pushStyle]
     * 2. Applies:
     *    - Fill color from [fillColor]
     *    - Stroke color from [strokeColor]
     *    - Stroke weight from [strokeWeight]
     * 3. Draws circle using Processing's [PApplet.circle] with:
     *    - Center at [origin.x], [origin.y]
     *    - Diameter = `2 * radius` (Processing expects diameter)
     * 4. Restores original style with [PApplet.popStyle]
     *
     * @param g Processing graphics context to draw on
     *
     * @see PApplet.pushStyle
     * @see PApplet.popStyle
     * @see PApplet.circle
     */

    /**
     * Draws the circle with uniform scaling - maintains perfect roundness
     * while centering the entire composition.
     *
     * @param g Processing graphics context
     *
     * @see Shape.drawUniform
     */
    override fun drawUniform(g: PApplet) {
        g.pushStyle()
        g.fill(fillColor.red, fillColor.green, fillColor.blue, fillColor.alpha)
        g.stroke(strokeColor.red, strokeColor.green, strokeColor.blue, strokeColor.alpha)
        g.strokeWeight(strokeWeight)
        g.ellipse(origin.x, origin.y, radius * 2, radius * 2)
        g.popStyle()
    }

    /**
     * Draws the circle with relative positioning - scales position coordinates
     * while maintaining perfect roundness through [uniformScale].
     *
     * @param g            Processing graphics context
     * @param scaleX       Horizontal scaling factor (windowWidth / baseWidth)
     * @param scaleY       Vertical scaling factor (windowHeight / baseHeight)
     * @param uniformScale Unified scaling factor for circles/squares (min(windowScaleX, windowScaleY))
     *
     * @see Shape.drawRelative
     */
    override fun drawRelative(
        g: PApplet,
        scaleX: Float,
        scaleY: Float,
        uniformScale: Float
    ) {
        val cx = origin.x * scaleX
        val cy = origin.y * scaleY
        val r  = radius     * uniformScale

        g.pushStyle()
        g.fill(fillColor.red, fillColor.green, fillColor.blue, fillColor.alpha)
        g.stroke(strokeColor.red, strokeColor.green, strokeColor.blue, strokeColor.alpha)
        g.strokeWeight(strokeWeight)
        g.ellipse(cx, cy, r * 2, r * 2)
        g.popStyle()
    }

    /**
     * Returns a string representation of the Circle
     *
     * @return A string representation of the Circle.
     */
    override fun toString(): String =
        buildString(
            listOf(
                Triple("Type",   this::class.simpleName!!,         Config.PAD_TYPE   to Config.PAD_TYPE_VAL),
                Triple("X",      origin.x.formatCoordinateValue(), Config.PAD_CORD   to Config.PAD_CORD_VAL),
                Triple("Y",      origin.y.formatCoordinateValue(), Config.PAD_CORD   to Config.PAD_CORD_VAL),
                Triple("Radius", radius.formatAttribute1Value(),   Config.PAD_ATTR_1 to Config.PAD_ATTR_1_VAL),
                Triple("",       "",                               (Config.PAD_ATTR_2 + Config.SEPARATOR_KEY_VALUE.length + Config.PAD_ATTR_2_VAL) to 0),
                Triple("Area",   getArea().formatAreaValue(),      Config.PAD_AREA   to Config.PAD_AREA_VAL),

                Triple("Fill Color",    fillColor.toString(),
                    Config.PAD_FILL_COLR to Config.PAD_FILL_COLR_VAL),
                Triple("Stroke Color",  strokeColor.toString(),
                    Config.PAD_STRK_COLR to Config.PAD_STRK_COLR_VAL),
                Triple("Stroke Weight", strokeWeight.formatStrokeWeightValue(),
                    Config.PAD_STRK_WGHT to Config.PAD_STRK_WGHT_VAL)
            ))
}
