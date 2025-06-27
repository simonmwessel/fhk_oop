package de.fhkiel.oop.config

/**
 * Minimal [AppConfig] implementation used for compact string serialization.
 *
 * This configuration mirrors [DefaultConfig] for behavioural settings but
 * removes all padding and surrounding text so that shape `toString()`
 * produces a simple pipe separated representation.
 */
object PlainConfig : DelegatingAppConfig(DefaultConfig) {
    override val prefix: String = ""
    override val suffix: String = ""
    override val separator: String = "|"
    override val separatorKeyValue: String = "|"

    override val padType: Int = 0
    override val padTypeVal: Int = 0
    override val padCord: Int = 0
    override val padCordVal: Int = 0
    override val padAttr1: Int = 0
    override val padAttr1Val: Int = 0
    override val padAttr2: Int = 0
    override val padAttr2Val: Int = 0
    override val padArea: Int = 0
    override val padAreaVal: Int = 0
    override val padFillColr: Int = 0
    override val padFillColrVal: Int = 0
    override val padStrkColr: Int = 0
    override val padStrkColrVal: Int = 0
    override val padStrkWght: Int = 0
    override val padStrkWghtVal: Int = 0
}
