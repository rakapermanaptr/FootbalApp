package com.rakapermanaputra.footballmatchschedule.matches.lastmatches

import com.rakapermanaputra.footballmatchschedule.model.EventResponse
import com.rakapermanaputra.footballmatchschedule.model.repository.EventRepositoryImpl
import com.rakapermanaputra.footballmatchschedule.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class LastMatchPresenter(val mView: LastMatchContract.View,
                         val eventRepositoryImpl: EventRepositoryImpl,
                         val scheduler: SchedulerProvider) : LastMatchContract.Presenter {

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    val compositeDisposable = CompositeDisposable()

    override fun getLastMatch(idLeague: String) {
        mView.showLoading()
        compositeDisposable.add(eventRepositoryImpl.getLastMatch(idLeague)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                //handle request
                .subscribeWith(object : ResourceSubscriber<EventResponse>() {
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onNext(t: EventResponse) {
                        mView.showScheduleList(t.events)
                    }

                    override fun onError(e: Throwable) {
                        mView.hideLoading()
                        mView.showScheduleList(Collections.emptyList())
                    }

                }))
    }
}