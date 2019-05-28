package com.rakapermanaputra.footballmatchschedule.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.rakapermanaputra.footballmatchschedule.R
import com.rakapermanaputra.footballmatchschedule.model.Player
import com.rakapermanaputra.footballmatchschedule.teams.players.playerdetail.PlayerActivity
import kotlinx.android.synthetic.main.item_players.view.*
import org.jetbrains.anko.startActivity

class PlayersAdapter(private val players: List<Player>, private val context: Context)
    : RecyclerView.Adapter<PlayersAdapter.LeagueHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): LeagueHolder {
        return LeagueHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_players, viewGroup, false))
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: LeagueHolder, position: Int) = holder.bindItem(players[position])


    class LeagueHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgPlayer = itemView.imgThumbPlayer
        private val tvPlayerName = itemView.tvPlayerName
        private val tvPlayerPosition = itemView.tvPosition

        fun bindItem(player: Player) {
            Glide.with(itemView)
                    .load(player.strThumb)
                    .into(imgPlayer)

            tvPlayerName.text = player.strPlayer
            tvPlayerPosition.text = player.strPosition

            itemView.setOnClickListener {
                itemView.context.startActivity<PlayerActivity>("idPlayer" to player)
            }
        }
    }
}