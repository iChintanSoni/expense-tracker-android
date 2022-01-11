package dev.chintansoni.preference

import org.koin.dsl.module

val preferenceModule = module {
    single {
        LoginPreferenceDataStore(get())
    }
}