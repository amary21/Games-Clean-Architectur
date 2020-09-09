package com.amary.codexgamer.core.data.pagination

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.amary.codexgamer.core.data.datasource.local.LocalDataSource
import com.amary.codexgamer.core.data.datasource.local.entity.GamesEntity
import com.amary.codexgamer.core.data.datasource.remote.RemoteDataSource
import com.amary.codexgamer.core.utils.DataMapper
import com.amary.codexgamer.domain.model.ResourceState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GamePageDataSource(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val search: String
) : PageKeyedDataSource<Int, GamesEntity?>() {

    val resourceState: MutableLiveData<ResourceState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GamesEntity?>
    ) {
        fetchData(1) {
            it?.let { it1 -> callback.onResult(it1, null, 2) }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GamesEntity?>) {}

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GamesEntity?>) {
        fetchData(params.key) {
            it?.let { it1 -> callback.onResult(it1, params.key + 1) }
        }
    }

    @SuppressLint("CheckResult")
    private fun fetchData(page: Int, callback: (List<GamesEntity>?) -> Unit) {
        resourceState.postValue(ResourceState.LOADING)
        remoteDataSource.getAllGames(page, search)
            .map { DataMapper.mapResponsesToEntities(it) }
            .doOnSuccess {
                if (it.isNotEmpty()) {
                    resourceState.postValue(ResourceState.LOADED)
                    localDataSource.insertGames(it)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                    callback(it)
                } else {
                    resourceState.postValue(ResourceState.ERROR)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .ignoreElement()
            .subscribe({}, { error ->
                callback(null)
                getJobErrorHandler(error)
                resourceState.postValue(ResourceState.ERROR)
            })
    }

    private fun getJobErrorHandler(error: Throwable) {
        Log.e(TAG, error.message.toString())
    }

    companion object {
        private const val TAG = "GamePageDataSource"
    }

}