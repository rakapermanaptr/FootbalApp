package com.rakapermanaputra.footballmatchschedule.teams.players

import com.rakapermanaputra.footballmatchschedule.model.Player

interface PlayersContract {

    interface View {
        fun showThumbnail(playersList: List<Player>)
    }

    interface Presenter {
        fun getAllPlayers(idTeam: String)
    }
}