package pl.aifer.youtube_sandbox_m6_l3.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import pl.aifer.youtube_sandbox_m6_l3.core.network.RemoteDataSource
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.domain.paging.PagingSource
import pl.aifer.youtube_sandbox_m6_l3.domain.paging.PlaylistItemsPagingSource

internal class Repository(private val remoteDataSource: RemoteDataSource) {

    fun getPlaylists(): LiveData<PagingData<PlaylistsModel.Item>> {
        val pagingData = Pager(
            config = PagingConfig(
                initialLoadSize = 20,
                pageSize = Int.MAX_VALUE,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = { PagingSource(remoteDataSource = remoteDataSource) }
        )
        return pagingData.liveData
    }

    fun getPlaylistItems(playlistId: String): LiveData<PagingData<PlaylistsModel.Item>> {
        val pagingData = Pager(
            config = PagingConfig(
                initialLoadSize = 20,
                pageSize = Int.MAX_VALUE,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                PlaylistItemsPagingSource(
                    remoteDataSource = remoteDataSource,
                    playlistId = playlistId
                )
            }
        )
        return pagingData.liveData
    }

    suspend fun getVideo(videoId: String): Result<PlaylistsModel> {
        return remoteDataSource.getVideo(videoId)
    }
}