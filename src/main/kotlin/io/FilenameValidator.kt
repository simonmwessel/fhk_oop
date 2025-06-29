package de.fhkiel.oop.io

/**
 * Filters a user-supplied file name so that it only contains characters that are
 * legal on Windows, macOS and most Unix derivates:
 *
 *   • letters   A-Z a-z
 *   • digits    0-9
 *   • symbols   _ . -
 *
 * Everything else is silently discarded.
 */
object FilenameValidator {

    private val legalChars: Set<Char> = (('a'..'z').toSet() +
            ('A'..'Z').toSet() +
            ('0'..'9').toSet() +
            setOf('.', '_', '-'))

    /** True if the given character is regarded as portable. */
    fun isLegal(ch: Char): Boolean = ch in legalChars

    /** Removes every illegal character from the input sequence. */
    fun sanitize(raw: CharSequence): String = raw.toString().filter(::isLegal)
}
