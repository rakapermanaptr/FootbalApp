package com.rakapermanaputra.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

class SearchingMatches(
        @SerializedName("event")
        var events: List<Event>
)