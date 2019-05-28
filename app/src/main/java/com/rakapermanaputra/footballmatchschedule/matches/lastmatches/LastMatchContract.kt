package com.rakapermanaputra.footballmatchschedule.matches.lastmatches

import com.rakapermanaputra.footballmatchschedule.model.Event

interface LastMatchContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showScheduleList(eventList: List<Event>)
    }

    interface Presenter {
        fun getLastMatch(idLeague: String)
        fun onDestroy()
    }
}