package pl.aifer.youtube_sandbox_m6_l3.core.network

import pl.aifer.youtube_sandbox_m6_l3.core.utils.Resource
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiService {
    @GET("playlists")
    fun getPlaylists(
        @Query("part")
        part: String,
        @Query("key")
        apiKey: String,
        @Query("channelId")
        channelId: String,
        @Query("maxResults")
        maxResults: Int
    ): Call<Resource<PlaylistsModel>>
}