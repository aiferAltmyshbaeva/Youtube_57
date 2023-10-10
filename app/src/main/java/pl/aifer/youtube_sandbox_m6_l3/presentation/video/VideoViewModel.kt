package pl.aifer.youtube_sandbox_m6_l3.presentation.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseViewModel
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.domain.repository.Repository

internal class VideoViewModel(private val repository: Repository) : BaseViewModel() {

    private val _video = MutableLiveData<PlaylistsModel>()
    val video: LiveData<PlaylistsModel> get() = _video

    fun getVideo(videoId: String) = doOperation(
        operation = { repository.getVideo(videoId) },
        success = { _video.postValue(it) }
    )
}
