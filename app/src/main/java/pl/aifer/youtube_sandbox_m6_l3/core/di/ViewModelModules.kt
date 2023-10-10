package pl.aifer.youtube_sandbox_m6_l3.core.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.aifer.youtube_sandbox_m6_l3.presentation.playlistitems.PlaylistItemsViewModel
import pl.aifer.youtube_sandbox_m6_l3.presentation.playlists.PlaylistsViewModel
import pl.aifer.youtube_sandbox_m6_l3.presentation.video.VideoViewModel

val viewModelModules = module {
    viewModel { PlaylistsViewModel(get()) }
    viewModel { PlaylistItemsViewModel(get()) }
    viewModel { VideoViewModel(get()) }
}