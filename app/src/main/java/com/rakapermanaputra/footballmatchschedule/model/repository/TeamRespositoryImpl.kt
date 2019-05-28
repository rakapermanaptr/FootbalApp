package com.rakapermanaputra.footballmatchschedule.model.repository

import com.rakapermanaputra.footballmatchschedule.model.TeamResponse
import com.rakapermanaputra.footballmatchschedule.rest.ApiRest
import io.reactivex.Flowable

class TeamRespositoryImpl(val apiRest: ApiRest) : TeamRepository {
    override fun getTeamByName(query: String): Flowable<TeamResponse> = apiRest.searchTeams(query)

    override fun getTeams(id: String): Flowable<TeamResponse> = apiRest.getAllTeam(id)

    override fun getDetailTeam(id: String): Flowable<TeamResponse> = apiRest.getDetailTeam(id)

    override fun getAllTeams(id: String): Flowable<TeamResponse> = apiRest.getAllTeam(id)

}