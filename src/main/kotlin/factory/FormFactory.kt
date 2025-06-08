package de.fhkiel.oop.factory

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.config.DistributionConfig
import de.fhkiel.oop.config.GenerationParams
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.Vector2D
import de.fhkiel.oop.model.InteractiveShape
import de.fhkiel.oop.model.Shape
import de.fhkiel.oop.model.ShapeType
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
 * Shapes are generated based on [GenerationParams] and specific [DistributionConfig] settings.
 *
 * #### **Bounds Handling** (via [GenerationParams.safe])
 * - `safe = true` (default): Shapes are fully contained within the [Config] canvas.
 * - `safe = false`: Legacy mode with unrestricted coordinates (shapes might be partially or fully outside canvas).
 *
 * #### **Probability Distributions** (via [GenerationParams.size], [GenerationParams.origin] or direct `sizeConfig`/`originConfig` parameters)
 * - Size parameters (radius, width, etc.) are controlled by a [DistributionConfig].
 * - Origin coordinates are controlled by a [DistributionConfig].
 *
 * #### **Normal Distribution Parameters** (within [DistributionConfig] when [DistributionConfig.distribution] is [Distribution.NORMAL])
 * - `peakFraction` or `mean`: Defines the central tendency ([DistributionConfig.peakFraction], [DistributionConfig.mean]).
 * - `sigma`: Defines the spread or standard deviation ([DistributionConfig.sigma]).
 *
 * ### Example
 * ```kotlin
 * val factory = FormFactory()
 *
 * // Define distribution configurations
 * val customSizeConfig = DistributionConfig(
 *     distribution = Distribution.NORMAL,
 *     peakFraction = 0.25f // Peak at 25% of size range
 * )
 * val customOriginConfig = DistributionConfig(distribution = Distribution.UNIFORM)
 *
 * // Setup generation parameters, including the distribution configs
 * val params = GenerationParams(
 *     count = 50,
 *     shapeType = ShapeType.CIRCLE,
 *     safe = true,
 *     size = customSizeConfig,    // Define default size config within params
 *     origin = customOriginConfig // Define default origin config within params
 * )
 *
 * // Produce shapes using the size/origin configs from GenerationParams by passing them explicitly
 * val shapes = factory.produce(
 *     generationParams = params,
 *     sizeConfig = params.size,       // Explicitly use size config from params
 *     originConfig = params.origin    // Explicitly use origin config from params
 * )
 *
 * // Alternative: Produce shapes using default size/origin configs of the 'produce' method,
 * // if GenerationParams' internal configs are not explicitly passed to produce's parameters.
 * val simpleParams = GenerationParams(count = 10, shapeType = ShapeType.SQUARE) // Uses GenerationParams' default DistributionConfig for size and origin
 * // This call will use DistributionConfig.DEFAULT_SIZE and DistributionConfig.DEFAULT_ORIGIN
 * // because simpleParams.size and simpleParams.origin are not explicitly passed to produce's parameters.
 * val shapesWithDefaults = factory.produce(simpleParams)
 * ```
 *
 * @author Simon Wessel
 * @version 2.8
 * @since 1.7
 */
class FormFactory {

    /**
     * Produces a list of random shapes based on the provided parameters.
     *
     * Shape generation behavior is primarily controlled by [generationParams].
     * The [sizeConfig] and [originConfig] parameters of this method allow providing specific distribution
     * configurations for this particular call, which are then used by the underlying generation logic.
     * If `generationParams.safe` is true, shapes are generated within canvas bounds using [produceInBounds],
     * otherwise, legacy generation is used via [produceLegacy].
     *
     * If `generationParams.selectable` is true, the generated shapes are wrapped in [InteractiveShape].
     *
     * @param generationParams Parameters for shape generation, encapsulating:
     *   - `count`: Number of shapes to create.
     *   - `shapeType`: Optional specific [ShapeType]. If null, random types are generated.
     *   - `safe`: If `true`, restricts shapes to canvas bounds.
     *   - `size`: The default [DistributionConfig] for sizes, intended to be used if not overridden by the `sizeConfig` parameter of this method.
     *   - `origin`: The default [DistributionConfig] for origins, intended to be used if not overridden by the `originConfig` parameter of this method.
     *   - `selectable`: If `true`, wraps the generated shapes in [InteractiveShape].
     *   (Note: To use `generationParams.size` or `generationParams.origin`, pass them explicitly as arguments to `sizeConfig` or `originConfig` respectively).
     * @param sizeConfig The [DistributionConfig] to use for generating shape sizes (e.g., radius, width, height).
     *   This configuration specifies the [DistributionConfig.distribution] type (e.g., [Distribution.NORMAL], [Distribution.UNIFORM]),
     *   and for normal distributions, parameters like [DistributionConfig.peakFraction] (or [DistributionConfig.mean]) and [DistributionConfig.sigma].
     *   Defaults to [DistributionConfig.DEFAULT_SIZE].
     * @param originConfig The [DistributionConfig] to use for generating shape origins (coordinates).
     *   Similar to `sizeConfig`, this specifies distribution type and its parameters.
     *   Defaults to [DistributionConfig.DEFAULT_ORIGIN].
     * @return An immutable [List] of generated [BaseShape]s.
     *
     * @see GenerationParams
     * @see DistributionConfig
     * @see ShapeType
     * @see produceInBounds
     * @see produceLegacy
     */
    fun produce(
        generationParams: GenerationParams,
        sizeConfig:       DistributionConfig = DistributionConfig.DEFAULT_SIZE,
        originConfig:     DistributionConfig = DistributionConfig.DEFAULT_ORIGIN
    ): List<BaseShape> {
        val raw: List<BaseShape> =
            if (generationParams.safe) produceInBounds(
                count        = generationParams.count,
                shapeType    = generationParams.shapeType,
                sizeConfig   = sizeConfig,
                originConfig = originConfig
            ) else produceLegacy(
                count     = generationParams.count,
                shapeType = generationParams.shapeType
            )

        if (generationParams.selectable)
            return raw.map { InteractiveShape(it) }
        return raw
    }

    /**
     * Builds a [Circle] fully contained within the canvas, using specified distribution configurations.
     *
     * The size (radius) of the circle is determined by [sizeConfig].
     * The origin (center vector) of the circle is determined by [originConfig].
     *
     * @param sizeConfig The [DistributionConfig] for generating the circle's radius.
     *   It defines the [DistributionConfig.distribution] (e.g., [Distribution.NORMAL], [Distribution.UNIFORM])
     *   and its relevant parameters (e.g., [DistributionConfig.peakFraction], [DistributionConfig.mean], [DistributionConfig.sigma]).
     *   Defaults to [DistributionConfig.DEFAULT_SIZE].
     * @param originConfig The [DistributionConfig] for generating the circle's center coordinates (x, y).
     *   It defines the distribution and its parameters similarly to `sizeConfig`.
     *   Defaults to [DistributionConfig.DEFAULT_ORIGIN].
     * @return A [Circle] instance with generated parameters.
     * @see DistributionConfig
     * @see Config.MAX_CIRCLE_RADIUS
     */
    fun circle(
        sizeConfig:   DistributionConfig = DistributionConfig.DEFAULT_SIZE,
        originConfig: DistributionConfig = DistributionConfig.DEFAULT_ORIGIN
    ): Circle {
        val r = (Config.MIN_CIRCLE_RADIUS..Config.MAX_CIRCLE_RADIUS).random(sizeConfig)
        val x = (r..Config.MAX_X - r).random(originConfig)
        val y = (r..Config.MAX_Y - r).random(originConfig)
        return Circle(Vector2D(x, y), r)
    }

    /**
     * Builds a [Rectangle] fully contained within the canvas, using specified distribution configurations.
     *
     * The width and height of the rectangle are determined by [sizeConfig].
     * The top-left origin vector of the rectangle is determined by [originConfig].
     *
     * @param sizeConfig The [DistributionConfig] for generating the rectangle's width and height.
     *   It defines the [DistributionConfig.distribution] and its relevant parameters.
     *   Defaults to [DistributionConfig.DEFAULT_SIZE].
     * @param originConfig The [DistributionConfig] for generating the rectangle's top-left coordinates (x, y).
     *   It defines the distribution and its parameters.
     *   Defaults to [DistributionConfig.DEFAULT_ORIGIN].
     * @return A [Rectangle] instance with generated parameters.
     * @see DistributionConfig
     * @see Config.MAX_RECT_WIDTH
     * @see Config.MAX_RECT_HEIGHT
     */
    fun rectangle(
        sizeConfig:   DistributionConfig = DistributionConfig.DEFAULT_SIZE,
        originConfig: DistributionConfig = DistributionConfig.DEFAULT_ORIGIN
    ): Rectangle {
        val w = (Config.MIN_RECT_WIDTH..Config.MAX_RECT_WIDTH).random(sizeConfig)
        val h = (Config.MIN_RECT_HEIGHT..Config.MAX_RECT_HEIGHT).random(sizeConfig)
        val x = (Float.MIN_VALUE..Config.MAX_X - w).random(originConfig)
        val y = (Float.MIN_VALUE..Config.MAX_Y - h).random(originConfig)
        return Rectangle(Vector2D(x, y), w, h)
    }

    /**
     * Builds a [Square] fully contained within the canvas, using specified distribution configurations.
     *
     * The side length of the square is determined by [sizeConfig].
     * The top-left origin vector of the square is determined by [originConfig].
     *
     * @param sizeConfig The [DistributionConfig] for generating the square's side length.
     *   It defines the [DistributionConfig.distribution] and its relevant parameters.
     *   Defaults to [DistributionConfig.DEFAULT_SIZE].
     * @param originConfig The [DistributionConfig] for generating the square's top-left coordinates (x, y).
     *   It defines the distribution and its parameters.
     *   Defaults to [DistributionConfig.DEFAULT_ORIGIN].
     * @return A [Square] instance with generated parameters.
     * @see DistributionConfig
     * @see Config.MAX_SQUARE_SIDE
     */
    fun square(
        sizeConfig:   DistributionConfig = DistributionConfig.DEFAULT_SIZE,
        originConfig: DistributionConfig = DistributionConfig.DEFAULT_ORIGIN
    ): Square {
        val s = (Config.MIN_SQUARE_SIDE..Config.MAX_SQUARE_SIDE).random(sizeConfig)
        val x = (Float.MIN_VALUE..Config.MAX_X - s).random(originConfig)
        val y = (Float.MIN_VALUE..Config.MAX_Y - s).random(originConfig)
        return Square(Vector2D(x, y), s)
    }

    /**
     * Internal method for safe shape generation, ensuring shapes are fully contained within canvas bounds.
     *
     * @param count Number of shapes to generate.
     * @param shapeType Optional. The specific [ShapeType] to generate. If null, random shape types are generated.
     * @param sizeConfig The [DistributionConfig] used for determining the size parameters of the shapes
     *   (e.g., radius, width/height). This includes distribution type and its parameters.
     * @param originConfig The [DistributionConfig] used for determining the origin coordinates of the shapes.
     *   This includes distribution type and its parameters.
     * @return A list of [BaseShape]s generated with bounds constraints.
     * @see ShapeType
     * @see DistributionConfig
     */
    private fun produceInBounds(
        count:        Int,
        shapeType:    ShapeType? = null,
        sizeConfig:   DistributionConfig = DistributionConfig.DEFAULT_SIZE,
        originConfig: DistributionConfig = DistributionConfig.DEFAULT_ORIGIN
    ): List<BaseShape> {
        val actualShapeProducer: () -> BaseShape = when (shapeType) {
            ShapeType.CIRCLE -> {
                { circle(sizeConfig, originConfig) }
            }
            ShapeType.RECTANGLE -> {
                { rectangle(sizeConfig, originConfig) }
            }
            ShapeType.SQUARE -> {
                { square(sizeConfig, originConfig) }
            }
            else -> { // Random shape if type is null or unknown
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
     * Internal method for legacy shape generation, which does not perform bounds checking.
     * Shapes might be generated with default constructors and may exceed canvas bounds.
     *
     * @param count Number of shapes to generate.
     * @param shapeType Optional. The specific [ShapeType] to generate. If null, random shape types are generated
     *   using their default constructors.
     * @return A list of [BaseShape]s with parameters generated by their default constructors (may exceed canvas bounds).
     * @see ShapeType
     */
    private fun produceLegacy(count: Int, shapeType: ShapeType? = null): List<BaseShape> {
        val actualShapeProducer: () -> BaseShape = when (shapeType) {
            ShapeType.CIRCLE    -> { { Circle() } }
            ShapeType.RECTANGLE -> { { Rectangle() } }
            ShapeType.SQUARE    -> { { Square() } }
            else                -> { // Random shape if type is null or unknown
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
