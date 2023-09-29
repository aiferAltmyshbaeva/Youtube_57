package pl.aifer.youtube_sandbox_m6_l3.presentation.playlists.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.databinding.ItemPlaylistsBinding

internal class PlaylistsAdapter(private var playlists: List<PlaylistsModel.Item>) :
    RecyclerView.Adapter<PlaylistsAdapter.PlaylistsViewHolder>() {

    class PlaylistsViewHolder(val binding: ItemPlaylistsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistsViewHolder {
        val binding =
            ItemPlaylistsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistsViewHolder, position: Int) {
        val playlist = playlists[position]
        holder.binding.tvTitle.text = playlist.snippet.title
        holder.binding.tvSubtitle.text = playlist.snippet.description
        Glide.with(holder.itemView)
            .load(playlist.snippet.thumbnails.default.url)
            .into(holder.binding.imgPlaylists)
    }

    override fun getItemCount() = playlists.size

    fun updateData(newPlaylists: List<PlaylistsModel.Item>){
        playlists = newPlaylists
        notifyDataSetChanged()
    }
}