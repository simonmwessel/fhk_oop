package de.fhkiel.oop.model

import de.fhkiel.oop.config.Config
import de.fhkiel.oop.utils.Color
import de.fhkiel.oop.utils.RandomUtils.randomColor
import de.fhkiel.oop.utils.RandomUtils.random


/**
 * Immutable bundle of graphical attributes for a [Shape].
 *
 * @property fill   The fill (interior) colour, defaults to a random RGB-A colour.
 * @property stroke The stroke (outline) colour, defaults to another random RGB-A colour.
 * @property weight The stroke width in pixels, random between [Config.MIN_STRK_WEIGHT] and [Config.MAX_STRK_WEIGHT].
 *
 * Using a data class makes it trivial to create altered copies
 * (`style.copy(fill = newColour)`), which keeps the public API ergonomic.
 *
 * @see Color
 * @see de.fhkiel.oop.utils.RandomUtils.randomColor
 * @see Config
 *
 * @author  Simon Wessel
 * @version 1.0
 * @since   2.5
 */
data class Style(
    val fill:   Color = randomColor(),
    val stroke: Color = randomColor(),
    val weight: Float = (Config.MIN_STRK_WEIGHT..Config.MAX_STRK_WEIGHT).random()
)
