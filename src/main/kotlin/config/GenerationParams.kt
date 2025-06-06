package de.fhkiel.oop.config

import de.fhkiel.oop.model.ShapeType

/**
 * Parameters for controlling the generation of shapes in [de.fhkiel.oop.factory.FormFactory].
 *
 * This data class encapsulates all settings related to how shapes are produced,
 * including the number of shapes, their type, whether they should be generated
 * safely within canvas bounds, and the [DistributionConfig] for their size and origin.
 *
 * @property count The number of shapes to generate. Must be greater than 0. Default is 1.
 * @property shapeType The specific [ShapeType] to generate (e.g., [ShapeType.CIRCLE]).
 *   If null, random shape types will be generated.
 * @property safe If `true` (default), shapes are generated fully contained within the canvas bounds
 *   defined in [Config]. If `false`, legacy mode with potentially unrestricted
 *   coordinates is used.
 * @property selectable If `true`, the generated shapes will be wrapped in the [de.fhkiel.oop.model.ManipulatableShape] decorator,
 *   allowing for selection and manipulation.
 * @property size The [DistributionConfig] used for determining the size parameters of the shapes
 *   (e.g., radius for [de.fhkiel.oop.shapes.Circle], side for [de.fhkiel.oop.shapes.Square], or width/height for [de.fhkiel.oop.shapes.Rectangle]).
 *   Defaults to [DistributionConfig.DEFAULT_SIZE].
 * @property origin The [DistributionConfig] used for determining the origin coordinates of the shapes.
 *   Defaults to [DistributionConfig.DEFAULT_ORIGIN].
 *
 * @author  Simon Wessel
 * @version 1.1
 * @since   2.7
 *
 * @see de.fhkiel.oop.factory.FormFactory
 * @see DistributionConfig
 * @see ShapeType
 * @see Config
 */
data class GenerationParams(
    val count:      Int            = 1,
    val shapeType:  ShapeType?     = null,
    val safe:       Boolean        = true,
    val selectable: Boolean        = true,
    val size:   DistributionConfig = DistributionConfig.DEFAULT_SIZE,
    val origin: DistributionConfig = DistributionConfig.DEFAULT_ORIGIN
) {
    companion object {
        /** Default parameters for generating 20 random shapes. */
        val RANDOM_20 = GenerationParams(count = 20)

        /** Default parameters for generating a single [ShapeType.CIRCLE]. */
        val CIRCLE    = GenerationParams(shapeType = ShapeType.CIRCLE)
        /** Default parameters for generating a single [ShapeType.RECTANGLE]. */
        val RECTANGLE = GenerationParams(shapeType = ShapeType.RECTANGLE)
        /** Default parameters for generating a single [ShapeType.SQUARE]. */
        val SQUARE    = GenerationParams(shapeType = ShapeType.SQUARE)
    }
}
