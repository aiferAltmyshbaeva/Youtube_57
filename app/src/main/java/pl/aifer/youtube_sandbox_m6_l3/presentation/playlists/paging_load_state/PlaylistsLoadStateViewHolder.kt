package pl.aifer.youtube_sandbox_m6_l3.presentation.playlists.paging_load_state

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import pl.aifer.youtube_sandbox_m6_l3.R
import pl.aifer.youtube_sandbox_m6_l3.databinding.ItemProgressBarBinding

class PlaylistsLoadStateViewHolder(
    binding: ItemProgressBarBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        loadState.endOfPaginationReached
    }

    companion object {
        fun create(parent: ViewGroup): PlaylistsLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_progress_bar, parent, false
            )
            val binding = ItemProgressBarBinding.bind(view)
            return PlaylistsLoadStateViewHolder(binding)
        }
    }
}