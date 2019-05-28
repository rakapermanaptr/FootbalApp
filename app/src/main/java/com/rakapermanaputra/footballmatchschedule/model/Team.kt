package com.rakapermanaputra.footballmatchschedule.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
        @SerializedName("idTeam") var idTeam: String,
        @SerializedName("strTeam") var strTeam: String?,
        @SerializedName("strTeamShort") var strTeamShort: String?,
        @SerializedName("intFormedYear") var intFormedYear: String?,
        @SerializedName("strLeague") var strLeague: String?,
        @SerializedName("idLeague") var idLeague: String?,
        @SerializedName("strManager") var strManager: String?,
        @SerializedName("strStadium") var strStadium: String?,
        @SerializedName("strKeywords") var strKeywords: String?,
        @SerializedName("strDescriptionEN") var strDescriptionEN: String?,
        @SerializedName("strCountry") var strCountry: String?,
        @SerializedName("strTeamBadge") var strTeamBadge: String
) : Parcelable