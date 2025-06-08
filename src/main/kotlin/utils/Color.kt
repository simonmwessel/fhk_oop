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
    constructor(r: Int, g: Int, b: Int, a: Int = 0xff) : this(
        ((r and 0xff) shl 24) or
        ((g and 0xff) shl 16) or
        ((b and 0xff) shl  8) or
        (a and 0xff)
    )
    /** Red channel (0–255). */   val red:   Float get() = ((rgba ushr 24) and 0xff).toFloat()
    /** Green channel (0–255). */ val green: Float get() = ((rgba ushr 16) and 0xff).toFloat()
    /** Blue channel (0–255). */  val blue:  Float get() = ((rgba ushr  8) and 0xff).toFloat()
    /** Alpha channel (0–255).*/  val alpha: Float get() = ( rgba          and 0xff).toFloat()

    /**
     * Returns a new [Color] instance, optionally overriding channels.
     */
    private fun copy(
        red:   Float = this.red,
        green: Float = this.green,
        blue:  Float = this.blue,
        alpha: Float = this.alpha
    ): Color = Color(
        red.coerceIn(0f, 255f).toInt(),
        green.coerceIn(0f, 255f).toInt(),
        blue.coerceIn(0f, 255f).toInt(),
        alpha.coerceIn(0f, 255f).toInt()
    )

    /**
     * Returns a new [Color] with the red component adjusted by [delta], clamped 0..255.
     */
    fun adjustRed(delta: Int): Color =
        copy(red = (red + delta).coerceIn(0f, 255f))

    /**
     * Returns a new [Color] with the green component adjusted by [delta], clamped 0..255.
     */
    fun adjustGreen(delta: Int): Color =
        copy(green = (green + delta).coerceIn(0f, 255f))

    /**
     * Returns a new [Color] with the blue component adjusted by [delta], clamped 0..255.
     */
    fun adjustBlue(delta: Int): Color =
        copy(blue = (blue + delta).coerceIn(0f, 255f))

    /** Increments the red channel by 5 units. */
    fun incrementRed(): Color = adjustRed(5)
    /** Decrements the red channel by 5 units. */
    fun decrementRed(): Color = adjustRed(-5)

    /** Increments the green channel by 5 units. */
    fun incrementGreen(): Color = adjustGreen(5)
    /** Decrements the green channel by 5 units. */
    fun decrementGreen(): Color = adjustGreen(-5)

    /** Increments the blue channel by 5 units. */
    fun incrementBlue(): Color = adjustBlue(5)
    /** Decrements the blue channel by 5 units. */
    fun decrementBlue(): Color = adjustBlue(-5)

    /**
     * 24-bit RGB representation without alpha (0xrrggbb).
     */
    val rgb: Int
        get() = rgba and 0x00ffffff

    /**
     * Returns the color as a hex string in `#rrggbbaa` format.
     *
     * @return Non-premultiplied ARGB representation like "#ff00ff80"
     */
    override fun toString(): String =
        "#%02X%02X%02X%02X".format(red.toInt(), green.toInt(), blue.toInt(), alpha.toInt())

    companion object {
        /**
         * Parses a hexadecimal color string to a [Color] instance.
         *
         * Supported formats:
         * - `#rrggbb` (alpha defaults to 0xff)
         * - `#rrggbbaa`
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
                6    -> Color(((v and 0xffffff) shl 8 or 0xff).toInt())
                8    -> Color(v.toInt())
                else -> error("Invalid hex color: $hex")
            }
        }
    }
}
