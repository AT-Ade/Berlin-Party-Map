package com.example.berlinpartymap.di

import androidx.room.Room
import com.example.berlinpartymap.data.local.EventDatabase
import com.example.berlinpartymap.data.remote.api.APIService
import com.example.berlinpartymap.data.repository.EventRepository
import com.example.berlinpartymap.data.repository.EventRepositoryImpl
import com.example.berlinpartymap.ui.map.EventViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val BASE_URL = "http://10.0.2.2:8080/"
// Modul
val appModule = module {

    // ------------- Database & DAO -------------
    single {
        Room.databaseBuilder(androidContext(), EventDatabase::class.java, "event_database")
            .build()
    }
    single { get<EventDatabase>().eventDao() }


    // ------------- Retrofit & API Service -------------
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .baseUrl(BASE_URL)
            .build()
    }
    single<APIService> { get<Retrofit>().create(APIService::class.java) }


    // ------------- Repository -------------

    single<EventRepository> { EventRepositoryImpl(get(), get()) }


    // ------------- ViewModel -------------

        viewModelOf(::EventViewModel)


}
