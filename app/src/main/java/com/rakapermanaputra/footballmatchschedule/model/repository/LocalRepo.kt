package com.rakapermanaputra.footballmatchschedule.model.repository

import com.rakapermanaputra.footballmatchschedule.db.Alarm
import com.rakapermanaputra.footballmatchschedule.db.FavoriteEvent
import com.rakapermanaputra.footballmatchschedule.db.FavoriteTeam

interface LocalRepo {

    fun getMatchFromDb(): List<FavoriteEvent>

    fun insertFavorite(eventId: String, homeTeamId: String, awayTeamId: String)

    fun deleteFavorite(eventId: String)

    fun getTeamFromDb(): List<FavoriteTeam>

    fun insertTeamData(teamId: String, teamImg: String)

    fun deleteTeamData(teamId: String)

    fun getDataEventAlarmFromDb(): List<Alarm>

    fun insertEventAlarm(eventId: String)

    fun deleteEventAlarm(eventId: String)

}