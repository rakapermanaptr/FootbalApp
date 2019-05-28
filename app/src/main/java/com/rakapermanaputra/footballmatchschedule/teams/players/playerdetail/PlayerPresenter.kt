package com.rakapermanaputra.footballmatchschedule.teams.players.playerdetail

import com.rakapermanaputra.footballmatchschedule.model.repository.PlayerRepositoryImpl
import com.rakapermanaputra.footballmatchschedule.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class PlayerPresenter(val mView: PlayerContract.View,
                      val playerRepositoryImpl: PlayerRepositoryImpl,
                      val scheduler: SchedulerProvider) : PlayerContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    override fun getPlayerDetail(idPlayer: String) {
        compositeDisposable.add(playerRepositoryImpl.getPlayerDetail(idPlayer)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribe {
                    mView.setPlayerDetail(it.player)
                })
    }
}