package com.rakapermanaputra.footballmatchschedule.matches.searchmatches

import com.rakapermanaputra.footballmatchschedule.model.repository.EventRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchMatchPresenter(val mView: SearchMatchContract.View,
                           val eventRepositoryImpl: EventRepositoryImpl) : SearchMatchContract.Presenter {

    val compositeDisposable = CompositeDisposable()


    override fun searchMatch(query: String?) {
        mView.showLoading()
        compositeDisposable.add(eventRepositoryImpl.getSearchMatches(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    mView.hideLoading()
                    mView.showMatches(it.events)
                })
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}