package com.rakapermanaputra.footballmatchschedule.db

data class FavoriteTeam(
        val id: Long?,
        val idTeam: String,
        val teamImg: String) {
    companion object {
        const val TABLE_TEAM: String = "TABLE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_IMG: String = "TEAM_IMG"
    }
}