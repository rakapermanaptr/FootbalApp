package com.rakapermanaputra.footballmatchschedule.favorites.favmatches

import com.rakapermanaputra.footballmatchschedule.model.Event

interface FavMatchContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showFavoritesList(favList: List<Event>)
    }

    interface Presenter {
        fun getFavoriteMatch()
        fun onDestroy()
    }
}