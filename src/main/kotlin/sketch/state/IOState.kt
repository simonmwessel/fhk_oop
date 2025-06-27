package de.fhkiel.oop.sketch.state

/**
 * Holds the state of file operations requiring filename input.
 *
 * @property pendingAction the action to perform once a filename is provided
 * @property waitingForInput whether the application is currently waiting for a filename
 */
class IOState {
    /** Action that should be performed once filename input is complete. */
    var pendingAction: FileAction = FileAction.NONE

    /** Whether the program is currently waiting for user input in the console. */
    var waitingForInput: Boolean = false
}
