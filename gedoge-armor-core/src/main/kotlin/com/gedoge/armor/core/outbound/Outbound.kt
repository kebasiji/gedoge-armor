package com.gedoge.armor.core.outbound

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Outbound(val type: String, val destination: String, val tag: String = "")
