package de.fhkiel.oop.io

import de.fhkiel.oop.config.AppConfig
import de.fhkiel.oop.config.PlainConfig
import de.fhkiel.oop.model.BaseShape
import de.fhkiel.oop.model.Vector2D
import de.fhkiel.oop.shapes.Circle
import de.fhkiel.oop.shapes.Rectangle
import de.fhkiel.oop.shapes.Square
import de.fhkiel.oop.model.Style
import de.fhkiel.oop.model.InteractiveShape
import de.fhkiel.oop.utils.Color
import de.fhkiel.oop.utils.StringExtensions.toLocalFloat

/**
 * Utility for converting [BaseShape] instances to a compact String format and
 * back again.
 *
 * The default format is compatible with [PlainConfig] so the `toString()`
 * representation of a shape can be used for persistence. Each line begins
 * with an interaction flag (`"i"` for [InteractiveShape], `"n"` otherwise),
 * followed by the shape type and its numeric attributes and style values,
 * all separated by pipes.
 */
object ShapeSerializer {

    /**
     * Serialises a [BaseShape] to a pipe separated string.
     */
    fun serialize(shape: BaseShape, config: AppConfig = PlainConfig): String =
        (if (shape is InteractiveShape) "i" else "n") +
        config.separator +
        (if (shape is InteractiveShape) shape.inner.toConfiguredString(config, false)
        else                            shape.toConfiguredString(config, false))

    /**
     * Recreates a [BaseShape] from a previously serialised string.
     *
     * @throws IllegalArgumentException if the string cannot be parsed.
     */
    fun deserialize(
        line: String,
        parseConfig: AppConfig = PlainConfig,
        shapeConfig: AppConfig = parseConfig,
    ): BaseShape {
        val parts = line.split(parseConfig.separator)
        require(parts.size >= 2) { "Malformed line: '$line'" }
        val interactive = parts[0].lowercase() == "i"

        return when (parts.getOrNull(1) ?: "") {
            Circle::class.simpleName -> {
                require(parts.size == 10) { "Invalid circle line" }
                val shape = Circle(
                    shapeConfig,
                    Vector2D(shapeConfig, parts[2].toLocalFloat(parseConfig.locale), parts[3].toLocalFloat(parseConfig.locale)),
                    parts[4].toLocalFloat(parseConfig.locale),
                    Style(
                        shapeConfig,
                        Color.fromHex(parts[7]),
                        Color.fromHex(parts[8]),
                        parts[9].toLocalFloat(parseConfig.locale)
                    )
                )
                if (interactive) InteractiveShape(shape) else shape
            }

            Square::class.simpleName -> {
                require(parts.size == 10) { "Invalid square line" }
                val shape = Square(
                    shapeConfig,
                    Vector2D(shapeConfig, parts[2].toLocalFloat(parseConfig.locale), parts[3].toLocalFloat(parseConfig.locale)),
                    parts[4].toLocalFloat(parseConfig.locale),
                    Style(
                        shapeConfig,
                        Color.fromHex(parts[7]),
                        Color.fromHex(parts[8]),
                        parts[9].toLocalFloat(parseConfig.locale)
                    )
                )
                if (interactive) InteractiveShape(shape) else shape
            }

            Rectangle::class.simpleName -> {
                require(parts.size == 10) { "Invalid rectangle line" }
                val shape = Rectangle(
                    shapeConfig,
                    Vector2D(shapeConfig, parts[2].toLocalFloat(parseConfig.locale), parts[3].toLocalFloat(parseConfig.locale)),
                    parts[4].toLocalFloat(parseConfig.locale),
                    parts[5].toLocalFloat(parseConfig.locale),
                    Style(
                        shapeConfig,
                        Color.fromHex(parts[7]),
                        Color.fromHex(parts[8]),
                        parts[9].toLocalFloat(parseConfig.locale)
                    )
                )
                if (interactive) InteractiveShape(shape) else shape
            }

            else -> throw IllegalArgumentException("Unknown shape type in line: $line")
        }
    }
}
