package com.rakapermanaputra.footballmatchschedule.teams.teamdetail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rakapermanaputra.footballmatchschedule.R
import com.rakapermanaputra.footballmatchschedule.model.Team
import com.rakapermanaputra.footballmatchschedule.model.repository.LocalRepoImpl
import com.rakapermanaputra.footballmatchschedule.model.repository.TeamRespositoryImpl
import com.rakapermanaputra.footballmatchschedule.rest.ApiRest
import com.rakapermanaputra.footballmatchschedule.rest.ApiService
import com.rakapermanaputra.footballmatchschedule.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_team_overview.*
import org.jetbrains.anko.support.v4.act

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamOverviewFragment : Fragment(), TeamDetailContract.View {

    private lateinit var presenter: TeamDetailPresenter
    var team: Team? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_overview, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        team = arguments?.getParcelable("teams")
        Log.v("isi ", "id : " + team?.idTeam)

        val service = ApiService.getClient().create(ApiRest::class.java)
        val request = TeamRespositoryImpl(service)
        val localRepoImpl = LocalRepoImpl(act)
        val scheduler = AppSchedulerProvider()
        presenter = TeamDetailPresenter(this, request, localRepoImpl, scheduler)
        presenter.getTeamDetail(team!!.idTeam)
    }

    override fun setDetailTeam(detail: List<Team>) {
        tvTeamNameOvw.text = detail[0].strTeam
        tvFormedYear.text = detail[0].intFormedYear
        tvTeamDesc.text = detail[0].strDescriptionEN
    }

    override fun showTeamBadge(team: Team) {

    }

}
