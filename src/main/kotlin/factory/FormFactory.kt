package de.fhkiel.oop.factory

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.config.DistributionConfig
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.Point
import de.fhkiel.oop.model.Shape
import de.fhkiel.oop.shapes.Circle
import de.fhkiel.oop.shapes.Rectangle
import de.fhkiel.oop.shapes.Square
import de.fhkiel.oop.utils.RandomUtils.random
import de.fhkiel.oop.utils.RandomUtils.Distribution
import kotlin.random.Random

/**
 * Factory for creating random [Shape] instances: [Circle], [Rectangle], [Square].
 *
 * ### Configuration Options
 * #### **Bounds Handling**
 * - `safe = true` (default): Shapes are fully contained within the [Config] canvas.
 * - `safe = false`: Legacy mode with unrestricted coordinates.
 *
 * #### **Probability Distributions**
 * - `sizeDist`: Controls size parameters (radius, width, etc.).
 * - `originDist`: Controls origin coordinates.
 *
 * #### **Normal Distribution Parameters** (applies only when distribution is [Distribution.NORMAL])
 * - `sizePeakFraction`/`sizeMean`: Peak position for sizes.
 * - `sizeSigma`: Spread for sizes.
 * - `originPeakFraction`/`originMean`: Peak position for origins.
 * - `originSigma`: Spread for origins.
 *
 * ### Example
 * ```kotlin
 * val factory = FormFactory()
 * val shapes  = factory.produce(
 *     count            = 50,
 *     shapeType        = "Circle", // Optional: Generate only circles
 *     safe             = true,
 *     sizeDist         = Distribution.NORMAL,
 *     sizePeakFraction = 0.25f, // Peak at 25% of size range
 *     originDist       = Distribution.UNIFORM
 * )
 * ```
 *
 * @author Simon Wessel
 * @version 2.4
 * @since 1.7
 */
class FormFactory {

    /**
     * Produces a list of random shapes with full control over distributions.
     *
     * @param count              Number of shapes to create.
     * @param shapeType          Optional. The specific type of shape to generate (e.g., "Square", "Rectangle", "Circle").
     *                           If null or an unknown type is provided, random shapes will be generated (if safe=true)
     *                           or legacy random shapes (if safe=false).
     * @param safe               If `true`, restricts shapes to canvas bounds.
     *
     * @param sizeDist           Distribution for size parameters (radius, width, etc.).
     * @param sizePeakFraction   For [Distribution.NORMAL]: Relative peak position (0.0-1.0) in size range. Overrides [sizeMean] if set.
     * @param sizeMean           For [Distribution.NORMAL]: Absolute mean for sizes (used if [sizePeakFraction] is null).
     * @param sizeSigma          For [Distribution.NORMAL]: Standard deviation for sizes.
     *
     * @param originDist         Distribution for origin coordinates.
     * @param originPeakFraction For [Distribution.NORMAL]: Relative peak position (0.0-1.0) in origin range. Overrides [originMean] if set.
     * @param originMean         For [Distribution.NORMAL]: Absolute mean for origins (used if [originPeakFraction] is null).
     * @param originSigma        For [Distribution.NORMAL]: Standard deviation for origins.
     *
     * @return Immutable [List] of generated [Shape]s following specified distributions.
     */
    fun produce(
        count:        Int,
        shapeType:    String? = null,
        safe:         Boolean = true,
        sizeConfig:   DistributionConfig = DistributionConfig.DEFAULT_SIZE,
        originConfig: DistributionConfig = DistributionConfig.DEFAULT_ORIGIN
    ): List<BaseShape> =
        if (safe) produceInBounds(
            count        = count,
            shapeType    = shapeType,
            sizeConfig   = sizeConfig,
            originConfig = originConfig
        )
        else produceLegacy(count, shapeType)

    /**
     * Builds a [Circle] fully contained within the canvas.
     *
     * @param sizeDist           Distribution for radius generation.
     * @param sizePeakFraction   For [Distribution.NORMAL]: Relative peak position (0.0-1.0) in radius range.
     * @param sizeMean           For [Distribution.NORMAL]: Absolute mean for radius.
     * @param sizeSigma          For [Distribution.NORMAL]: Standard deviation for radius.
     *
     * @param originDist         Distribution for center coordinates.
     * @param originPeakFraction For [Distribution.NORMAL]: Relative peak position (0.0-1.0) in coordinate range.
     * @param originMean         For [Distribution.NORMAL]: Absolute mean for coordinates.
     * @param originSigma        For [Distribution.NORMAL]: Standard deviation for coordinates.
     *
     * @return [Circle] with generated parameters following specified distributions.
     */
    fun circle(
        sizeConfig:   DistributionConfig = DistributionConfig.DEFAULT_SIZE,
        originConfig: DistributionConfig = DistributionConfig.DEFAULT_ORIGIN
    ): Circle {
        val r = (Float.MIN_VALUE..Config.MAX_CIRCLE_RADIUS).random(sizeConfig)
        val x = (r..Config.MAX_X - r).random(originConfig)
        val y = (r..Config.MAX_Y - r).random(originConfig)
        return Circle(Point(x, y), r)
    }

    /**
     * Builds a [Rectangle] fully contained within the canvas.
     *
     * @param sizeDist           Distribution for width/height generation.
     * @param sizePeakFraction   For [Distribution.NORMAL]: Relative peak position (0.0-1.0) in dimension ranges.
     * @param sizeMean           For [Distribution.NORMAL]: Absolute mean for dimensions.
     * @param sizeSigma          For [Distribution.NORMAL]: Standard deviation for dimensions.
     *
     * @param originDist         Distribution for top-left coordinates.
     * @param originPeakFraction For [Distribution.NORMAL]: Relative peak position (0.0-1.0) in coordinate range.
     * @param originMean         For [Distribution.NORMAL]: Absolute mean for coordinates.
     * @param originSigma        For [Distribution.NORMAL]: Standard deviation for coordinates.
     *
     * @return [Rectangle] with generated parameters following specified distributions.
     */
    fun rectangle(
        sizeConfig:   DistributionConfig = DistributionConfig.DEFAULT_SIZE,
        originConfig: DistributionConfig = DistributionConfig.DEFAULT_ORIGIN
    ): Rectangle {
        val w = (Float.MIN_VALUE..Config.MAX_RECT_WIDTH).random(sizeConfig)
        val h = (Float.MIN_VALUE..Config.MAX_RECT_HEIGHT).random(sizeConfig)
        val x = (Float.MIN_VALUE..Config.MAX_X - w).random(originConfig)
        val y = (Float.MIN_VALUE..Config.MAX_Y - h).random(originConfig)
        return Rectangle(Point(x, y), w, h)
    }

    /**
     * Builds a [Square] fully contained within the canvas.
     *
     * @param sizeDist           Distribution for side length generation.
     * @param sizePeakFraction   For [Distribution.NORMAL]: Relative peak position (0.0-1.0) in side length range.
     * @param sizeMean           For [Distribution.NORMAL]: Absolute mean for side length.
     * @param sizeSigma          For [Distribution.NORMAL]: Standard deviation for side length.
     *
     * @param originDist         Distribution for top-left coordinates.
     * @param originPeakFraction For [Distribution.NORMAL]: Relative peak position (0.0-1.0) in coordinate range.
     * @param originMean         For [Distribution.NORMAL]: Absolute mean for coordinates.
     * @param originSigma        For [Distribution.NORMAL]: Standard deviation for coordinates.
     *
     * @return [Square] with generated parameters following specified distributions.
     */
    fun square(
        sizeConfig:   DistributionConfig = DistributionConfig.DEFAULT_SIZE,
        originConfig: DistributionConfig = DistributionConfig.DEFAULT_ORIGIN
    ): Square {
        val s = (Float.MIN_VALUE..Config.MAX_SQUARE_SIDE).random(sizeConfig)
        val x = (Float.MIN_VALUE..Config.MAX_X - s).random(originConfig)
        val y = (Float.MIN_VALUE..Config.MAX_Y - s).random(originConfig)
        return Square(Point(x, y), s)
    }

    /**
     * Internal method for safe shape generation with bounds checking.
     *
     * @param count              Number of shapes to generate.
     * @param shapeType          Optional. Specific type of shape to generate. If null or an unknown type is provided, random types are generated.
     *
     * @param sizeDist           Distribution for size parameters.
     * @param sizePeakFraction   Normal distribution peak fraction for sizes.
     * @param sizeMean           Normal distribution mean for sizes.
     * @param sizeSigma          Normal distribution sigma for sizes.
     *
     * @param originDist         Distribution for origin parameters.
     * @param originPeakFraction Normal distribution peak fraction for origins.
     * @param originMean         Normal distribution mean for origins.
     * @param originSigma        Normal distribution sigma for origins.
     *
     * @return List of shapes generated with bounds constraints.
     */
    private fun produceInBounds(
        count:        Int,
        shapeType:    String? = null,
        sizeConfig:   DistributionConfig = DistributionConfig.DEFAULT_SIZE,
        originConfig: DistributionConfig = DistributionConfig.DEFAULT_ORIGIN
    ): List<BaseShape> {
        val actualShapeProducer: () -> BaseShape = when (shapeType?.lowercase()) {
            "circle" -> {
                { circle(sizeConfig, originConfig) }
            }
            "rectangle" -> {
                { rectangle(sizeConfig, originConfig) }
            }
            "square" -> {
                { square(sizeConfig, originConfig) }
            }
            else -> {
                {
                    when (Random.nextDouble()) {
                        in 0.0..0.33  -> circle(sizeConfig, originConfig)
                        in 0.33..0.66 -> rectangle(sizeConfig, originConfig)
                        else          -> square(sizeConfig, originConfig)
                    }
                }
            }
        }
        return List(count) { actualShapeProducer() }
    }

    /**
     * Internal method for legacy shape generation without bounds checking.
     *
     * @param count Number of shapes to generate.
     * @param shapeType Optional. Specific type of shape to generate. If null or an unknown type is provided, random types are generated.
     *
     * @return List of shapes with random parameters (may exceed canvas bounds).
     */
    private fun produceLegacy(count: Int, shapeType: String? = null): List<BaseShape> {
        val actualShapeProducer: () -> BaseShape = when (shapeType?.lowercase()) {
            "circle"    -> { { Circle() } }
            "rectangle" -> { { Rectangle() } }
            "square"    -> { { Square() } }
            else        -> { // Random shape if type is null or unknown
                {
                    when (Random.nextDouble()) {
                        in 0.0..0.33  -> Circle()
                        in 0.33..0.66 -> Rectangle()
                        else          -> Square()
                    }
                }
            }
        }
        return List(count) { actualShapeProducer() }
    }
}
