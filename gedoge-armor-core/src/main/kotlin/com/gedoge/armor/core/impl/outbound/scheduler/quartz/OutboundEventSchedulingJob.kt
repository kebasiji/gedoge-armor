package com.gedoge.armor.core.impl.outbound.scheduler.quartz

import com.gedoge.armor.core.spi.EventPublisher
import com.gedoge.armor.core.store.OutboundEventStore
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean

@DisallowConcurrentExecution
class OutboundEventSchedulingJob(
    private val eventStore: OutboundEventStore,
    private val eventPublisher: EventPublisher,
) : QuartzJobBean() {

    override fun executeInternal(context: JobExecutionContext) {
        val waitingEventList = eventStore.listWaitingEvent()
        for (event in waitingEventList) {
            eventPublisher.publish(event)
        }
    }

}