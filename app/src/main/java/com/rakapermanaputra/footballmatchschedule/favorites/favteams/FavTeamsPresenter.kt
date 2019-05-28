package com.rakapermanaputra.footballmatchschedule.favorites.favteams

import com.rakapermanaputra.footballmatchschedule.model.Team
import com.rakapermanaputra.footballmatchschedule.model.repository.LocalRepoImpl
import com.rakapermanaputra.footballmatchschedule.model.repository.TeamRespositoryImpl
import com.rakapermanaputra.footballmatchschedule.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class FavTeamsPresenter(private val mView: FavTeamsContract.View,
                        private val teamRespositoryImpl: TeamRespositoryImpl,
                        private val localRepoImpl: LocalRepoImpl,
                        private val scheduler: SchedulerProvider) : FavTeamsContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    override fun getFavoriteTeams() {
        mView.showLoading()
        val favTeamList = localRepoImpl.getTeamFromDb()
        var teamList: MutableList<Team> = mutableListOf()
        for (fav in favTeamList) {
            compositeDisposable.add(teamRespositoryImpl.getDetailTeam(fav.idTeam)
                    .observeOn(scheduler.ui())
                    .subscribeOn(scheduler.io())
                    .subscribe {
                        teamList.add(it.teams[0])
                        mView.showFavTeams(teamList)
                        mView.hideLoading()
                    })
        }
        if (favTeamList.isEmpty()) {
            mView.hideLoading()
            mView.showFavTeams(teamList)
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}