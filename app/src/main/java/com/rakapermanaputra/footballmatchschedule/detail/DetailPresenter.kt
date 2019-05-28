package com.rakapermanaputra.footballmatchschedule.detail

import com.rakapermanaputra.footballmatchschedule.model.repository.EventRepositoryImpl
import com.rakapermanaputra.footballmatchschedule.model.repository.LocalRepoImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailPresenter(val mView: DetailContract.View, val eventRepositoryImpl: EventRepositoryImpl,
                      val localRepoImpl: LocalRepoImpl) : DetailContract.Presenter {

    override fun insertAlarm(eventId: String) {
        localRepoImpl.insertEventAlarm(eventId)
    }

    override fun deleteAlarm(eventId: String) {
        localRepoImpl.deleteEventAlarm(eventId)
    }

    override fun insertFavorite(eventId: String, homeTeamId: String, awayTeamId: String) {
        localRepoImpl.insertFavorite(eventId, homeTeamId, awayTeamId)
    }

    override fun deleteFavorite(eventId: String) {
        localRepoImpl.deleteFavorite(eventId)
    }

    val compositeDisposable = CompositeDisposable()

    override fun getHomeTeamBadge(id: String) {
        compositeDisposable.add(eventRepositoryImpl.getDetailTeams(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    mView.showHomeTeamBadge(it.teams[0])
                })
    }

    override fun getAwayTeamBadge(id: String) {
        compositeDisposable.add(eventRepositoryImpl.getDetailTeams(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    mView.showAwayTeamBadge(it.teams[0])
                })
    }

    override fun getEventDetail(eventId: String) {
        compositeDisposable.add(eventRepositoryImpl.getEventById(eventId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    mView.setDetailEvent(it.events)
                })
    }

}