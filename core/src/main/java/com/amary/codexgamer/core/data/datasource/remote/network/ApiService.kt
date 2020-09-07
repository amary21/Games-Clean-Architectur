package com.amary.codexgamer.core.data.datasource.remote.network

import com.amary.codexgamer.core.data.datasource.remote.response.GamesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    fun getList(
        @Query("page") page: Int,
        @Query("search")searchKey: String
    ): Single<List<GamesResponse>>
}