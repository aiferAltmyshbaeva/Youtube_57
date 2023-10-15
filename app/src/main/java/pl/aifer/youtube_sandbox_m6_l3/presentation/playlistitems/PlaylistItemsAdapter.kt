package pl.aifer.youtube_sandbox_m6_l3.presentation.playlistitems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.databinding.ItemPlaylistItemsBinding

internal class PlaylistItemsAdapter(
    diffUtilCallback: DiffUtil.ItemCallback<PlaylistsModel.Item>,
    private val onCLickItem: (item: PlaylistsModel.Item) -> Unit
) :
    PagingDataAdapter<PlaylistsModel.Item, PlaylistItemsAdapter.PlaylistItemsViewHolder>(
        diffUtilCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaylistItemsAdapter.PlaylistItemsViewHolder {
        return PlaylistItemsViewHolder(
            ItemPlaylistItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: PlaylistItemsAdapter.PlaylistItemsViewHolder, position: Int
    ) {
        val newPosition = getItem(position)
        newPosition?.let {
            holder.bind(it)
        }
    }

    inner class PlaylistItemsViewHolder(private val binding: ItemPlaylistItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: PlaylistsModel.Item) {
            if (!playlist.snippet.thumbnails.default.url.isNullOrEmpty() && !playlist.snippet.title.isNullOrEmpty()) {
                binding.tvVideoTitle.text = playlist.snippet.title
                binding.imgPlaylists.load(playlist.snippet.thumbnails.default.url)
            }
            itemView.setOnClickListener { onCLickItem(playlist) }
        }
    }
}