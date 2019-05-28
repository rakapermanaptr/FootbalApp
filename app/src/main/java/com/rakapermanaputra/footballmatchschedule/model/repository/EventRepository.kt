package com.rakapermanaputra.footballmatchschedule.model.repository

import com.rakapermanaputra.footballmatchschedule.model.EventResponse
import com.rakapermanaputra.footballmatchschedule.model.SearchingMatches
import com.rakapermanaputra.footballmatchschedule.model.TeamResponse
import io.reactivex.Flowable

interface EventRepository {

    fun getNextMatch(id: String): Flowable<EventResponse>

    fun getLastMatch(id: String): Flowable<EventResponse>

    fun getDetailTeams(id: String = "0"): Flowable<TeamResponse>

    fun getEventById(id: String): Flowable<EventResponse>

    fun getSearchMatches(query: String?): Flowable<SearchingMatches>
}