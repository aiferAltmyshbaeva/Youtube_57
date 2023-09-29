package pl.aifer.youtube_sandbox_m6_l3.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.aifer.youtube_sandbox_m6_l3.BuildConfig
import pl.aifer.youtube_sandbox_m6_l3.core.network.ApiService
import pl.aifer.youtube_sandbox_m6_l3.core.utils.Resource
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.utils.Constants

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class Repository(private val apiService: ApiService) {

    fun getPlaylists(): LiveData<Resource<PlaylistsModel>> {
        val resourceData = MutableLiveData<Resource<PlaylistsModel>>()
        apiService.getPlaylists(
            part = Constants.PART,
            channelId = Constants.CHANNEL_ID,
            apiKey = BuildConfig.API_KEY,
            maxResults = 10,
        ).enqueue(
            object : Callback<Resource<PlaylistsModel>> {
                override fun onResponse(
                    call: Call<Resource<PlaylistsModel>>,
                    response: Response<Resource<PlaylistsModel>>
                ) {
                    if (response.isSuccessful) {
                        resourceData.value = response.body()

                    } else {
                        resourceData.value = Resource.error(
                            msg = response.message().toString(),
                            data = null,
                            code = 430
                        )
                    }
                }

                override fun onFailure(call: Call<Resource<PlaylistsModel>>, t: Throwable) {
                    resourceData.value = Resource.error(
                        msg = t.message.toString(),
                        data = null,
                        code = 429
                    )
                }

            }
        )
        return resourceData
    }
}