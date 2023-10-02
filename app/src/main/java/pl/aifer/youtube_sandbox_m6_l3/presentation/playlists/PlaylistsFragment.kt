package pl.aifer.youtube_sandbox_m6_l3.presentation.playlists

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import pl.aifer.youtube_sandbox_m6_l3.R
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseFragment
import pl.aifer.youtube_sandbox_m6_l3.core.utils.Status
import pl.aifer.youtube_sandbox_m6_l3.databinding.FragmentPlaylistsBinding
import pl.aifer.youtube_sandbox_m6_l3.presentation.MainActivity
import pl.aifer.youtube_sandbox_m6_l3.presentation.playlists.adapter.PlaylistsAdapter
import pl.aifer.youtube_sandbox_m6_l3.utils.NetworkUtils

internal class PlaylistsFragment : BaseFragment<FragmentPlaylistsBinding>() {

    private val adapter = PlaylistsAdapter()
    private val playlistsViewModel = PlaylistsViewModel(MainActivity.repository)

    override fun inflateViewBinding() =
        FragmentPlaylistsBinding.inflate(layoutInflater)

    override fun checkConnection() {
        NetworkUtils(requireContext()).observe(viewLifecycleOwner, Observer { isConnected ->
            if (!isConnected) findNavController().navigate(R.id.noConnectionFragment)
        })
    }

    override fun initView() {
        super.initView()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        playlistsViewModel.getPlaylists().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
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