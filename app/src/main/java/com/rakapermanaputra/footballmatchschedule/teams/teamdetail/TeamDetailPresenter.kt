package com.rakapermanaputra.footballmatchschedule.teams.teamdetail

import com.rakapermanaputra.footballmatchschedule.model.repository.LocalRepoImpl
import com.rakapermanaputra.footballmatchschedule.model.repository.TeamRespositoryImpl
import com.rakapermanaputra.footballmatchschedule.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class TeamDetailPresenter(val mView: TeamDetailContract.View,
                          val teamRepositoryImpl: TeamRespositoryImpl,
                          val localRepoImpl: LocalRepoImpl,
                          val scheduler: SchedulerProvider) : TeamDetailContract.Presenter {

    override fun insertFavorite(teamId: String, teamImg: String) {
        localRepoImpl.insertTeamData(teamId, teamImg)
    }

    override fun deleteFavorite(teamId: String) {
        localRepoImpl.deleteTeamData(teamId)
    }

    val compositeDisposable = CompositeDisposable()

    override fun getTeamDetail(idTeam: String) {
        compositeDisposable.add(teamRepositoryImpl.getDetailTeam(idTeam)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribe {
                    mView.setDetailTeam(it.teams)
                })
    }

    override fun getTeamBadge(idTeam: String) {
        compositeDisposable.add(teamRepositoryImpl.getDetailTeam(idTeam)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribe {
                    mView.showTeamBadge(it.teams[0])
                })
    }


}