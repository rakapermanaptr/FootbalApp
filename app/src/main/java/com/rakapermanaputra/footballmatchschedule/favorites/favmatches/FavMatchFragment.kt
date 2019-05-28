package com.rakapermanaputra.footballmatchschedule.favorites.favmatches


import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rakapermanaputra.footballmatchschedule.R
import com.rakapermanaputra.footballmatchschedule.adapter.MainAdapter
import com.rakapermanaputra.footballmatchschedule.model.Event
import com.rakapermanaputra.footballmatchschedule.model.repository.EventRepositoryImpl
import com.rakapermanaputra.footballmatchschedule.model.repository.LocalRepoImpl
import com.rakapermanaputra.footballmatchschedule.rest.ApiRest
import com.rakapermanaputra.footballmatchschedule.rest.ApiService
import com.rakapermanaputra.footballmatchschedule.utils.AppSchedulerProvider
import com.rakapermanaputra.footballmatchschedule.utils.invisible
import com.rakapermanaputra.footballmatchschedule.utils.visible
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class FavMatchFragment : Fragment(), FavMatchContract.View {

    private var favoriteList: MutableList<Event> = mutableListOf()
    private lateinit var presenter: FavMatchPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Set progressBar color
        progressBar.indeterminateDrawable.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN)

        val service = ApiService.getClient().create(ApiRest::class.java)
        val request = EventRepositoryImpl(service)
        val localRepoImpl = LocalRepoImpl(ctx)
        val scheduler = AppSchedulerProvider()
        presenter = FavMatchPresenter(this, request, localRepoImpl, scheduler)
        presenter.getFavoriteMatch()

        refreshFavMatch.onRefresh {
            presenter.getFavoriteMatch()
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showFavoritesList(favList: List<Event>) {
        favoriteList.clear()
        favoriteList.addAll(favList)
        refreshFavMatch.isRefreshing = false
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvFavorite.layoutManager = layoutManager
        rvFavorite.adapter = MainAdapter(favList, ctx)
    }

    override fun onResume() {
        super.onResume()
        presenter.getFavoriteMatch()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }
}
