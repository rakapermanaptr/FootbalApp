package com.rakapermanaputra.footballmatchschedule.teams

import com.rakapermanaputra.footballmatchschedule.model.TeamResponse
import com.rakapermanaputra.footballmatchschedule.model.repository.TeamRespositoryImpl
import com.rakapermanaputra.footballmatchschedule.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class TeamsPresenter(val mView: TeamsContract.View, val teamRepositoryImpl: TeamRespositoryImpl,
                     val scheduler: SchedulerProvider) : TeamsContract.Presenter {

    override fun getSearchTeam(teamName: String) {
        compositeDisposable.add(teamRepositoryImpl.getTeamByName(teamName)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<TeamResponse>() {
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onNext(t: TeamResponse) {
                        mView.showTeams(t.teams)
                    }

                    override fun onError(t: Throwable?) {
                        mView.hideLoading()
                        mView.showTeams(Collections.emptyList())
                    }
                }))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    val compositeDisposable = CompositeDisposable()

    override fun getAllTeams(idLeague: String) {
        mView.showLoading()
        compositeDisposable.add(teamRepositoryImpl.getAllTeams(idLeague)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<TeamResponse>() {
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onNext(t: TeamResponse) {
                        mView.showTeams(t.teams)
                    }

                    override fun onError(t: Throwable?) {
                        mView.hideLoading()
                        mView.showTeams(Collections.emptyList())
                    }
                }))
    }

}