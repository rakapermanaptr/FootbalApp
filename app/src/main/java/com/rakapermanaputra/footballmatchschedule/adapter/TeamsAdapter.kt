package com.rakapermanaputra.footballmatchschedule.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.rakapermanaputra.footballmatchschedule.R
import com.rakapermanaputra.footballmatchschedule.model.Team
import com.rakapermanaputra.footballmatchschedule.teams.teamdetail.TeamDetailActivity
import kotlinx.android.synthetic.main.item_league.view.*
import org.jetbrains.anko.startActivity

class TeamsAdapter(private val teams: List<Team>, private val context: Context)
    : RecyclerView.Adapter<TeamsAdapter.LeagueHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): LeagueHolder {
        return LeagueHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_teams, viewGroup, false))
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: LeagueHolder, position: Int) = holder.bindItem(teams[position])


    class LeagueHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgLeague = itemView.imgLogoLeagues

        fun bindItem(team: Team) {
            Glide.with(itemView)
                    .load(team.strTeamBadge)
                    .into(imgLeague)

            itemView.setOnClickListener {
                itemView.context.startActivity<TeamDetailActivity>("teams" to team)
            }
            Log.v("data favorite : ", team.idTeam)
        }
    }
}