package com.soulkey.fspicker.di

import androidx.room.Room
import com.soulkey.fspicker.config.Constant.BASE_URL
import com.soulkey.fspicker.config.Constant.CONNECT_TIMEOUT
import com.soulkey.fspicker.config.Constant.READ_TIMEOUT
import com.soulkey.fspicker.config.Constant.WRITE_TIMEOUT
import com.soulkey.fspicker.data.LocationRepository
import com.soulkey.fspicker.data.LocationRepositoryImpl
import com.soulkey.fspicker.db.AppDatabase
import com.soulkey.fspicker.lib.FoursquareAPI
import com.soulkey.fspicker.lib.FoursquareClient
import com.soulkey.fspicker.ui.main.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

val AppModule = module {

    //Room
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
    single { get<AppDatabase>().locationDao() }
    single<LocationRepository> {LocationRepositoryImpl(get())}

    //OkHttpClient
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single { FoursquareClient(get()) }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(FoursquareAPI::class.java)
    }

    viewModel { MainViewModel(get(), get()) }

}