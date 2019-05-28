package com.rakapermanaputra.footballmatchschedule.model.repository

import com.rakapermanaputra.footballmatchschedule.model.PlayerDetailResponse
import com.rakapermanaputra.footballmatchschedule.model.PlayerResponse
import io.reactivex.Flowable

interface PlayerRepository {

    fun getPlayerDetail(id: String = "0"): Flowable<PlayerDetailResponse>

    fun getPlayers(id: String = "0"): Flowable<PlayerResponse>
}