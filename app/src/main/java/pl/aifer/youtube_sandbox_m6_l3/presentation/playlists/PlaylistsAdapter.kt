package pl.aifer.youtube_sandbox_m6_l3.presentation.playlists

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pl.aifer.youtube_sandbox_m6_l3.R
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.databinding.ItemPlaylistsBinding
import pl.aifer.youtube_sandbox_m6_l3.utils.ResourceProvider

internal class PlaylistsAdapter(
    private val onClickItem: (playlistItem: PlaylistsModel.Item) -> Unit,
    private val resourceProvider: ResourceProvider
) :
    RecyclerView.Adapter<PlaylistsAdapter.PlaylistsViewHolder>() {

    private val playlists = mutableListOf<PlaylistsModel.Item>()

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
        holder.bind(playlists[position])
    }

    override fun getItemCount() = playlists.size

    inner class PlaylistsViewHolder(private val binding: ItemPlaylistsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(playlist: PlaylistsModel.Item) {
            binding.tvTitle.text = playlist.snippet.title
            binding.tvSubtitle.text =
                resourceProvider.getStringWithKey(
                    resId = R.string.video_series,
                    keyResId = playlist.contentDetails.itemCount.toString()
                )
            binding.imgPlaylists.load(playlist.snippet.thumbnails.default.url)
            itemView.setOnClickListener { onClickItem(playlist) }
        }
    }

    fun updateData(newPlaylists: List<PlaylistsModel.Item>) {
        playlists.clear()
        playlists.addAll(newPlaylists)
        notifyItemRangeInserted(playlists.size, newPlaylists.size - playlists.size)
    }
}