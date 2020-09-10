package com.amary.codexgamer.di

import com.amary.codexgamer.domain.usecase.GamesInteractor
import com.amary.codexgamer.domain.usecase.GamesUseCase
import com.amary.codexgamer.ui.detail.DetailViewModel
import com.amary.codexgamer.ui.favorite.FavoriteViewModel
import com.amary.codexgamer.ui.home.HomeViewModel
import com.amary.codexgamer.utils.Preference
import com.amary.codexgamer.ui.settings.SettingsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GamesUseCase> {
        GamesInteractor(
            get()
        )
    }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}

val preferenceModule = module {
    factory {
        Preference(get())
    }
}