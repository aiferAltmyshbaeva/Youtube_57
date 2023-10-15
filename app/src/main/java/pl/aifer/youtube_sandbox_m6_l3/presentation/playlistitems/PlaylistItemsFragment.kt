package pl.aifer.youtube_sandbox_m6_l3.presentation.playlistitems

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.aifer.youtube_sandbox_m6_l3.R
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseFragment
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.databinding.FragmentPlaylistItemsBinding
import pl.aifer.youtube_sandbox_m6_l3.presentation.playlistitems.paging_load_state.PlaylistItemsLoadStateAdapter
import pl.aifer.youtube_sandbox_m6_l3.utils.Constants
import pl.aifer.youtube_sandbox_m6_l3.utils.NetworkUtils
import pl.aifer.youtube_sandbox_m6_l3.utils.PlaylistsModelComparator

internal class PlaylistItemsFragment :
    BaseFragment<FragmentPlaylistItemsBinding, PlaylistItemsViewModel>() {

    override val viewModel: PlaylistItemsViewModel by viewModel()

    private val adapter = PlaylistItemsAdapter(
        diffUtilCallback = PlaylistsModelComparator,
        onCLickItem = this::onClickItem
    )

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlaylistItemsBinding.inflate(inflater, container, false)

    override fun initView() {
        super.initView()
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun initListener() {
        super.initListener()

        binding.layoutToolbar.containerBack.setOnClickListener {
            findNavController().navigateUp()
        }

        setFragmentResultListener(Constants.REQUEST_KEY) { _, bundle ->
            bundle.getSerializable(Constants.RESULT_KEY)
                ?.let { playlist ->
                    val _playlist = playlist as PlaylistsModel.Item
                    initViewModel(_playlist.id)
                    initData(_playlist)
                }
        }
    }

    override fun checkConnection() {
        val networkUtils = NetworkUtils(requireContext())
        networkUtils.observe(viewLifecycleOwner) { hasInternet ->
            if (!hasInternet) {
                binding.containerToolbar.visibility = View.GONE
                binding.containerNoConnection.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnTryAgain.setOnClickListener {
                if (hasInternet) {
                    binding.containerToolbar.visibility = View.VISIBLE
                    binding.containerNoConnection.visibility = View.GONE
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.you_don_t_have_internet_connection_try_again,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initViewModel(playlistId: String) {
        viewModel.getPagingPlaylistItems(playlistId).observe(viewLifecycleOwner) {
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = PlaylistItemsLoadStateAdapter(),
                    footer = PlaylistItemsLoadStateAdapter()
                )
                adapter.submitData(
                    lifecycle = lifecycle,
                    pagingData = it
                )
                adapter.retry()
                adapter.refresh()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initData(playlist: PlaylistsModel.Item) {
        binding.tvPlaylistTitle.text = playlist.snippet.title
        binding.tvPlaylistDesc.text = playlist.snippet.description
        binding.tvAmountOfVideo.text =
            playlist.contentDetails.itemCount.toString() + " video series"
    }

    private fun onClickItem(item: PlaylistsModel.Item) {
        setFragmentResult(
            Constants.VIDEO_REQUEST_KEY,
            bundleOf(Constants.VIDEO_RESULT_KEY to item)
        )
        findNavController().navigate(R.id.videoFragment)
    }
}