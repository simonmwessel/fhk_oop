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
    fun serialize(shape: BaseShape, config: AppConfig = PlainConfig): String {
        val isInteractive = shape is InteractiveShape
        val base = if (isInteractive) shape.inner else shape
        val tokens = when (base) {
            is Circle -> listOf(    // TODO: Rebuild toString to accept config and use base toString
                if (isInteractive) "i" else "n",
                "circle",
                base.origin.x.toString(),
                base.origin.y.toString(),
                base.radius.toString(),
                base.style.fill.toString(),
                base.style.stroke.toString(),
                base.style.weight.toString()
            )

            is Square -> listOf(    // TODO: Rebuild toString to accept config and use base toString
                if (isInteractive) "i" else "n",
                "square",
                base.origin.x.toString(),
                base.origin.y.toString(),
                base.width.toString(),
                base.style.fill.toString(),
                base.style.stroke.toString(),
                base.style.weight.toString()
            )

            is Rectangle -> listOf( // TODO: Rebuild toString to accept config and use base toString
                if (isInteractive) "i" else "n",
                "rectangle",
                base.origin.x.toString(),
                base.origin.y.toString(),
                base.width.toString(),
                base.height.toString(),
                base.style.fill.toString(),
                base.style.stroke.toString(),
                base.style.weight.toString()
            )

            else -> throw IllegalArgumentException("Unsupported shape type: ${base.javaClass.simpleName}")
        }
        return tokens.joinToString("|")
    }

    /**
     * Recreates a [BaseShape] from a previously serialised string.
     *
     * @throws IllegalArgumentException if the string cannot be parsed.
     */
    fun deserialize(line: String, config: AppConfig = PlainConfig): BaseShape {
        // Split on a literal pipe. Using Char version avoids regex overhead.
        val parts = line.split('|')
        require(parts.size >= 2) { "Malformed line: '$line'" }
        val interactive = parts[0].lowercase() == "i"
        return when (parts.getOrNull(1)?.lowercase()) {
            "circle" -> {
                require(parts.size == 8) { "Invalid circle line" }      // TODO: Check via Reflection?
                val shape = Circle(
                    config,
                    Vector2D(config, parts[2].toFloat(), parts[3].toFloat()),
                    parts[4].toFloat(),
                    Style(
                        config,
                        Color.fromHex(parts[5]),
                        Color.fromHex(parts[6]),
                        parts[7].toFloat()
                    )
                )
                if (interactive) InteractiveShape(shape) else shape
            }

            "square" -> {
                require(parts.size == 8) { "Invalid square line" }      // TODO: Check via Reflection?
                val shape = Square(
                    config,
                    Vector2D(config, parts[2].toFloat(), parts[3].toFloat()),
                    parts[4].toFloat(),
                    Style(
                        config,
                        Color.fromHex(parts[5]),
                        Color.fromHex(parts[6]),
                        parts[7].toFloat()
                    )
                )
                if (interactive) InteractiveShape(shape) else shape
            }

            "rectangle" -> {
                require(parts.size == 9) { "Invalid rectangle line" }   // TODO: Check via Reflection?
                val shape = Rectangle(
                    config,
                    Vector2D(config, parts[2].toFloat(), parts[3].toFloat()),
                    parts[4].toFloat(),
                    parts[5].toFloat(),
                    Style(
                        config,
                        Color.fromHex(parts[6]),
                        Color.fromHex(parts[7]),
                        parts[8].toFloat()
                    )
                )
                if (interactive) InteractiveShape(shape) else shape
            }

            else -> throw IllegalArgumentException("Unknown shape type in line: $line")
        }
    }
}
