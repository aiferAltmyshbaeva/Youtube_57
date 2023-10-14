package pl.aifer.youtube_sandbox_m6_l3.presentation.playlistitems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.databinding.ItemPlaylistItemsBinding

internal class PlaylistItemsAdapter(
    private val onCLickItem: (item: PlaylistsModel.Item) -> Unit
) :
    RecyclerView.Adapter<PlaylistItemsAdapter.PlaylistItemsViewHolder>() {

    private val playlists = mutableListOf<PlaylistsModel.Item>()
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
        holder.bind(playlists[position])
    }

    override fun getItemCount() = playlists.size

    inner class PlaylistItemsViewHolder(private val binding: ItemPlaylistItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: PlaylistsModel.Item) {
            binding.tvVideoTitle.text = playlist.snippet.title
            binding.imgPlaylists.load(playlist.snippet.thumbnails.default.url)
            itemView.setOnClickListener { onCLickItem(playlist) }
        }
    }

    fun updateData(newPlaylists: List<PlaylistsModel.Item>) {
        playlists.clear()
        playlists.addAll(newPlaylists)
        notifyItemRangeInserted(playlists.size, newPlaylists.size - playlists.size)
    }
}