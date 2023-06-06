package com.gedoge.armor.core.store

import com.gedoge.armor.core.inbound.InboundEvent

interface InboundEventStore {

    fun saveIfAbsent(event: InboundEvent): Boolean

}