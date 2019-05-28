package com.rakapermanaputra.footballmatchschedule.detail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.rakapermanaputra.footballmatchschedule.R
import com.rakapermanaputra.footballmatchschedule.R.drawable.*
import com.rakapermanaputra.footballmatchschedule.R.id.add_to_favorite
import com.rakapermanaputra.footballmatchschedule.R.menu.menu_detail
import com.rakapermanaputra.footballmatchschedule.db.Alarm
import com.rakapermanaputra.footballmatchschedule.db.FavoriteEvent
import com.rakapermanaputra.footballmatchschedule.db.database
import com.rakapermanaputra.footballmatchschedule.model.Event
import com.rakapermanaputra.footballmatchschedule.model.Team
import com.rakapermanaputra.footballmatchschedule.model.repository.EventRepositoryImpl
import com.rakapermanaputra.footballmatchschedule.model.repository.LocalRepoImpl
import com.rakapermanaputra.footballmatchschedule.rest.ApiRest
import com.rakapermanaputra.footballmatchschedule.rest.ApiService
import com.rakapermanaputra.footballmatchschedule.utils.Date
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailActivity : AppCompatActivity(), AnkoLogger, DetailContract.View {

    private lateinit var presenter: DetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private var isAlarm: Boolean = false
    private lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        event = intent.getParcelableExtra("event")
        supportActionBar?.title = event.strEvent

        val service = ApiService.getClient().create(ApiRest::class.java)
        val request = EventRepositoryImpl(service)
        val localRepo = LocalRepoImpl(applicationContext)
        presenter = DetailPresenter(this, request, localRepo)

        presenter.getHomeTeamBadge(event.idHomeTeam)
        presenter.getAwayTeamBadge(event.idAwayTeam)
        presenter.getEventDetail(event.idEvent)

        favoriteState()
        alarmState()
    }

    override fun setDetailEvent(detail: List<Event>) {
        tvSchedule.text = detail[0].dateEvent?.let { Date.formatDate(it) }

        tvHomeScore.text = detail[0].intHomeScore
        tvAwayScore.text = detail[0].intAwayScore

        tvHomeName.text = detail[0].strHomeTeam
        tvAwayName.text = detail[0].strAwayTeam

        tvHomeScorer.text = detail[0].strHomeGoalDetails
        tvAwayScorer.text = detail[0].strAwayGoalDetails

        tvHomeShots.text = detail[0].intHomeShots
        tvAwayShots.text = detail[0].intAwayShots

        tvHomeLineupsGk.text = detail[0].strHomeLineupGoalkeeper
        tvAwayLineupsGk.text = detail[0].strAwayLineupGoalkeeper

        tvHomeLineupsDef.text = detail[0].strHomeLineupDefense
        tvAwayLineupsDef.text = detail[0].strAwayLineupDefense

        tvHomeLineupsMid.text = detail[0].strHomeLineupMidfield
        tvAwayLineupsMid.text = detail[0].strAwayLineupMidfield

        tvHomeLineupsFwd.text = detail[0].strHomeLineupForward
        tvAwayLineupsFwd.text = detail[0].strAwayLineupForward

        tvHomeSubtitutes.text = detail[0].strHomeLineupSubstitutes
        tvAwaySubtitutes.text = detail[0].strAwayLineupSubstitutes

        Log.v("data", " idHome : " + detail[0].idHomeTeam + ",idAway : " + detail[0].idAwayTeam)

        if (detail[0].intHomeScore.equals(null) && detail[0].intAwayScore.equals(null)) {
            tvHomeScore.text = "0"
            tvAwayScore.text = "0"
        }
    }


    override fun showHomeTeamBadge(team: Team) {
        Glide.with(applicationContext)
                .load(team.strTeamBadge)
                .into(imgHomeTeam)
    }

    override fun showAwayTeamBadge(team: Team) {
        Glide.with(applicationContext)
                .load(team.strTeamBadge)
                .into(imgAwayTeam)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(menu_detail, menu)
        menuItem = menu
        setFavorite()
        setAlarm()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (!isFavorite) {
                    presenter.insertFavorite(
                            event.idEvent, event.idHomeTeam, event.idAwayTeam)
                    toast(getString(R.string.toast_added_message))
                    isFavorite = !isFavorite
                } else {
                    presenter.deleteFavorite(event.idEvent)
                    toast(getString(R.string.toast_message_deleted))
                    isFavorite = !isFavorite
                }
                setFavorite()
                true
            }
            R.id.add_to_alarm -> {
                if (!isAlarm) {
                    presenter.insertAlarm(event.idEvent)
                    toast(getString(R.string.upcoming_feature))
                    isAlarm = !isAlarm
                } else {
                    presenter.deleteAlarm(event.idEvent)
                    toast("Upcoming Feature")
                    isAlarm = !isAlarm
                }
                setAlarm()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setFavoriteState(favList: List<FavoriteEvent>) {
        if (!favList.isEmpty()) isFavorite = true
    }

    override fun setAlarmState(favList: List<FavoriteEvent>) {
        if (!favList.isEmpty()) isAlarm = true
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun setAlarm() {
        if (isAlarm)
            menuItem?.getItem(1)?.icon = ContextCompat.getDrawable(this, ic_notifications_black_24dp)
        else
            menuItem?.getItem(1)?.icon = ContextCompat.getDrawable(this, ic_notifications_none_black_24dp)
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteEvent.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to event.idEvent)
            val favorite = result.parseList(classParser<FavoriteEvent>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun alarmState() {
        database.use {
            val result = select(Alarm.TABLE_ALARM)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to event.idEvent)
            val favorite = result.parseList(classParser<Alarm>())
            if (!favorite.isEmpty()) isAlarm = true
        }
    }

}
