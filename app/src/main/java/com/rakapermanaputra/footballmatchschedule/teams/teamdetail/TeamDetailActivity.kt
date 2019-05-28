package com.rakapermanaputra.footballmatchschedule.teams.teamdetail

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.rakapermanaputra.footballmatchschedule.R
import com.rakapermanaputra.footballmatchschedule.adapter.ViewPagerAdapter
import com.rakapermanaputra.footballmatchschedule.db.FavoriteTeam
import com.rakapermanaputra.footballmatchschedule.db.database
import com.rakapermanaputra.footballmatchschedule.model.Team
import com.rakapermanaputra.footballmatchschedule.model.repository.LocalRepoImpl
import com.rakapermanaputra.footballmatchschedule.model.repository.TeamRespositoryImpl
import com.rakapermanaputra.footballmatchschedule.rest.ApiRest
import com.rakapermanaputra.footballmatchschedule.rest.ApiService
import com.rakapermanaputra.footballmatchschedule.teams.players.PlayersFragment
import com.rakapermanaputra.footballmatchschedule.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class TeamDetailActivity : AppCompatActivity(), TeamDetailContract.View {

    private lateinit var presenter: TeamDetailPresenter
    private var menuItem: Menu? = null

    private var isFavorite: Boolean = false
    lateinit var team: Team

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        team = intent.getParcelableExtra("teams")
        supportActionBar?.title = team.strTeam

        val service = ApiService.getClient().create(ApiRest::class.java)
        val request = TeamRespositoryImpl(service)
        val localRepo = LocalRepoImpl(applicationContext)
        val scheduler = AppSchedulerProvider()
        presenter = TeamDetailPresenter(this, request, localRepo, scheduler)

        presenter.getTeamBadge(team.idTeam)
        presenter.getTeamDetail(team.idTeam)

        favoriteState()

        val bundle = Bundle()
        bundle.putParcelable("teams", team)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        val fragmentOverviewFragment = TeamOverviewFragment()
        val fragmentTeamPlayers = PlayersFragment()
        fragmentOverviewFragment.arguments = bundle
        fragmentTeamPlayers.arguments = bundle
        val vPager = findViewById<ViewPager>(R.id.viewPager)
        val tabs = findViewById<TabLayout>(R.id.tabs)
        adapter.populateFragment(fragmentOverviewFragment, "Team Overview")
        adapter.populateFragment(fragmentTeamPlayers, "Players")
        vPager.adapter = adapter
        tabs.setupWithViewPager(vPager)
        Log.v("data detail team act, ", "idTeam : " + team.idTeam)
    }

    override fun showTeamBadge(team: Team) {
        Glide.with(applicationContext)
                .load(team.strTeamBadge)
                .into(imgTeamBadge)
    }

    override fun setDetailTeam(detail: List<Team>) {
        tvTeamName.text = detail[0].strTeam
        tvTeamLeague.text = detail[0].strLeague
        tvTeamStadium.text = detail[0].strStadium
        tvManager.text = detail[0].strManager
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_team, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (!isFavorite) {
                    presenter.insertFavorite(team.idTeam, team.strTeamBadge)
                    isFavorite = !isFavorite
                    toast(team.strTeam + " Added to Favorite")
                } else {
                    presenter.deleteFavorite(team.idTeam)
                    toast(getString(R.string.toast_message_deleted))
                    isFavorite = !isFavorite
                }
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeam.TABLE_TEAM)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to team.idTeam)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}
