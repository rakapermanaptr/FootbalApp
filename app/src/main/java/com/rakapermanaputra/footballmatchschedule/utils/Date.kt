package com.rakapermanaputra.footballmatchschedule.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.Date

object Date {
    fun formatDate(date: Date): String {
        return SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(date)
    }
}