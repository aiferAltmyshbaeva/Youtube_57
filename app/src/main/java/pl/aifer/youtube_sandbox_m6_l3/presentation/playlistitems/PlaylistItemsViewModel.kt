package pl.aifer.youtube_sandbox_m6_l3.presentation.playlistitems

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseViewModel
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.domain.repository.Repository

internal class PlaylistItemsViewModel(private val repository: Repository) : BaseViewModel() {
    fun getPagingPlaylistItems(playlistId: String): LiveData<PagingData<PlaylistsModel.Item>> {
        return repository.getPlaylistItems(playlistId)
    }
}