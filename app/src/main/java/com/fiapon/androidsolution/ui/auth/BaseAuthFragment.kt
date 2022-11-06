package com.fiapon.androidsolution.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fiapon.androidsolution.R
import androidx.navigation.fragment.findNavController

const val NAVIGATION_KEY = "NAV_KEY"

abstract class BaseAuthFragment : Fragment() {

    private val baseAuthViewModel: BaseAuthViewModel by viewModels()
    abstract val layout: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        registerObserver()
        baseAuthViewModel.isLoggedIn()

        val screenRootView = FrameLayout(requireContext())
        val screenView = inflater.inflate(layout, container, false)

        screenRootView.addView(screenView)

        return screenRootView
    }

    private fun registerObserver() {
        baseAuthViewModel.loggedState.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Loading -> {}
                is RequestState.Success -> {
                }
                is RequestState.Error -> {
                    findNavController().navigate(
                        R.id.loginFragment, bundleOf(
                            NAVIGATION_KEY to findNavController().currentDestination?.id
                        )
                    )
                }
            }
        }
    }
}