package com.rakapermanaputra.footballmatchschedule.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rakapermanaputra.footballmatchschedule.R
import com.rakapermanaputra.footballmatchschedule.detail.DetailActivity
import com.rakapermanaputra.footballmatchschedule.model.Event
import com.rakapermanaputra.footballmatchschedule.utils.Date
import com.rakapermanaputra.footballmatchschedule.utils.invisible
import kotlinx.android.synthetic.main.item_schedule.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity


class MainAdapter(private val eventList: List<Event>, private val context: Context)
    : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false))
    }

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindItem(eventList[position])

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), AnkoLogger {
        fun bindItem(event: Event) {

            Log.v("Event", event.idEvent + " : " + event.strHomeTeam + ", idHomeTeam : " + event.idHomeTeam + " vs " + event.strAwayTeam + " idAwayTeam : " + event.idAwayTeam)

            itemView.tvSchedule.text = event.dateEvent?.let { Date.formatDate(it) }
            itemView.tvHomeName.text = event.strHomeTeam
            itemView.tvHomeScore.text = event.intHomeScore
            itemView.tvAwayScore.text = event.intAwayScore
            itemView.tvAwayName.text = event.strAwayTeam

            if (event.intHomeScore.equals(null) && event.intAwayScore.equals(null)) {
                itemView.tvHomeScore.text = "0"
                itemView.tvAwayScore.text = "0"
            }

            if (event.intHomeScore.equals(null)) {
                itemView.tvFinal.text = " "
            }

            if (event.strAwayLineupDefense.equals(null)) {
                itemView.tvFinal.visibility = View.GONE
            }

            itemView.setOnClickListener {
                itemView.context.startActivity<DetailActivity>("event" to event)
            }

        }
    }
}