package com.rakapermanaputra.footballmatchschedule.db

data class FavoriteEvent(val id: Long?,
                    val eventId: String,
                    val homeTeamId: String,
                    val awayTeamId: String) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val HOME_TEAM_ID: String = "HOME_TEAM_ID"
        const val AWAY_TEAM_ID: String = "AWAY_TEAM_ID"
    }
}