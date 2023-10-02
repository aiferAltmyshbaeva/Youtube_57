package pl.aifer.youtube_sandbox_m6_l3.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateViewBinding()
        return binding.root
    }

    abstract fun inflateViewBinding(): VB?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkConnection()
        initView()
        initListener()
    }

    abstract fun checkConnection()

    open fun initListener() {}
    open fun initView() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}