package com.rakapermanaputra.footballmatchschedule.teams

import com.rakapermanaputra.footballmatchschedule.model.Team

interface TeamsContract {
    interface View {
        fun showTeams(teamList: List<Team>)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun getAllTeams(idLeague: String)
        fun onDestroy()
        fun getSearchTeam(teamName: String)
    }
}