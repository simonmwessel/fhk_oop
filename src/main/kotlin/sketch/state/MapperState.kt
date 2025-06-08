package de.fhkiel.oop.sketch.state

import de.fhkiel.oop.config.AppConfig
import de.fhkiel.oop.mapper.CoordinateMapper
import de.fhkiel.oop.mapper.RelativeScaleMapper
import de.fhkiel.oop.mapper.UniformScaleMapper
import processing.core.PApplet

/**
 * Holds the coordinate mapper used for resizing and positioning shapes.
 *
 * This state allows switching between different mapping strategies,
 * such as relative scaling or uniform scaling.
 *
 * The default mapper is a [RelativeScaleMapper] with the given base dimensions.
 *
 * @param sketch The PApplet instance used for rendering.
 * @param baseWidth The base width for the coordinate mapper.
 * @param baseHeight The base height for the coordinate mapper.
 * @param baseMin The minimum coordinate value for the mapper.
 *
 * @author Simon Wessel
 */
class MapperState(
    config: AppConfig,
    private val sketch: PApplet,
    baseWidth: Float,
    baseHeight: Float,
    baseMin: Float
) {
    private var _mapper: CoordinateMapper =
        RelativeScaleMapper(config, sketch, baseWidth, baseHeight, baseMin)

    /**
     * Returns the current coordinate mapper.
     *
     * @return active [CoordinateMapper] strategy.
     * @see RelativeScaleMapper
     * @see UniformScaleMapper
     */
    var mapper: CoordinateMapper
        get() = _mapper
        /**
         * Sets the coordinate mapper.
         *
         * @throws IllegalArgumentException if value is of any other Type
         * than [RelativeScaleMapper] or [UniformScaleMapper].
         */
        set(value) {
            require(value is RelativeScaleMapper || value is UniformScaleMapper) {
                "Mapper must be RelativeScaleMapper or UniformScaleMapper"
            }
            _mapper = value
        }
}
