package pl.aifer.youtube_sandbox_m6_l3.domain.repository

import pl.aifer.youtube_sandbox_m6_l3.core.network.RemoteDataSource
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel

internal class Repository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getPlaylists(): Result<PlaylistsModel> {
        return remoteDataSource.getPlaylists()
    }

    suspend fun getPlaylistItems(playlistId: String): Result<PlaylistsModel> {
        return remoteDataSource.getPlaylistItems(playlistId)
    }
}