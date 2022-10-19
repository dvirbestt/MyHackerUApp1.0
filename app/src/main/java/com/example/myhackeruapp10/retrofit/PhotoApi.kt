package com.example.myhackeruapp10.retrofit

import com.example.myhackeruapp10.dataClasses.PhotoApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApi {

    @GET("?key=30411211-eef8699e508b60d60f0594130&")
    suspend fun getPhotos(@Query("q")query: String): Response<PhotoApiResponse>

}