package pl.aifer.youtube_sandbox_m6_l3.presentation.playlists

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseFragment
import pl.aifer.youtube_sandbox_m6_l3.core.utils.Status
import pl.aifer.youtube_sandbox_m6_l3.databinding.FragmentPlaylistsBinding
import pl.aifer.youtube_sandbox_m6_l3.presentation.MainActivity
import pl.aifer.youtube_sandbox_m6_l3.presentation.playlists.adapter.PlaylistsAdapter

internal class PlaylistsFragment :
    BaseFragment<FragmentPlaylistsBinding>() {
    private val viewModel = PlaylistsViewModel(MainActivity.repository)
    override fun inflateViewBinding(): FragmentPlaylistsBinding =
        FragmentPlaylistsBinding.inflate(layoutInflater)

    private val adapter = PlaylistsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.getPlaylists().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    // TODO добавить проверку
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let {
                        adapter.updateData(it.items)
                    }
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    val errorMessage = resource.message ?: "Unknown Error"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}