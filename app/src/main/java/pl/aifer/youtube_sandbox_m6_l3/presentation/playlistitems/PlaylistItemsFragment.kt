package pl.aifer.youtube_sandbox_m6_l3.presentation.playlistitems

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseFragment
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.databinding.FragmentPlaylistItemsBinding
import pl.aifer.youtube_sandbox_m6_l3.presentation.MainActivity
import pl.aifer.youtube_sandbox_m6_l3.utils.Constants
import pl.aifer.youtube_sandbox_m6_l3.utils.NetworkUtils


internal class PlaylistItemsFragment :
    BaseFragment<FragmentPlaylistItemsBinding, PlaylistItemsViewModel>() {

    private val adapter = PlaylistItemsAdapter()

    private val networkUtils: NetworkUtils by lazy { NetworkUtils(requireContext()) }
    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlaylistItemsBinding.inflate(inflater, container, false)

    override fun setViewModel() = PlaylistItemsViewModel(MainActivity.repository)

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
        binding.layoutToolbar.containerBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun checkConnection() {
        networkUtils.observe(viewLifecycleOwner) { hasInternet ->
            if (!hasInternet) {
                binding.recyclerView.visibility = View.GONE
                binding.containerNoConnection.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnTryAgain.setOnClickListener {
                if (hasInternet) {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.containerNoConnection.visibility = View.GONE
                }
            }
        }
    }
}