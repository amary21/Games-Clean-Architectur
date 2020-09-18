package com.amary.codexgamer.core.di

import androidx.room.Room
import com.amary.codexgamer.core.BuildConfig.*
import com.amary.codexgamer.core.data.GamesRepository
import com.amary.codexgamer.core.data.datasource.local.LocalDataSource
import com.amary.codexgamer.core.data.datasource.local.room.GamesDatabase
import com.amary.codexgamer.core.data.datasource.remote.RemoteDataSource
import com.amary.codexgamer.core.data.datasource.remote.network.ApiDeserializer
import com.amary.codexgamer.core.data.datasource.remote.network.ApiService
import com.amary.codexgamer.core.data.datasource.remote.response.GamesResponse
import com.amary.codexgamer.domain.repository.IGamesRepository
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<GamesDatabase>().gamesDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("__android_database__".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            GamesDatabase::class.java, "Games.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        GsonBuilder().registerTypeAdapter(
            object : TypeToken<List<@JvmSuppressWildcards GamesResponse>>() {}.type,
            ApiDeserializer<GamesResponse>()
        )
            .setLenient()
            .create()
    }
    single {

        val hostName = HOST_NAME
        val pins1 = CERTIFICATE_1
        val pins2 = CERTIFICATE_2
        val pins3 = CERTIFICATE_3
        val certificate = CertificatePinner.Builder()
            .add(hostName, pins1)
            .add(hostName, pins2)
            .add(hostName, pins3)
            .build()

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificate)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IGamesRepository> { GamesRepository(get(), get()) }
}