package de.fhkiel.oop.utils

/**
 * Immutable value class representing a 32-bit ARGB color with proper channel accessors.
 *
 * The color is stored as a single `Int` in the format `0xAARRGGBB` and provides:
 * - Type-safe color operations
 * - Channel accessors with normalized values
 * - Hex string conversion
 *
 * @property rgba Packed 32-bit ARGB value in `0xAARRGGBB` format.
 *
 * @constructor Creates from packed 32-bit integer.
 *
 * @author  Simon Wessel
 * @version 1.0
 * @since   2.3
 */
@JvmInline
value class Color(val rgba: Int) {

    /**
     * Creates a Color from individual 8-bit channels.
     *
     * @param r Red channel (0–255) - values outside range are clamped
     * @param g Green channel (0–255)
     * @param b Blue channel (0–255)
     * @param a Alpha channel (0–255, default = 255 = fully opaque)
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

    /**
     * 24-bit RGB representation without alpha (0xRRGGBB).
     */
    val rgb: Int
        get() = rgba and 0x00FFFFFF

    /**
     * Returns the color as a hex string in `#RRGGBBAA` format.
     *
     * @return Non-premultiplied ARGB representation like "#FF00FF80"
     */
    override fun toString(): String =
        "#%02X%02X%02X%02X".format(red.toInt(), green.toInt(), blue.toInt(), alpha.toInt())

    companion object {
        /**
         * Parses a hexadecimal color string to a [Color] instance.
         *
         * Supported formats:
         * - `#RRGGBB` (alpha defaults to 0xFF)
         * - `#RRGGBBAA`
         *
         * @param hex Color string starting with '#' followed by 6 or 8 hex digits
         *
         * @return Non-null [Color] instance
         *
         * @throws IllegalArgumentException for invalid formats
         */
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
