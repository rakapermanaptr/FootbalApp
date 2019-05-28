package com.rakapermanaputra.footballmatchschedule.model.repository

import com.rakapermanaputra.footballmatchschedule.model.TeamResponse
import io.reactivex.Flowable

interface TeamRepository {

    fun getTeams(id: String = "0"): Flowable<TeamResponse>

    fun getDetailTeam(id: String = "0"): Flowable<TeamResponse>

    fun getAllTeams(id: String): Flowable<TeamResponse>

    fun getTeamByName(query: String): Flowable<TeamResponse>
}