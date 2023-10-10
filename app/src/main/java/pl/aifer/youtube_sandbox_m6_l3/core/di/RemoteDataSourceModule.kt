package pl.aifer.youtube_sandbox_m6_l3.core.di

import org.koin.dsl.module
import pl.aifer.youtube_sandbox_m6_l3.core.network.RemoteDataSource

val remoteDataSourceModule = module {
    single { RemoteDataSource(get()) }
}