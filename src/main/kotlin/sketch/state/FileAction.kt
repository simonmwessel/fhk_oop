package de.fhkiel.oop.sketch.state

/**
 * Possible file actions triggered by the user.
 */
enum class FileAction {
    /** No pending file operation. */
    NONE,

    /** Save the current drawing to a file. */
    SAVE,

    /** Load a drawing from a file. */
    LOAD
}
