package com.amary.codexgamer.core.data

import android.annotation.SuppressLint
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.amary.codexgamer.core.data.datasource.local.LocalDataSource
import com.amary.codexgamer.core.data.datasource.remote.RemoteDataSource
import com.amary.codexgamer.core.data.pagination.GamePageDataSourceFactory
import com.amary.codexgamer.core.domain.model.Favorite
import com.amary.codexgamer.core.domain.model.Games
import com.amary.codexgamer.core.domain.model.GamesFavorite
import com.amary.codexgamer.core.domain.repository.IGamesRepository
import com.amary.codexgamer.core.utils.DataMapper
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class GamesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IGamesRepository{

    private val mCompositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getAllGames(searchKey: String): Flowable<Resource<PagedList<Games>>> {
        val result = PublishSubject.create<Resource<PagedList<Games>>>()
        try {
            result.first(Resource.Loading(null))
            val dataSource = GamePageDataSourceFactory(remoteDataSource, localDataSource, searchKey)
                .map { DataMapper.mapEntityToDomain(it) }

            val dispose =RxPagedListBuilder(dataSource, GamePageDataSourceFactory.pagedListConfig())
                .buildObservable().toFlowable(BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe({
                    result.onNext(Resource.Loading(null))
                    if (it != null) {
                        result.onNext(Resource.Success(it))
                    } else {
                        result.onNext(Resource.Error("Error", null))
                    }
                }, {
                    result.onNext(Resource.Error(it.message.toString(), null))
                })
            mCompositeDisposable.add(dispose)
        }catch (e: Exception){
            result.onNext(Resource.Error(e.message.toString(), null))
        }
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    override fun getAllFavoriteGames(): Flowable<Resource<PagedList<GamesFavorite>>> {
        val result = PublishSubject.create<Resource<PagedList<GamesFavorite>>>()
        try {
            result.first(Resource.Loading(null))
            val dataSource = localDataSource.getAllFavoriteGames().map {
                DataMapper.mapListFavoriteEntityToListFavoriteDomain(it)
            }
            val dispose = RxPagedListBuilder(dataSource, GamePageDataSourceFactory.pagedListConfig())
                .buildObservable().toFlowable(BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe({
                    result.first(Resource.Loading(null))
                    if (it != null){
                        result.onNext(Resource.Success(it))
                    } else {
                        result.onNext(Resource.Error("Error", null))
                    }
                },{
                    result.onNext(Resource.Error(it.message.toString(), null))
                })
            mCompositeDisposable.add(dispose)
        } catch (e: Exception){
            result.onNext(Resource.Error(e.message.toString(), null))
        }

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun insertFavorite(favorite: Favorite) {
        val input = DataMapper.mapFavoriteDomainToFavoriteEnitity(favorite)
        localDataSource.insertFavorite(input)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    @SuppressLint("CheckResult")
    override fun isFavorite(gamesId: Int) : Flowable<Int> {
        val result = PublishSubject.create<Int>()
        localDataSource.isFavorite(gamesId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                result.onNext(it)
            }
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun deleteFavorite(gamesId: Int){
        localDataSource.deleteFavorite(gamesId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}