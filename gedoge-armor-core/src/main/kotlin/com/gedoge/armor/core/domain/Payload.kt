package com.gedoge.armor.core.domain

open class Payload(private val map: Map<String, String> = HashMap()) : Map<String, String> {

    fun getBoolean(key: String): Boolean? {
        return map[key]?.toBoolean()
    }

    fun getChar(key: String): Char? {
        return map[key]?.get(0)
    }

    fun getDouble(key: String): Double? {
        return map[key]?.toDouble()
    }

    fun getFloat(key: String): Float? {
        return map[key]?.toFloat()
    }

    fun getShort(key: String): Short? {
        return map[key]?.toShort()
    }

    fun getInt(key: String): Int? {
        return map[key]?.toInt()
    }

    fun getLong(key: String): Long? {
        return map[key]?.toLong()
    }

    fun getString(key: String): String? {
        return map[key]
    }

    fun toMap(): Map<String, String> {
        return map.toMap()
    }

    fun toMutableMap(): MutableMap<String, String> {
        return map.toMutableMap()
    }
    
    override val entries: Set<Map.Entry<String, String>>
        get() = map.entries
    override val keys: Set<String>
        get() = map.keys
    override val size: Int
        get() = map.size
    override val values: Collection<String>
        get() = map.values

    override fun isEmpty(): Boolean {
        return map.isEmpty()
    }

    override fun get(key: String): String? {
        return map[key]
    }

    override fun containsValue(value: String): Boolean {
        return map.containsValue(value)
    }

    override fun containsKey(key: String): Boolean {
        return map.containsKey(key)
    }

}