package de.fhkiel.oop.model

import de.fhkiel.oop.config.DistributionConfig
import de.fhkiel.oop.factory.FormFactory

enum class ShapeType {
    CIRCLE {
        override fun produce(
            factory:      FormFactory,
            sizeConfig:   DistributionConfig,
            originConfig: DistributionConfig
        ): BaseShape =
            factory.circle(sizeConfig, originConfig)
    },

    RECTANGLE {
        override fun produce(
            factory:      FormFactory,
            sizeConfig:   DistributionConfig,
            originConfig: DistributionConfig
        ): BaseShape =
            factory.rectangle(sizeConfig, originConfig)
    },

    SQUARE {
        override fun produce(
            factory:      FormFactory,
            sizeConfig:   DistributionConfig,
            originConfig: DistributionConfig
        ): BaseShape =
            factory.square(sizeConfig, originConfig)
    };

    abstract fun produce(
        factory:      FormFactory,
        sizeConfig:   DistributionConfig,
        originConfig: DistributionConfig
    ): BaseShape
}
