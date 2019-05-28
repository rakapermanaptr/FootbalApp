package com.rakapermanaputra.footballmatchschedule.model.repository

import com.rakapermanaputra.footballmatchschedule.model.EventResponse
import com.rakapermanaputra.footballmatchschedule.model.SearchingMatches
import com.rakapermanaputra.footballmatchschedule.model.TeamResponse
import com.rakapermanaputra.footballmatchschedule.rest.ApiRest
import io.reactivex.Flowable

open class EventRepositoryImpl(private val apiRest: ApiRest) : EventRepository {

    override fun getSearchMatches(query: String?): Flowable<SearchingMatches> = apiRest.searchMatches(query)

    override fun getEventById(id: String): Flowable<EventResponse> = apiRest.getEventById(id)

    override fun getDetailTeams(id: String): Flowable<TeamResponse> = apiRest.getDetailTeam(id)

    override fun getLastMatch(id: String): Flowable<EventResponse> = apiRest.getLastMatch(id)

    override fun getNextMatch(id: String): Flowable<EventResponse> = apiRest.getNextMatch(id)

}