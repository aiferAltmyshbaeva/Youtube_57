package pl.aifer.youtube_sandbox_m6_l3.data.model

import java.io.Serializable

internal data class PlaylistsModel(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo
) {
    internal data class Item(
        val contentDetails: ContentDetails,
        val etag: String,
        val id: String,
        val kind: String,
        val snippet: Snippet
    ) : Serializable {
        internal data class ContentDetails(
            val videoId: String,
            val itemCount: Int
        ) :Serializable

        internal data class Snippet(
            val channelId: String,
            val channelTitle: String,
            val description: String,
            val localized: Localized,
            val publishedAt: String,
            val thumbnails: Thumbnails,
            val title: String
        ) {
            internal data class Localized(
                val description: String,
                val title: String
            )

            internal data class Thumbnails(
                val default: Default,
                val high: High,
                val maxres: Maxres,
                val medium: Medium,
                val standard: Standard
            ) {
                internal data class Default(
                    val height: Int,
                    val url: String,
                    val width: Int
                )

                internal data class High(
                    val height: Int,
                    val url: String,
                    val width: Int
                )

                internal data class Maxres(
                    val height: Int,
                    val url: String,
                    val width: Int
                )

                internal data class Medium(
                    val height: Int,
                    val url: String,
                    val width: Int
                )

                internal data class Standard(
                    val height: Int,
                    val url: String,
                    val width: Int
                )
            }
        }
    }

    internal data class PageInfo(
        val resultsPerPage: Int,
        val totalResults: Int
    )
}