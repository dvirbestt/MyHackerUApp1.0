package com.example.myhackeruapp10.dataClasses

import com.google.gson.annotations.SerializedName

data class PhotoApiResponse(
    @SerializedName("hits")val hits: List<Hits>
) {
}