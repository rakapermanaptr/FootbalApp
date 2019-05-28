package com.rakapermanaputra.footballmatchschedule.teams.players.playerdetail

import com.rakapermanaputra.footballmatchschedule.model.PlayerDetail

interface PlayerContract {

    interface View {
        fun setPlayerDetail(detail: List<PlayerDetail>)
    }

    interface Presenter {
        fun getPlayerDetail(idPlayer: String)
    }
}