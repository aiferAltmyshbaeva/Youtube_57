package pl.aifer.youtube_sandbox_m6_l3.presentation.playlists

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseViewModel
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.domain.repository.Repository

internal class PlaylistsViewModel(private val repository: Repository) : BaseViewModel() {

    fun getPagingPlaylists(): LiveData<PagingData<PlaylistsModel.Item>> {
        return repository.getPlaylists()
    }

}