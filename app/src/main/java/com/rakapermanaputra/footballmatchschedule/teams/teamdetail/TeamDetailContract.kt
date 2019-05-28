package com.rakapermanaputra.footballmatchschedule.teams.teamdetail

import com.rakapermanaputra.footballmatchschedule.model.Team

interface TeamDetailContract {

    interface View {
        fun showTeamBadge(team: Team)
        fun setDetailTeam(detail: List<Team>)
    }

    interface Presenter {
        fun getTeamBadge(idTeam: String)
        fun getTeamDetail(idTeam: String)
        fun insertFavorite(teamId: String, teamImg: String)
        fun deleteFavorite(teamId: String)
    }
}