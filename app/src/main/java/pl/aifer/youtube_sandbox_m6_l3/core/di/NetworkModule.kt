package pl.aifer.youtube_sandbox_m6_l3.core.di

import org.koin.dsl.module
import pl.aifer.youtube_sandbox_m6_l3.core.network.provideApiService
import pl.aifer.youtube_sandbox_m6_l3.core.network.provideInterceptor
import pl.aifer.youtube_sandbox_m6_l3.core.network.provideOkHttpClient
import pl.aifer.youtube_sandbox_m6_l3.core.network.provideRetrofit

val networkModule = module {
    single { provideInterceptor() }
    single { provideOkHttpClient(get()) }
    factory { provideRetrofit(get()) }
    single { provideApiService(get()) }
}