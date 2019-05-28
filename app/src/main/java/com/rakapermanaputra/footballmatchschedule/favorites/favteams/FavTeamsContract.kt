package com.rakapermanaputra.footballmatchschedule.favorites.favteams

import com.rakapermanaputra.footballmatchschedule.model.Team

interface FavTeamsContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showFavTeams(favTeamsList: List<Team>)
    }

    interface Presenter {
        fun getFavoriteTeams()
        fun onDestroy()
    }
}