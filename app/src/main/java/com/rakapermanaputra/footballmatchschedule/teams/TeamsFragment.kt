package com.rakapermanaputra.footballmatchschedule.teams


import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.rakapermanaputra.footballmatchschedule.R
import com.rakapermanaputra.footballmatchschedule.adapter.TeamsAdapter
import com.rakapermanaputra.footballmatchschedule.model.Team
import com.rakapermanaputra.footballmatchschedule.model.repository.TeamRespositoryImpl
import com.rakapermanaputra.footballmatchschedule.rest.ApiRest
import com.rakapermanaputra.footballmatchschedule.rest.ApiService
import com.rakapermanaputra.footballmatchschedule.utils.AppSchedulerProvider
import com.rakapermanaputra.footballmatchschedule.utils.invisible
import com.rakapermanaputra.footballmatchschedule.utils.visible
import kotlinx.android.synthetic.main.fragment_teams.*
import org.jetbrains.anko.support.v4.ctx

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamsFragment : Fragment(), TeamsContract.View {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamsPresenter
    private lateinit var leagueName: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Set progressBar color
        progressBar.indeterminateDrawable.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
        setHasOptionsMenu(true)

        val service = ApiService.getClient().create(ApiRest::class.java)
        val request = TeamRespositoryImpl(service)
        val scheduler = AppSchedulerProvider()
        presenter = TeamsPresenter(this, request, scheduler)
        presenter.getAllTeams("4328")
        val spinner = resources.getStringArray(R.array.leagueArray)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinner)
        spinnerTeams.adapter = spinnerAdapter
        spinnerTeams.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                parent.getChildAt(0).background = resources.getDrawable(R.drawable.card_rounded)
                leagueName = spinnerTeams.selectedItem.toString()
                when (leagueName) {
                    "English Premier League" -> presenter.getAllTeams("4328")
                    "Spanish La Liga" -> presenter.getAllTeams("4335")
                    "German Bundesliga" -> presenter.getAllTeams("4331")
                    "French Ligue 1" -> presenter.getAllTeams("4334")
                    "Italian Serie A" -> presenter.getAllTeams("4332")
                    "Portuguese Primeira Liga" -> presenter.getAllTeams("4344")
                    else -> presenter.getAllTeams("4328")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun showTeams(teamList: List<Team>) {
        teams.clear()
        teams.addAll(teamList)
        val layoutManager = GridLayoutManager(activity, 3)
        rvTeams.layoutManager = layoutManager
        rvTeams.adapter = TeamsAdapter(teams, ctx)
    }

    override fun showLoading() {
        progressBar.visible()
        rvTeams.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        rvTeams.visible()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)

        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView?

        searchView?.queryHint = "Search teams"

        searchView?.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.getSearchTeam(query)

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.getSearchTeam(newText)

                return false
            }
        })

        searchView?.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                presenter.getAllTeams("4335")
                return true
            }
        })
    }


}
