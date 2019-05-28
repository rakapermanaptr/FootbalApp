package com.rakapermanaputra.footballmatchschedule.model.repository

import android.content.Context
import com.rakapermanaputra.footballmatchschedule.db.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class LocalRepoImpl(private val context: Context) : LocalRepo {
    override fun getDataEventAlarmFromDb(): List<Alarm> {
        lateinit var alarmEventList: List<Alarm>
        context.database.use {
            val result = select(Alarm.TABLE_ALARM)
            val alarm = result.parseList(classParser<Alarm>())
            alarmEventList = alarm
        }
        return alarmEventList
    }

    override fun insertEventAlarm(eventId: String) {
        context.database.use {
            insert(Alarm.TABLE_ALARM,
                    Alarm.EVENT_ID to eventId)
        }
    }

    override fun deleteEventAlarm(eventId: String) {
        context.database.use {
            delete(Alarm.TABLE_ALARM,
                    "(EVENT_ID = {id})",
                    "id" to eventId)
        }
    }

    override fun getTeamFromDb(): List<FavoriteTeam> {
        lateinit var favoriteTeams: List<FavoriteTeam>
        context.database.use {
            val result = select(FavoriteTeam.TABLE_TEAM)
            val favoriteTeam = result.parseList(classParser<FavoriteTeam>())
            favoriteTeams = favoriteTeam
        }
        return favoriteTeams
    }

    override fun insertTeamData(teamId: String, teamImg: String) {
        context.database.use {
            insert(FavoriteTeam.TABLE_TEAM,
                    FavoriteTeam.TEAM_ID to teamId,
                    FavoriteTeam.TEAM_IMG to teamId)
        }
    }

    override fun deleteTeamData(teamId: String) {
        context.database.use {
            delete(FavoriteTeam.TABLE_TEAM,
                    "(TEAM_ID = {id})",
                    "id" to teamId)
        }
    }

    override fun getMatchFromDb(): List<FavoriteEvent> {
        lateinit var favoriteList: List<FavoriteEvent>
        context.database.use {
            val result = select(FavoriteEvent.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteEvent>())
            favoriteList = favorite
        }
        return favoriteList
    }

    override fun insertFavorite(eventId: String, homeTeamId: String, awayTeamId: String) {
        context.database.use {
            insert(FavoriteEvent.TABLE_FAVORITE,
                    FavoriteEvent.EVENT_ID to eventId,
                    FavoriteEvent.HOME_TEAM_ID to homeTeamId,
                    FavoriteEvent.AWAY_TEAM_ID to awayTeamId)
        }
    }

    override fun deleteFavorite(eventId: String) {
        context.database.use {
            delete(FavoriteEvent.TABLE_FAVORITE,
                    "(EVENT_ID = {id})",
                    "id" to eventId)
        }
    }

}