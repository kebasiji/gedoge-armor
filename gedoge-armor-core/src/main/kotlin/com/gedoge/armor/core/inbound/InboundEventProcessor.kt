package com.gedoge.armor.core.inbound

interface InboundEventProcessor {

    fun process(eventJsonString: String)

}