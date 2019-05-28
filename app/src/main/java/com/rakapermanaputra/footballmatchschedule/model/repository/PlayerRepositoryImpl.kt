package com.rakapermanaputra.footballmatchschedule.model.repository

import com.rakapermanaputra.footballmatchschedule.model.PlayerDetailResponse
import com.rakapermanaputra.footballmatchschedule.model.PlayerResponse
import com.rakapermanaputra.footballmatchschedule.rest.ApiRest
import io.reactivex.Flowable

class PlayerRepositoryImpl(val apiRest: ApiRest) : PlayerRepository {

    override fun getPlayerDetail(id: String): Flowable<PlayerDetailResponse> = apiRest.getPlayerDetail(id)

    override fun getPlayers(id: String): Flowable<PlayerResponse> = apiRest.getAllPlayers(id)
}