package com.rakapermanaputra.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class PlayerDetailResponse(
        @SerializedName("players") var player: List<PlayerDetail>
)