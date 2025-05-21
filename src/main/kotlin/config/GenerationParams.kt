package de.fhkiel.oop.config

import de.fhkiel.oop.model.ShapeType

data class GenerationParams(
    val count:     Int,
    val shapeType: ShapeType?         = null,
    val safe:      Boolean            = true,
    val size:      DistributionConfig = DistributionConfig.DEFAULT_SIZE,
    val origin:    DistributionConfig = DistributionConfig.DEFAULT_ORIGIN
) {
    companion object {
        val RANDOM_20 = GenerationParams(count = 20)

        val CIRCLE    = GenerationParams(count = 1, shapeType = ShapeType.CIRCLE)
        val RECTANGLE = GenerationParams(count = 1, shapeType = ShapeType.RECTANGLE)
        val SQUARE    = GenerationParams(count = 1, shapeType = ShapeType.SQUARE)
    }
}
