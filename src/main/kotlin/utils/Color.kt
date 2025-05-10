package de.fhkiel.oop.utils

/**
 * Wraps a 32-bit ARGB Int as a proper Color type.
 *
 * @property rgba Packed 0xAARRGGBB value.
 *
 * @constructor Constructs a Color from an ARGB Int.
 *
 * @author  Simon Wessel
 * @version 1.0
 * @since   2.3
 */
@JvmInline
value class Color(val rgba: Int) {

    /**
     * Constructs a Color from individual channels.
     *
     * @param r red   (0–255)
     * @param g green (0–255)
     * @param b blue  (0–255)
     * @param a alpha (0–255, default 255 = opaque)
     */
    constructor(r: Int, g: Int, b: Int, a: Int = 0xFF) : this(
        ((r and 0xFF) shl 24) or
        ((g and 0xFF) shl 16) or
        ((b and 0xFF) shl  8) or
        (a and 0xFF)
    )
    /** Red channel (0–255). */   val red:   Float get() = ((rgba ushr 24) and 0xFF).toFloat()
    /** Green channel (0–255). */ val green: Float get() = ((rgba ushr 16) and 0xFF).toFloat()
    /** Blue channel (0–255). */  val blue:  Float get() = ((rgba ushr  8) and 0xFF).toFloat()
    /** Alpha channel (0–255).*/  val alpha: Float get() = ( rgba          and 0xFF).toFloat()

    /** 24-bit RGB value (0xRRGGBB). */
    val rgb: Int
        get() = rgba and 0x00FFFFFF

    /** 24-bit RGBA value as a hex string in the format #RRGGBBAA. */
    override fun toString(): String =
        "#%02X%02X%02X%02X".format(red.toInt(), green.toInt(), blue.toInt(), alpha.toInt())

    companion object {
        /** Parses "#RRGGBB" or "#RRGGBBAA". */
        fun fromHex(hex: String): Color {
            val clean = hex.removePrefix("#")
            val v = clean.toLong(16)
            return when (clean.length) {
                6    -> Color(((v and 0xFFFFFF) shl 8 or 0xFF).toInt())
                8    -> Color(v.toInt())
                else -> error("Invalid hex color: $hex")
            }
        }
    }
}
