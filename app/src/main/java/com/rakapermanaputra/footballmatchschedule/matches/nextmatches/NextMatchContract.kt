package com.rakapermanaputra.footballmatchschedule.matches.nextmatches

import com.rakapermanaputra.footballmatchschedule.model.Event

interface NextMatchContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showScheduleList(eventList: List<Event>?)
    }

    interface Presenter {
        fun getNextMatch(idLeague: String)
        fun onDestroy()
    }
}