package com.gedoge.armor.core.domain

class MutablePayload(private val mutableMap: MutableMap<String, String> = HashMap()) : Payload(mutableMap),
    MutableMap<String, String> {

    fun put(key: String, value: Boolean) {
        mutableMap[key] = value.toString()
    }

    fun put(key: String, value: Char) {
        mutableMap[key] = value.toString()
    }

    fun put(key: String, value: Double) {
        mutableMap[key] = value.toString()
    }

    fun put(key: String, value: Float) {
        mutableMap[key] = value.toString()
    }

    fun put(key: String, value: Short) {
        mutableMap[key] = value.toString()
    }

    fun put(key: String, value: Int) {
        mutableMap[key] = value.toString()
    }

    fun put(key: String, value: Long) {
        mutableMap[key] = value.toString()
    }

    override fun put(key: String, value: String): String? {
        return mutableMap.put(key, value)
    }

    override val entries: MutableSet<MutableMap.MutableEntry<String, String>>
        get() = mutableMap.entries
    override val keys: MutableSet<String>
        get() = mutableMap.keys

    override val values: MutableCollection<String>
        get() = mutableMap.values

    override fun clear() {
        mutableMap.clear()
    }

    override fun remove(key: String): String? {
        return mutableMap.remove(key)
    }

    override fun putAll(from: Map<out String, String>) {
        mutableMap.putAll(from)
    }

}