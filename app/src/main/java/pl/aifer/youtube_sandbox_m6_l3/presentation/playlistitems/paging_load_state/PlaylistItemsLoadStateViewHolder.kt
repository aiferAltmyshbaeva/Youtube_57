package pl.aifer.youtube_sandbox_m6_l3.presentation.playlistitems.paging_load_state

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import pl.aifer.youtube_sandbox_m6_l3.R
import pl.aifer.youtube_sandbox_m6_l3.databinding.ItemProgressBarBinding

class PlaylistItemsLoadStateViewHolder(
    binding: ItemProgressBarBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        loadState.endOfPaginationReached
    }

    companion object {
        fun create(parent: ViewGroup): PlaylistItemsLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_progress_bar, parent, false
            )
            val binding = ItemProgressBarBinding.bind(view)
            return PlaylistItemsLoadStateViewHolder(binding)
        }
    }

}