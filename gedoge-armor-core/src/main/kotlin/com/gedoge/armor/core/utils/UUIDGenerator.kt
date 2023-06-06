package com.gedoge.armor.core.utils

import java.util.*

class UUIDGenerator : EventIdGenerator {

    override fun generateId(): String {
        return UUID.randomUUID().toString()
    }

}