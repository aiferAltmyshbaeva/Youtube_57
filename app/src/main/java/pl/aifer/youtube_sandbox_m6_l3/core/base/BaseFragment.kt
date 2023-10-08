package pl.aifer.youtube_sandbox_m6_l3.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    private var _viewModel: VM? = null
    protected val viewModel get() = _viewModel!!
    protected abstract fun inflaterViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected abstract fun setViewModel(): VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflaterViewBinding(inflater, container)
        _viewModel = setViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        checkConnection()
    }

    open fun initView() {}
    open fun initListener() {}
    open fun checkConnection() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _viewModel = null
    }
}