package pl.aifer.youtube_sandbox_m6_l3.presentation.playlists.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.databinding.ItemPlaylistsBinding

internal class PlaylistsAdapter() :
    RecyclerView.Adapter<PlaylistsAdapter.PlaylistsViewHolder>() {

    private var _playlists = mutableListOf<PlaylistsModel.Item>()
    private val playlists get() = _playlists

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
                playlist.contentDetails.itemCount.toString() + " video series"
            binding.imgPlaylists.load(playlist.snippet.thumbnails.default.url)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newPlaylists: List<PlaylistsModel.Item>) {
        _playlists.clear()
        _playlists.addAll(newPlaylists)
//        notifyItemRangeInserted(_playlists.size, newPlaylists.size - _playlists.size)
        notifyDataSetChanged()
    }
}