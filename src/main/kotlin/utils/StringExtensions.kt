package de.fhkiel.oop.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object StringExtensions {
    @JvmStatic
    fun String.toLocalFloat(locale: Locale): Float {
        try {
            val format = DecimalFormat().apply {
                decimalFormatSymbols = DecimalFormatSymbols(locale)
            }
            return format.parse(this)?.toFloat()
                ?: throw NumberFormatException("Could not parse: $this")
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to parse '$this' with locale $locale", e)
        }
    }
}
