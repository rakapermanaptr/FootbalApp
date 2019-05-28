package com.rakapermanaputra.footballmatchschedule.detail

import com.rakapermanaputra.footballmatchschedule.db.FavoriteEvent
import com.rakapermanaputra.footballmatchschedule.model.Event
import com.rakapermanaputra.footballmatchschedule.model.Team

interface DetailContract {

    interface View {
        fun showHomeTeamBadge(team: Team)
        fun showAwayTeamBadge(team: Team)
        fun setFavoriteState(favList: List<FavoriteEvent>)
        fun setAlarmState(favList: List<FavoriteEvent>)
        fun setDetailEvent(detail: List<Event>)
    }

    interface Presenter {
        fun getEventDetail(eventId: String)
        fun getHomeTeamBadge(id: String)
        fun getAwayTeamBadge(id: String)
        fun insertFavorite(eventId: String, homeTeamId: String, awayTeamId: String)
        fun deleteFavorite(eventId: String)
        fun insertAlarm(eventId: String)
        fun deleteAlarm(eventId: String)
    }
}