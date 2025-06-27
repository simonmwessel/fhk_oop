package de.fhkiel.oop.config

/**
 * Convenience base class that delegates all [AppConfig] properties to
 * another instance.
 *
 * Only the mutable [debug] property is explicitly forwarded so subclasses
 * may override individual read-only values.
 *
 * @param base underlying configuration to delegate to
 */
open class DelegatingAppConfig(private val base: AppConfig) : AppConfig by base {
    override var debug: Boolean
        get() = base.debug
        set(value) { base.debug = value }
}
