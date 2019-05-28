package com.rakapermanaputra.footballmatchschedule.matches.nextmatches


import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.rakapermanaputra.footballmatchschedule.R
import com.rakapermanaputra.footballmatchschedule.adapter.MainAdapter
import com.rakapermanaputra.footballmatchschedule.model.Event
import com.rakapermanaputra.footballmatchschedule.model.repository.EventRepositoryImpl
import com.rakapermanaputra.footballmatchschedule.rest.ApiRest
import com.rakapermanaputra.footballmatchschedule.rest.ApiService
import com.rakapermanaputra.footballmatchschedule.utils.AppSchedulerProvider
import com.rakapermanaputra.footballmatchschedule.utils.invisible
import com.rakapermanaputra.footballmatchschedule.utils.visible
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class NextMatchFragment : Fragment(), AnkoLogger, NextMatchContract.View {

    private var nextMatchList: MutableList<Event> = mutableListOf()
    private lateinit var presenter: NextMatchPresenter
    private lateinit var leagueName: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Set progressBar color
        progressBar.indeterminateDrawable.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN)

        val service = ApiService.getClient().create(ApiRest::class.java)
        val request = EventRepositoryImpl(service)
        val scheduler = AppSchedulerProvider()
        presenter = NextMatchPresenter(this, request, scheduler)
        presenter.getNextMatch("4328")

        val spinner = resources.getStringArray(R.array.leagueArray)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinner)
        spinnerNextMatch.adapter = spinnerAdapter
        spinnerNextMatch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                parent.getChildAt(0).background = resources.getDrawable(R.drawable.card_rounded)
                leagueName = spinnerNextMatch.selectedItem.toString()
                when (leagueName) {
                    "English Premier League" -> presenter.getNextMatch("4328")
                    "Spanish La Liga" -> presenter.getNextMatch("4335")
                    "German Bundesliga" -> presenter.getNextMatch("4331")
                    "French Ligue 1" -> presenter.getNextMatch("4334")
                    "Italian Serie A" -> presenter.getNextMatch("4332")
                    "Portuguese Primeira Liga" -> presenter.getNextMatch("4344")
                    else -> presenter.getNextMatch("4328")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showScheduleList(eventList: List<Event>?) {
        nextMatchList.clear()
        if (eventList != null) {
            nextMatchList.addAll(eventList)
        }
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvNext.layoutManager = linearLayoutManager
        rvNext.adapter = MainAdapter(nextMatchList, ctx)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
    }

}
