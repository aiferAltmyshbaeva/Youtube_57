package pl.aifer.youtube_sandbox_m6_l3.presentation.playlists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.aifer.youtube_sandbox_m6_l3.R
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseFragment
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.databinding.FragmentPlaylistsBinding
import pl.aifer.youtube_sandbox_m6_l3.utils.Constants
import pl.aifer.youtube_sandbox_m6_l3.utils.NetworkUtils
import pl.aifer.youtube_sandbox_m6_l3.utils.ResourceProvider

internal class PlaylistsFragment : BaseFragment<FragmentPlaylistsBinding, PlaylistsViewModel>(),
    ResourceProvider {

    override val viewModel: PlaylistsViewModel by viewModel()

    private val adapter = PlaylistsAdapter(
        onClickItem = this::onClickItem,
        resourceProvider = this
    )

    private fun initRecyclerView(items: List<PlaylistsModel.Item>) {
        adapter.updateData(items)
        binding.recyclerView.adapter = adapter
    }

    private fun onClickItem(playlistItem: PlaylistsModel.Item) {
        setFragmentResult(
            Constants.REQUEST_KEY, bundleOf(Constants.RESULT_KEY to playlistItem)
        )
        findNavController().navigate(R.id.playlistItemsFragment)
    }

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlaylistsBinding.inflate(inflater, container, false)

    override fun initView() {
        super.initView()

        viewModel.getPlaylists()

        viewModel.playlists.observe(viewLifecycleOwner) { list ->
            initRecyclerView(list.items)
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
                        "You don't have Internet connection, try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun getStringWithKey(resId: Int, keyResId: String): String {
        return resources.getString(resId, keyResId)
    }
}