package pl.aifer.youtube_sandbox_m6_l3.presentation.video

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.aifer.youtube_sandbox_m6_l3.R
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseFragment
import pl.aifer.youtube_sandbox_m6_l3.data.model.PlaylistsModel
import pl.aifer.youtube_sandbox_m6_l3.databinding.FragmentVideoBinding
import pl.aifer.youtube_sandbox_m6_l3.utils.Constants
import pl.aifer.youtube_sandbox_m6_l3.utils.NetworkUtils

internal class VideoFragment : BaseFragment<FragmentVideoBinding, VideoViewModel>() {

    override val viewModel: VideoViewModel by viewModel()

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentVideoBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(binding.youtubePlayerView)
    }

    override fun initView() {
        super.initView()
        viewModel.video.observe(viewLifecycleOwner) { item ->
            initData(item.items)
        }
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

    override fun checkConnection() {
        val networkUtils = NetworkUtils(requireContext())
        networkUtils.observe(viewLifecycleOwner) { hasInternet ->
            if (!hasInternet) {
                binding.mainContainer.visibility = View.GONE
                binding.containerNoConnection.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnTryAgain.setOnClickListener {
                if (hasInternet) {
                    binding.mainContainer.visibility = View.VISIBLE
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

    private fun initViewModel(videoId: String) {
        viewModel.getVideo(videoId)
    }

    private fun downloadVideo() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(R.layout.layout_download).show()
    }

    private fun initData(items: List<PlaylistsModel.Item>) {
        binding.youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                val videoId = items.first().id
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
        binding.tvVideoTitle.text = items.first().snippet.title
        binding.tvVideoDesc.text = items.first().snippet.description
//        binding.layoutToolbar.containerBack.setOnClickListener {
//            findNavController().navigateUp()
//        }
    }
}