package com.amary.codexgamer.di

import com.amary.codexgamer.domain.usecase.GamesInteractor
import com.amary.codexgamer.domain.usecase.GamesUseCase
import com.amary.codexgamer.utils.Preference
import org.koin.dsl.module

val useCaseModule = module {
    factory<GamesUseCase> {
        GamesInteractor(
            get()
        )
    }
}

val preferenceModule = module {
    factory {
        Preference(get())
    }
}