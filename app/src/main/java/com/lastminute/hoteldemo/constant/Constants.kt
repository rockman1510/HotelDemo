package com.lastminute.hoteldemo.constant

object Constants {
    const val CACHE_SIZE = (10 * 5 * 1024 * 1024).toLong()
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 45L
    const val CONNECT_TIMEOUT = 30L
    const val CACHE_CONTROL = "Cache-Control"
    const val TIME_CACHE_ONLINE = "public, max-age = 60" // 1 minute
    const val TIME_CACHE_OFFLINE = "public, only-if-cached, max-stale = 604800"

    const val MIN_STAR = 0
    const val MAX_STAR = 5
    const val MIN_RATING = 0.0f
    const val MAX_RATING = 10.0f
    const val MIN_PRICE = 10
    const val MAX_PRICE = 1000
    val DEFAULT_SORT_TYPE = SortType.DEFAULT.name
    const val DEFAULT_CURRENCY = "EUR"
    const val HOTEL_PREFERENCES_NAME = "hotel_preferences"

    const val QUERY_OFFSET = "offset"
    const val QUERY_LIMIT = "limit"
}