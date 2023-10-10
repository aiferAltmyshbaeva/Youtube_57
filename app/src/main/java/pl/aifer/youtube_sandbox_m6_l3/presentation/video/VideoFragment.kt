package pl.aifer.youtube_sandbox_m6_l3.presentation.video

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import coil.load
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.aifer.youtube_sandbox_m6_l3.R
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseFragment
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.databinding.FragmentVideoBinding
import pl.aifer.youtube_sandbox_m6_l3.utils.Constants
import pl.aifer.youtube_sandbox_m6_l3.utils.NetworkUtils


internal class VideoFragment : BaseFragment<FragmentVideoBinding, VideoViewModel>() {

    private val networkUtils: NetworkUtils by lazy { NetworkUtils(requireContext()) }

    override val viewModel: VideoViewModel by viewModel()

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentVideoBinding.inflate(inflater, container, false)

    private fun initViewModel(videoId: String) {
        viewModel.getVideo(videoId)
    }

    override fun initView() {
        super.initView()
        viewModel.video.observe(viewLifecycleOwner) { item ->
            initData(item.items)
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

    override fun initListener() {
        super.initListener()

        binding.btnDownload.setOnClickListener {
            downloadVideo()
        }

        setFragmentResultListener(Constants.VIDEO_REQUEST_KEY) { _, bundle ->
            bundle.getSerializable(Constants.VIDEO_RESULT_KEY)
                ?.let { item ->
                    val _item = item as PlaylistsModel.Item
                    initViewModel(_item.contentDetails.videoId)
                }
        }
    }

    private fun downloadVideo() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(R.layout.layout_download).show()
    }

    private fun initData(items: List<PlaylistsModel.Item>) {
        binding.imgVideo.load(items.first().snippet.thumbnails.standard.url)
        binding.tvVideoTitle.text = items.first().snippet.title
        binding.tvVideoDesc.text = items.first().snippet.description
        binding.layoutToolbar.containerBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun checkConnection() {
        networkUtils.observe(viewLifecycleOwner) { hasInternet ->
            if (!hasInternet) {
                binding.mainContainer.visibility = View.GONE
                binding.layoutToolbar.root.visibility = View.GONE
                binding.containerNoConnection.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnTryAgain.setOnClickListener {
                if (hasInternet) {
                    binding.mainContainer.visibility = View.VISIBLE
                    binding.layoutToolbar.root.visibility = View.VISIBLE
                    binding.containerNoConnection.visibility = View.GONE
                }
            }
        }
    }

}