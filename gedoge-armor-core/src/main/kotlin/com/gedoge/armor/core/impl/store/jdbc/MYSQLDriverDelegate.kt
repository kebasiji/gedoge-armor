package com.gedoge.armor.core.impl.store.jdbc

import com.gedoge.armor.core.inbound.InboundEvent
import com.gedoge.armor.core.outbound.OutboundEvent
import com.gedoge.armor.core.outbound.OutboundEventStatus
import com.gedoge.armor.core.utils.EventUtils
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Timestamp

class MYSQLDriverDelegate(private val inboundEventSchema: String, private val outboundEventSchema: String) :
    DriverDelegate {

    override fun save(conn: Connection, event: OutboundEvent) {
        val sql = """INSERT INTO $inboundEventSchema 
            | (event_id, occurred_at, type, source_id, source, payload, destination, tag, status)
            | VALUES (?,?,?,?,?,?,?,?,?)
        """.trimMargin()
        conn.prepareStatement(sql).apply {
            setString(1, event.eventId)
            setTimestamp(2, Timestamp.valueOf(event.occurredAt))
            setString(3, event.type)
            setString(4, event.sourceId)
            setString(5, event.source)
            setString(6, event.payload?.run(EventUtils::payloadToJsonString))
            setString(7, event.destination)
            setString(8, event.tag)
            setString(9, event.status.name)
        }.use { ps ->
            ps.executeUpdate()
        }
    }

    private fun ResultSet.toEvent(): OutboundEvent {
        return OutboundEvent(
            eventId = getString("event_id"),
            occurredAt = getTimestamp("occurred_at").toLocalDateTime(),
            type = getString("type"),
            sourceId = getString("source_id"),
            source = getString("source"),
            payload = getString("payload")?.run(EventUtils::jsonStringToPayload),
            destination = getString("destination"),
            tag = getString("tag"),
            _status = OutboundEventStatus.valueOf(getString("status")),
            _sentAt = getTimestamp("sent_at")?.toLocalDateTime()
        )
    }

    override fun findById(conn: Connection, eventId: Long): OutboundEvent? {
        val sql = """SELECT * FROM $inboundEventSchema WHERE event_id = ?"""
        return conn.prepareStatement(sql).use { ps ->
            ps.executeQuery().use { rs ->
                if (rs.next()) {
                    rs.toEvent()
                } else {
                    null
                }
            }
        }
    }

    override fun listWaitingEvent(conn: Connection): List<OutboundEvent> {
        val sql = """SELECT * FROM $inboundEventSchema WHERE status = 'WAITING' ORDER BY occurred_at"""
        return conn.prepareStatement(sql).use { ps ->
            ps.executeQuery().use { rs ->
                val eventList = mutableListOf<OutboundEvent>()
                while (rs.next()) {
                    eventList.add(rs.toEvent())
                }
                eventList
            }
        }
    }

    override fun update(conn: Connection, event: OutboundEvent) {
        val sql =
            """UPDATE $inboundEventSchema SET status = ?, sent_at = ? WHERE event_id = ? AND status = 'WAITING'"""
        conn.prepareStatement(sql).apply {
            setString(1, event.status.name)
            setTimestamp(2, Timestamp.valueOf(event.sentAt!!))
            setString(3, event.eventId)
        }.use { ps ->
            ps.executeUpdate()
        }
    }

    override fun saveIfAbsent(conn: Connection, event: InboundEvent): Boolean {
        val sql = """INSERT IGNORE INTO  $outboundEventSchema 
            | (event_id, occurred_at, type, source_id, source, payload, arrived_at)
            | VALUES (?,?,?,?,?,?,?)
        """.trimMargin()
        return conn.prepareStatement(sql).apply {
            setString(1, event.eventId)
            setTimestamp(2, Timestamp.valueOf(event.occurredAt))
            setString(3, event.type)
            setString(4, event.sourceId)
            setString(5, event.source)
            setString(6, event.payload?.run(EventUtils::payloadToJsonString))
            setTimestamp(7, Timestamp.valueOf(event.arrivedAt))
        }.use { ps ->
            ps.executeUpdate() == 1
        }
    }

}