package com.gedoge.armor.core.spi

interface SourceSerializer {

    fun getSupportedSourceClass(): Class<out Any>

    fun isSupportedSourceType(source: Any): Boolean {
        val clazz = source::class.java
        return clazz == getSupportedSourceClass()
    }

    fun serialize(source: Any): String

}