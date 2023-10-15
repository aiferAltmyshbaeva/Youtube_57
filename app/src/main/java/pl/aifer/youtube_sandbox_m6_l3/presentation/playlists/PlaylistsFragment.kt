package pl.aifer.youtube_sandbox_m6_l3.presentation.playlists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.aifer.youtube_sandbox_m6_l3.R
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseFragment
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.databinding.FragmentPlaylistsBinding
import pl.aifer.youtube_sandbox_m6_l3.presentation.playlists.paging_load_state.PlaylistsLoadStateAdapter
import pl.aifer.youtube_sandbox_m6_l3.utils.Constants
import pl.aifer.youtube_sandbox_m6_l3.utils.NetworkUtils
import pl.aifer.youtube_sandbox_m6_l3.utils.PlaylistsModelComparator

internal class PlaylistsFragment : BaseFragment<FragmentPlaylistsBinding, PlaylistsViewModel>() {

    override val viewModel: PlaylistsViewModel by viewModel()

    private val adapter = PlaylistsAdapter(
        diffUtilCallback = PlaylistsModelComparator,
        onClickItem = this::onClickItem
    )

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlaylistsBinding.inflate(inflater, container, false)

    override fun initView() {
        super.initView()
        viewModel.getPagingPlaylists().observe(viewLifecycleOwner) {
            viewModel.viewModelScope.launch(Dispatchers.IO) {

                binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = PlaylistsLoadStateAdapter(),
                    footer = PlaylistsLoadStateAdapter()
                )

                adapter.submitData(
                    lifecycle = lifecycle,
                    pagingData = it
                )
                adapter.retry()
                adapter.refresh()
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun checkConnection() {
        val networkUtils = NetworkUtils(requireContext())
        networkUtils.observe(viewLifecycleOwner) { hasInternet ->
            if (!hasInternet) {
                binding.recyclerView.visibility = View.GONE
                binding.containerNoConnection.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnTryAgain.setOnClickListener {
                if (hasInternet) {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.containerNoConnection.visibility = View.GONE
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.you_don_t_have_internet_connection_try_again),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun onClickItem(playlistItem: PlaylistsModel.Item) {
        setFragmentResult(
            Constants.REQUEST_KEY, bundleOf(Constants.RESULT_KEY to playlistItem)
        )
        findNavController().navigate(R.id.playlistItemsFragment)
    }
}