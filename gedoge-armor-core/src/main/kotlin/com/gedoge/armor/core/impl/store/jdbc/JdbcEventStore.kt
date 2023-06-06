package com.gedoge.armor.core.impl.store.jdbc

import com.gedoge.armor.core.store.EventStore
import com.gedoge.armor.core.inbound.InboundEvent
import com.gedoge.armor.core.outbound.OutboundEvent
import org.springframework.jdbc.datasource.DataSourceUtils
import org.springframework.transaction.annotation.Transactional
import javax.sql.DataSource

@Transactional
class JdbcEventStore(private val dataSource: DataSource, private val driverDelegate: DriverDelegate) : EventStore {

    override fun save(event: OutboundEvent) {
        driverDelegate.save(DataSourceUtils.getConnection(dataSource), event)
    }

    override fun findById(eventId: Long): OutboundEvent? {
        return driverDelegate.findById(DataSourceUtils.getConnection(dataSource), eventId)
    }

    override fun listWaitingEvent(): List<OutboundEvent> {
        return driverDelegate.listWaitingEvent(DataSourceUtils.getConnection(dataSource))
    }

    override fun update(event: OutboundEvent) {
        driverDelegate.update(DataSourceUtils.getConnection(dataSource), event)
    }

    override fun saveIfAbsent(event: InboundEvent): Boolean {
        return driverDelegate.saveIfAbsent(DataSourceUtils.getConnection(dataSource), event)
    }

}