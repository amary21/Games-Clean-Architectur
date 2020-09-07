package com.amary.codexgamer.core.data.datasource.remote

import com.amary.codexgamer.core.data.datasource.remote.network.ApiService


class RemoteDataSource(private val apiService: ApiService) {

    fun getAllGames(page: Int, searchKey: String) = apiService.getList(page, searchKey)

}