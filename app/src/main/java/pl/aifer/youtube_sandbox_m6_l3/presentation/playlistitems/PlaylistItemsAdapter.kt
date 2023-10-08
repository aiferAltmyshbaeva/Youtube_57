package pl.aifer.youtube_sandbox_m6_l3.presentation.playlistitems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.databinding.ItemPlaylistItemsBinding

internal class PlaylistItemsAdapter :
    RecyclerView.Adapter<PlaylistItemsAdapter.PlaylistItemsViewHolder>() {

    private var _playlists = mutableListOf<PlaylistsModel.Item>()
    private val playlists: List<PlaylistsModel.Item> get() = _playlists
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
        holder: PlaylistItemsAdapter.PlaylistItemsViewHolder,
        position: Int
    ) {
        holder.bind(playlists[position])
    }

    override fun getItemCount() = playlists.size

    inner class PlaylistItemsViewHolder(private val binding: ItemPlaylistItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: PlaylistsModel.Item) {
            binding.tvVideoTitle.text = playlist.snippet.title
            binding.imgPlaylists.load(playlist.snippet.thumbnails.default.url)
        }
    }

    fun updateData(newPlaylists: List<PlaylistsModel.Item>) {
        _playlists.clear()
        _playlists.addAll(newPlaylists)
        notifyItemRangeInserted(_playlists.size, newPlaylists.size - _playlists.size)
    }
}