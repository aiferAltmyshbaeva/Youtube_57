package pl.aifer.youtube_sandbox_m6_l3.presentation.noconnection

import android.content.Context
import android.net.ConnectivityManager
import androidx.navigation.fragment.findNavController
import pl.aifer.youtube_sandbox_m6_l3.core.base.BaseFragment
import pl.aifer.youtube_sandbox_m6_l3.databinding.FragmentNoconnectionBinding

class NoConnectionFragment : BaseFragment<FragmentNoconnectionBinding>() {
    override fun inflateViewBinding() = FragmentNoconnectionBinding.inflate(layoutInflater)
    override fun initListener() {
        super.initListener()
        binding.btnTryAgain.setOnClickListener {
            checkConnection()
        }
    }

    override fun checkConnection() {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetwork
        if (networkInfo != null) {
            findNavController().navigateUp()
        }
    }
}