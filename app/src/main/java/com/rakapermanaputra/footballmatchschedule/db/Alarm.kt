package com.rakapermanaputra.footballmatchschedule.db

data class Alarm(val id: Long?,
                 val eventId: String) {
    companion object {
        const val TABLE_ALARM: String = "TABLE_ALARM"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
    }
}