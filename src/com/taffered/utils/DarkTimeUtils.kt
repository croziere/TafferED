package com.taffered.utils

import java.text.SimpleDateFormat
import java.util.*

object DarkTimeUtils {

    fun MilisToDuration(time: Long): String {
        val df = SimpleDateFormat("HH 'h', mm 'min', ss 'seconds'")
        df.timeZone = TimeZone.getTimeZone("GMT+0")
        return df.format(Date(time))
    }
}