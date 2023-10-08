package pl.aifer.youtube_sandbox_m6_l3.presentation.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseViewModel
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.domain.repository.Repository

internal class PlaylistsViewModel(private val repository: Repository) : BaseViewModel() {

    private val _playlists = MutableLiveData<PlaylistsModel>()
    val playlists: LiveData<PlaylistsModel> get() = _playlists

    fun getPlaylists() = doOperation(
        operation = { repository.getPlaylists() },
        success = { _playlists.postValue(it) }
    )
}