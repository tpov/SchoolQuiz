package com.tpov.schoolquiz.presentation.network.profile

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.presentation.MainApp
import com.tpov.schoolquiz.presentation.factory.ViewModelFactory
import com.tpov.schoolquiz.presentation.fragment.BaseFragment
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @OptIn(InternalCoroutinesApi::class)
    private val component by lazy {
        (requireActivity().application as MainApp).component
    }

    @OptIn(InternalCoroutinesApi::class)
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @OptIn(InternalCoroutinesApi::class)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

    @OptIn(InternalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
    }

    @OptIn(InternalCoroutinesApi::class)
    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

}