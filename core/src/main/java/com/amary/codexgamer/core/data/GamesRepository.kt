package com.amary.codexgamer.core.data

import android.annotation.SuppressLint
import android.arch.convert.toFlowable
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.amary.codexgamer.core.data.datasource.local.LocalDataSource
import com.amary.codexgamer.core.data.datasource.remote.RemoteDataSource
import com.amary.codexgamer.core.data.pagination.GamePageDataSource
import com.amary.codexgamer.core.data.pagination.GamePageDataSourceFactory
import com.amary.codexgamer.core.utils.DataMapper
import com.amary.codexgamer.domain.model.Favorite
import com.amary.codexgamer.domain.model.Games
import com.amary.codexgamer.domain.model.GamesFavorite
import com.amary.codexgamer.domain.model.ResourceState
import com.amary.codexgamer.domain.repository.IGamesRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class GamesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IGamesRepository {

    private lateinit var gamePageDataSourceFactory: GamePageDataSourceFactory

    override fun getAllGames(searchKey: String): Flowable<PagedList<Games>> {
        gamePageDataSourceFactory =
            GamePageDataSourceFactory(remoteDataSource, localDataSource, searchKey)
        val dataSource = gamePageDataSourceFactory.map { DataMapper.mapEntityToDomain(it) }
        return RxPagedListBuilder(
            dataSource,
            GamePageDataSourceFactory.pagedListConfig()
        ).buildObservable().toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun getResourceState(): Flowable<ResourceState> {
        val transform = Transformations.switchMap(
            gamePageDataSourceFactory.gameLivePageDataSource,
            GamePageDataSource::resourceState
        )
        return transform.toFlowable()
    }

    override fun getAllFavoriteGames(): Flowable<List<GamesFavorite>> {
        return localDataSource.getAllFavoriteGames().map {
            DataMapper.mapListFavoriteEntityToListFavoriteDomain(it)
        }
    }

    override fun insertFavorite(favorite: Favorite) {
        val input = DataMapper.mapFavoriteDomainToFavoriteEntity(favorite)
        localDataSource.insertFavorite(input)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    @SuppressLint("CheckResult")
    override fun isFavorite(gamesId: Int): Flowable<Int> {
        val result = PublishSubject.create<Int>()
        localDataSource.isFavorite(gamesId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                result.onNext(it)
            }

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun deleteFavorite(gamesId: Int) {
        localDataSource.deleteFavorite(gamesId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}