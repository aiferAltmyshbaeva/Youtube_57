package pl.aifer.youtube_sandbox_m6_l3.presentation.playlists

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.databinding.ItemPlaylistsBinding

internal class PlaylistsAdapter(
    diffUtilCallback: DiffUtil.ItemCallback<PlaylistsModel.Item>,
    private val onClickItem: (playlistItem: PlaylistsModel.Item) -> Unit,
) :
    PagingDataAdapter<PlaylistsModel.Item, PlaylistsAdapter.PlaylistsViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistsViewHolder {
        return PlaylistsViewHolder(
            ItemPlaylistsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlaylistsViewHolder, position: Int) {
        val newPosition = getItem(position)
        newPosition?.let {
            holder.bind(it)
        }
    }

    inner class PlaylistsViewHolder(private val binding: ItemPlaylistsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(playlist: PlaylistsModel.Item) {
            binding.tvTitle.text = playlist.snippet.title
            binding.tvSubtitle.text =
                playlist.contentDetails.itemCount.toString() + " video series"
            binding.imgPlaylists.load(playlist.snippet.thumbnails.default.url)
            itemView.setOnClickListener { onClickItem(playlist) }
        }
    }
}