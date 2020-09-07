package com.amary.codexgamer.core.data.pagination

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.amary.codexgamer.core.data.datasource.local.LocalDataSource
import com.amary.codexgamer.core.data.datasource.local.entity.GamesEntity
import com.amary.codexgamer.core.data.datasource.remote.RemoteDataSource

class GamePageDataSourceFactory(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val search: String
) : DataSource.Factory<Int, GamesEntity>() {

    companion object {
        private const val PAGE_SIZE = 20
        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }

    override fun create(): DataSource<Int, GamesEntity> {
        return GamePageDataSource(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            search = search
        )
    }


}