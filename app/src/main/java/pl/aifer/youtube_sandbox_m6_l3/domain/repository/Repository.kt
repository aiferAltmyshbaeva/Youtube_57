package pl.aifer.youtube_sandbox_m6_l3.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import pl.aifer.youtube_sandbox_m6_l3.core.network.RemoteDataSource
import pl.aifer.youtube_sandbox_m6_l3.core.utils.Resource
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel

internal class Repository(private val remoteDataSource: RemoteDataSource) {

    fun getPlaylists(): LiveData<Resource<PlaylistsModel>> {
        return liveData {
            emit(Resource.loading())
            emit(remoteDataSource.getPlaylists())
        }
    }
}