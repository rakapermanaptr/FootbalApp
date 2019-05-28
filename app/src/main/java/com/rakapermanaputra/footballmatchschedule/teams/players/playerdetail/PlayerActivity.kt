package com.rakapermanaputra.footballmatchschedule.teams.players.playerdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.rakapermanaputra.footballmatchschedule.R
import com.rakapermanaputra.footballmatchschedule.model.Player
import com.rakapermanaputra.footballmatchschedule.model.PlayerDetail
import com.rakapermanaputra.footballmatchschedule.model.repository.PlayerRepositoryImpl
import com.rakapermanaputra.footballmatchschedule.rest.ApiRest
import com.rakapermanaputra.footballmatchschedule.rest.ApiService
import com.rakapermanaputra.footballmatchschedule.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity(), PlayerContract.View {

    private lateinit var presenter: PlayerPresenter
    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        player = intent.getParcelableExtra("idPlayer")

        val service = ApiService.getClient().create(ApiRest::class.java)
        val request = PlayerRepositoryImpl(service)
        val scheduler = AppSchedulerProvider()
        presenter = PlayerPresenter(this, request, scheduler)
        presenter.getPlayerDetail(player.idPlayer)

    }

    override fun setPlayerDetail(detail: List<PlayerDetail>) {
        tvPlayerName.text = detail[0].strPlayer
        tvPlayerTeam.text = detail[0].strTeam
        tvPlayerCountry.text = detail[0].strNationality
        tvPlayerPosistion.text = detail[0].strPosition
        tvPlayerWeight.text = detail[0].strWeight
        tvPlayerDate.text = detail[0].dateBorn
        tvPlayerFrom.text = detail[0].strBirthLocation
        tvPlayerDesc.text = detail[0].strDescriptionEN

        Log.v("data ", " weight player : " + detail[0].strWeight)

        if (detail[0].strWeight.equals("")) {
            tvPlayerWeight.text = "-"
        }

        Glide.with(applicationContext)
                .load(detail[0].strThumb)
                .into(imgProfilePlayer)
    }

}
