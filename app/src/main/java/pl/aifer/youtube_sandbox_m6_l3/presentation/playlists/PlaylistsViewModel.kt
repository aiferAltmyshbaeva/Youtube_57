package pl.aifer.youtube_sandbox_m6_l3.presentation.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseViewModel
import pl.aifer.youtube_sandbox_m6_l3.core.utils.Resource
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.domain.repository.Repository

internal class PlaylistsViewModel(private val repository: Repository) : BaseViewModel() {

    fun getPlaylists(): LiveData<Resource<PlaylistsModel>> {
        return repository.getPlaylists()
            ?: MutableLiveData(Resource.error("Loading playlists failed", null, null))
    }
}