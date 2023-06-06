package com.gedoge.armor.core.impl.store.jdbc

import com.gedoge.armor.core.inbound.InboundEvent
import com.gedoge.armor.core.outbound.OutboundEvent
import java.sql.Connection

interface DriverDelegate {

    fun save(conn: Connection, event: OutboundEvent)

    fun findById(conn: Connection, eventId: Long): OutboundEvent?

    fun listWaitingEvent(conn: Connection): List<OutboundEvent>

    fun update(conn: Connection, event: OutboundEvent)

    fun saveIfAbsent(conn: Connection, event: InboundEvent): Boolean

}