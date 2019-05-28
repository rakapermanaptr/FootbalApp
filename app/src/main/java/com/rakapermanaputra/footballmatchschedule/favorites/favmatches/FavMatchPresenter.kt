package com.rakapermanaputra.footballmatchschedule.favorites.favmatches

import com.rakapermanaputra.footballmatchschedule.model.Event
import com.rakapermanaputra.footballmatchschedule.model.repository.EventRepositoryImpl
import com.rakapermanaputra.footballmatchschedule.model.repository.LocalRepoImpl
import com.rakapermanaputra.footballmatchschedule.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class FavMatchPresenter(val mView: FavMatchContract.View,
                        val eventRepositoryImpl: EventRepositoryImpl,
                        val localRepoImpl: LocalRepoImpl,
                        val scheduler: SchedulerProvider) : FavMatchContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    override fun getFavoriteMatch() {
        mView.showLoading()
        val favList = localRepoImpl.getMatchFromDb()
        var eventList: MutableList<Event> = mutableListOf()
        for (fav in favList) {
            compositeDisposable.add(eventRepositoryImpl.getEventById(fav.eventId)
                    .observeOn(scheduler.ui())
                    .subscribeOn(scheduler.io())
                    .subscribe {
                        eventList.add(it.events[0])
                        mView.showFavoritesList(eventList)
                        mView.hideLoading()
                    })
        }

        if (favList.isEmpty()) {
            mView.hideLoading()
            mView.showFavoritesList(eventList)
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}