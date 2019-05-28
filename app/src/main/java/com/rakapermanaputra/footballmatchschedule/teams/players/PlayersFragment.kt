package com.rakapermanaputra.footballmatchschedule.teams.players


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rakapermanaputra.footballmatchschedule.R
import com.rakapermanaputra.footballmatchschedule.adapter.PlayersAdapter
import com.rakapermanaputra.footballmatchschedule.model.Player
import com.rakapermanaputra.footballmatchschedule.model.Team
import com.rakapermanaputra.footballmatchschedule.model.repository.PlayerRepositoryImpl
import com.rakapermanaputra.footballmatchschedule.rest.ApiRest
import com.rakapermanaputra.footballmatchschedule.rest.ApiService
import com.rakapermanaputra.footballmatchschedule.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_players.*
import org.jetbrains.anko.support.v4.ctx

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PlayersFragment : Fragment(), PlayersContract.View {

    private var players: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayersPresenter

    var team: Team? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_players, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        team = arguments?.getParcelable("teams")
        Log.v("data id team ", "idteam for players : " + team?.idTeam)

        val service = ApiService.getClient().create(ApiRest::class.java)
        val request = PlayerRepositoryImpl(service)
        val scheduler = AppSchedulerProvider()
        presenter = PlayersPresenter(this, request, scheduler)
        presenter.getAllPlayers(team!!.idTeam)
    }

    override fun showThumbnail(playersList: List<Player>) {
        players.clear()
        players.addAll(playersList)
        val layoutManager = GridLayoutManager(ctx, 3)
        rvPlayers.layoutManager = layoutManager
        rvPlayers.adapter = PlayersAdapter(playersList, ctx)
    }
}
