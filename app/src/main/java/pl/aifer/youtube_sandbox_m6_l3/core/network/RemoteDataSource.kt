package pl.aifer.youtube_sandbox_m6_l3.core.network

import pl.aifer.youtube_sandbox_m6_l3.BuildConfig
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseDataSource
import pl.aifer.youtube_sandbox_m6_l3.core.utils.Resource
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.utils.Constants

internal class RemoteDataSource(private val apiService: ApiService) : BaseDataSource() {
    suspend fun getPlaylists(): Resource<PlaylistsModel> {
        return getResult {
            apiService.getPlaylists(
                part = Constants.PART,
                channelId = Constants.CHANNEL_ID,
                apiKey = BuildConfig.API_KEY,
                maxResults = 10,
            )
        }
    }
}