package pl.aifer.youtube_sandbox_m6_l3.presentation.playlistitems.paging_load_state

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class PlaylistItemsLoadStateAdapter : LoadStateAdapter<PlaylistItemsLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PlaylistItemsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PlaylistItemsLoadStateViewHolder {
        return PlaylistItemsLoadStateViewHolder.create(
            parent = parent
        )
    }
}