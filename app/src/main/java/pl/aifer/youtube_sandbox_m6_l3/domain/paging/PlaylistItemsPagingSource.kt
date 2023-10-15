package pl.aifer.youtube_sandbox_m6_l3.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import pl.aifer.youtube_sandbox_m6_l3.core.network.RemoteDataSource
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel

internal class PlaylistItemsPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val playlistId: String
) : PagingSource<String, PlaylistsModel.Item>() {
    override fun getRefreshKey(state: PagingState<String, PlaylistsModel.Item>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PlaylistsModel.Item> {
        try {
            val nextPageToken = params.key ?: ""
            val response = remoteDataSource.getPlaylistItems(playlistId, nextPageToken)
            val items = mutableListOf<PlaylistsModel.Item>()

            when {
                response.isSuccess -> response.onSuccess {
                    items.addAll(it.items)
                }

                response.isFailure -> response.onFailure {

                }
            }

            var nextKey = ""

            if (response.isSuccess) response.onSuccess { nextKey = it.nextPageToken }
            return LoadResult.Page(
                data = items,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}