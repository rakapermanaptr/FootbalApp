package com.rakapermanaputra.footballmatchschedule.rest

import com.rakapermanaputra.footballmatchschedule.model.*
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRest {

    @GET("eventsnextleague.php")
    fun getNextMatch(@Query("id") id: String): Flowable<EventResponse>

    @GET("eventspastleague.php")
    fun getLastMatch(@Query("id") id: String): Flowable<EventResponse>

    @GET("lookupteam.php")
    fun getDetailTeam(@Query("id") id: String): Flowable<TeamResponse>

    @GET("lookupevent.php")
    fun getEventById(@Query("id") id: String): Flowable<EventResponse>

    @GET("lookup_all_teams.php")
    fun getAllTeam(@Query("id") id: String): Flowable<TeamResponse>

    @GET("lookup_all_players.php")
    fun getAllPlayers(@Query("id") id: String?): Flowable<PlayerResponse>

    @GET("lookupplayer.php")
    fun getPlayerDetail(@Query("id") id: String): Flowable<PlayerDetailResponse>

    @GET("searchevents.php")
    fun searchMatches(@Query("e") query: String?): Flowable<SearchingMatches>

    @GET("searchteams.php")
    fun searchTeams(@Query("t") query: String): Flowable<TeamResponse>

}