package pl.aifer.youtube_sandbox_m6_l3.utils

import androidx.recyclerview.widget.DiffUtil
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel

internal object PlaylistsModelComparator : DiffUtil.ItemCallback<PlaylistsModel.Item>() {
    override fun areItemsTheSame(
        oldItem: PlaylistsModel.Item,
        newItem: PlaylistsModel.Item
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PlaylistsModel.Item,
        newItem: PlaylistsModel.Item
    ): Boolean {
        return oldItem == newItem
    }
}


