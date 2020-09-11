package com.amary.codexgamer.settings

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel { SettingsViewModel(get()) }
}