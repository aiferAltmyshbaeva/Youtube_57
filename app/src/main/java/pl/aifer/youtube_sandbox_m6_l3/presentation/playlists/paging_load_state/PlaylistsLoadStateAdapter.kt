package pl.aifer.youtube_sandbox_m6_l3.presentation.playlists.paging_load_state

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class PlaylistsLoadStateAdapter : LoadStateAdapter<PlaylistsLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PlaylistsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PlaylistsLoadStateViewHolder {
        return PlaylistsLoadStateViewHolder.create(
            parent = parent
        )
    }
}