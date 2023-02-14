package com.tpov.schoolquiz.presentation.network.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.presentation.fragment.BaseFragment

class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        // TODO: Use the ViewModel
    }

}