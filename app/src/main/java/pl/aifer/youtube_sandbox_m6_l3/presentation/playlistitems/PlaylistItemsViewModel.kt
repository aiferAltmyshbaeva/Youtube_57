package pl.aifer.youtube_sandbox_m6_l3.presentation.playlistitems

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseViewModel
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.domain.repository.Repository

internal class PlaylistItemsViewModel(private val repository: Repository) : BaseViewModel() {
    private val _playlistItems = MutableLiveData<PlaylistsModel>()
    val playlistItems: LiveData<PlaylistsModel> get() = _playlistItems

    fun getPlaylistItems(playlistId: String) = doOperation(
        operation = { repository.getPlaylistItems(playlistId) },
        success = { _playlistItems.postValue(it) }
    )
}