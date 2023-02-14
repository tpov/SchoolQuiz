package com.tpov.schoolquiz.presentation.network

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.presentation.fragment.BaseFragment
import com.tpov.schoolquiz.presentation.network.profile.ProfileFragment

class AutorisationFragment : BaseFragment() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var loginCity: EditText
    private lateinit var loginButton: Button
    private lateinit var registrationButton: Button
    private lateinit var loadingProgressBar: ProgressBar

    companion object {
        fun newInstance() = AutorisationFragment()

    }

    private lateinit var viewModel: AutorisationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AutorisationViewModel::class.java]
        usernameEditText = view.findViewById(R.id.username)
        passwordEditText = view.findViewById(R.id.password)
        nameEditText = view.findViewById(R.id.loginName)
        dateEditText = view.findViewById(R.id.years)
        loginButton = view.findViewById(R.id.login)
        loadingProgressBar = view.findViewById(R.id.loading)
        registrationButton = view.findViewById(R.id.registration)
        loginCity = view.findViewById(R.id.city)

        loginButton.setOnClickListener {
            viewModel.loginAcc(
                usernameEditText.text.toString(),
                passwordEditText.text.toString(),
                requireContext()
            )
        }

        registrationButton.setOnClickListener {
            viewModel.createAcc(
                usernameEditText.text.toString(),
                passwordEditText.text.toString(),
                requireContext(),
                nameEditText.text.toString(),
                dateEditText.text.toString(),
                loginCity.text.toString()
            )
        }

        viewModel.someData.observe(viewLifecycleOwner) {
            if (it) {
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.remove(this)
                fragmentTransaction?.replace(R.id.title_fragment, ProfileFragment.newInstance())
                fragmentTransaction?.commit()
                Log.d("v3.0", "fragmentTransaction?.commit()")
            }
        }
    }
}