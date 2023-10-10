package pl.aifer.youtube_sandbox_m6_l3.presentation.playlistitems

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.aifer.youtube_sandbox_m6_l3.R
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseFragment
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.databinding.FragmentPlaylistItemsBinding
import pl.aifer.youtube_sandbox_m6_l3.utils.Constants
import pl.aifer.youtube_sandbox_m6_l3.utils.NetworkUtils

internal class PlaylistItemsFragment :
    BaseFragment<FragmentPlaylistItemsBinding, PlaylistItemsViewModel>() {

    private val adapter = PlaylistItemsAdapter(this::onClickItem)

    private val networkUtils: NetworkUtils by lazy { NetworkUtils(requireContext()) }

    override val viewModel: PlaylistItemsViewModel by viewModel()

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlaylistItemsBinding.inflate(inflater, container, false)

    private fun initViewModel(playlistId: String) {
        viewModel.getPlaylistItems(playlistId)
    }

    override fun initView() {
        super.initView()

        viewModel.playlistItems.observe(viewLifecycleOwner) { list ->
            initRecyclerView(list.items)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecyclerView(items: List<PlaylistsModel.Item>) {
        adapter.updateData(items)
        binding.recyclerView.adapter = adapter
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
                    initData(_playlist)
                    initViewModel(_playlist.id)
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

    override fun checkConnection() {
        networkUtils.observe(viewLifecycleOwner) { hasInternet ->
            if (!hasInternet) {
                binding.containerToolbar.visibility = View.GONE
                binding.containerNoConnection.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnTryAgain.setOnClickListener {
                if (hasInternet) {
                    binding.containerToolbar.visibility = View.VISIBLE
                    binding.containerNoConnection.visibility = View.GONE
                }
            }
        }
    }

    private fun onClickItem(item: PlaylistsModel.Item) {
        setFragmentResult(
            Constants.VIDEO_REQUEST_KEY,
            bundleOf(Constants.VIDEO_RESULT_KEY to item)
        )
        findNavController().navigate(R.id.videoFragment)
    }
}