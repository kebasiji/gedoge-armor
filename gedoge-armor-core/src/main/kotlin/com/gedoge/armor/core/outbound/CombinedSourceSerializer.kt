package com.gedoge.armor.core.outbound

import com.gedoge.armor.core.spi.SourceSerializer
import com.gedoge.armor.core.utils.EventUtils

class CombinedSourceSerializer(private val sourceSerializers: List<SourceSerializer>) {
    private val defaultSourceSerializer: SourceSerializer

    init {
        defaultSourceSerializer = object : SourceSerializer {

            override fun getSupportedSourceClass(): Class<out Any> {
                return Any::class.java
            }

            override fun serialize(source: Any): String {
                return EventUtils.sourceToJsonString(source)
            }

        }
    }

    fun serialize(source: Any): String {
        return findAppropriateSourceSerializer(source).serialize(source)
    }

    private fun findAppropriateSourceSerializer(source: Any): SourceSerializer {
        for (sourceSerializer in sourceSerializers) {
            if (sourceSerializer.isSupportedSourceType(source)) {
                return sourceSerializer
            }
        }
        return defaultSourceSerializer
    }

}