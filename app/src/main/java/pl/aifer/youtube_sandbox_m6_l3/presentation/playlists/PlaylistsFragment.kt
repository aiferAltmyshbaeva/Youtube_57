package pl.aifer.youtube_sandbox_m6_l3.presentation.playlists

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseFragment
import pl.aifer.youtube_sandbox_m6_l3.core.network.RetrofitClient
import pl.aifer.youtube_sandbox_m6_l3.core.utils.Status
import pl.aifer.youtube_sandbox_m6_l3.databinding.FragmentPlaylistsBinding
import pl.aifer.youtube_sandbox_m6_l3.domain.repository.Repository
import pl.aifer.youtube_sandbox_m6_l3.presentation.playlists.adapter.PlaylistsAdapter

internal class PlaylistsFragment :
    BaseFragment<FragmentPlaylistsBinding, PlaylistsViewModel>(FragmentPlaylistsBinding::inflate) {

    override lateinit var viewModel: PlaylistsViewModel
//    val viewModel = PlaylistsViewModel(Repository(RetrofitClient().createApiService()))

//    val playlistsViewModel = PlaylistsViewModel(Repository(RetrofitClient().createApiService()))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val viewModel = PlaylistsViewModel(Repository(RetrofitClient().createApiService()))
        val repository = Repository(RetrofitClient().createApiService())
        viewModel = PlaylistsViewModel(repository)

        val adapter = PlaylistsAdapter(emptyList())
        binding.recyclerView.adapter = adapter

        viewModel.getPlaylists().observe(viewLifecycleOwner, Observer { resource ->
            if (resource !=null) {
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        val playlists = resource.data?.items ?: emptyList()
                        adapter.updateData(playlists)
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
            }else{
                binding.progressBar.visibility = View.GONE
                Toast.makeText(context, "Unexpected error occured", Toast.LENGTH_SHORT).show()
            }
        })
    }
}