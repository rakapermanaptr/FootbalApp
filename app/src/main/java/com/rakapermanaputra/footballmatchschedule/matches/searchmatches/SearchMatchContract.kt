package com.rakapermanaputra.footballmatchschedule.matches.searchmatches

import com.rakapermanaputra.footballmatchschedule.model.Event

interface SearchMatchContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showMatches(matchList: List<Event>)
    }

    interface Presenter {
        fun searchMatch(query: String?)
        fun onDestroy()
    }
}