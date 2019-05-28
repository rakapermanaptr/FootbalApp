package com.rakapermanaputra.footballmatchschedule.teams.players

import com.rakapermanaputra.footballmatchschedule.model.repository.PlayerRepository
import com.rakapermanaputra.footballmatchschedule.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class PlayersPresenter(private val mView: PlayersContract.View,
                       private val playerRepository: PlayerRepository,
                       private val scheduler: SchedulerProvider) : PlayersContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getAllPlayers(idTeam: String) {
        compositeDisposable.add(playerRepository.getPlayers(idTeam)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribe {
                    mView.showThumbnail(it.player)
                })
    }
}