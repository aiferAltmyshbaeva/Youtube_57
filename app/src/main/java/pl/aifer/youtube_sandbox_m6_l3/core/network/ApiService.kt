package pl.aifer.youtube_sandbox_m6_l3.core.network

import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiService {
    @GET("playlists")
    suspend fun getPlaylists(
        @Query("part")
        part: String,
        @Query("channelId")
        channelId: String,
        @Query("key")
        apiKey: String,
        @Query("maxResults")
        maxResults: Int
    ): Response<PlaylistsModel>
}