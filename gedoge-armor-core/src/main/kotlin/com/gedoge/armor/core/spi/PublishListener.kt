package com.gedoge.armor.core.spi

import com.gedoge.armor.core.outbound.OutboundEvent

interface PublishListener {

    fun onSent(event: OutboundEvent)

}