package com.rakapermanaputra.footballmatchschedule.favorites.favteams


import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rakapermanaputra.footballmatchschedule.R
import com.rakapermanaputra.footballmatchschedule.adapter.TeamsAdapter
import com.rakapermanaputra.footballmatchschedule.model.Team
import com.rakapermanaputra.footballmatchschedule.model.repository.LocalRepoImpl
import com.rakapermanaputra.footballmatchschedule.model.repository.TeamRespositoryImpl
import com.rakapermanaputra.footballmatchschedule.rest.ApiRest
import com.rakapermanaputra.footballmatchschedule.rest.ApiService
import com.rakapermanaputra.footballmatchschedule.utils.AppSchedulerProvider
import com.rakapermanaputra.footballmatchschedule.utils.invisible
import com.rakapermanaputra.footballmatchschedule.utils.visible
import kotlinx.android.synthetic.main.fragment_fav_teams.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavTeamsFragment : Fragment(), FavTeamsContract.View {

    private val favTeamList: MutableList<Team> = mutableListOf()
    private lateinit var presenter: FavTeamsPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Set progressBar color
        progressBar.indeterminateDrawable.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN)

        val service = ApiService.getClient().create(ApiRest::class.java)
        val request = TeamRespositoryImpl(service)
        val localRepo = LocalRepoImpl(ctx)
        val scheduler = AppSchedulerProvider()
        presenter = FavTeamsPresenter(this, request, localRepo, scheduler)
        presenter.getFavoriteTeams()

        refreshFavTeam.onRefresh {
            presenter.getFavoriteTeams()
        }
    }

    override fun showLoading() {
        progressBar.visible()
        rvFavTeams.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        rvFavTeams.visible()
    }

    override fun showFavTeams(favTeams: List<Team>) {
        favTeamList.clear()
        favTeamList.addAll(favTeams)
        refreshFavTeam.isRefreshing = false
        val layoutManager = GridLayoutManager(activity, 3)
        rvFavTeams.layoutManager = layoutManager
        rvFavTeams.adapter = TeamsAdapter(favTeams, ctx)
    }

    override fun onResume() {
        super.onResume()
        presenter.getFavoriteTeams()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_teams, container, false)
    }

}
