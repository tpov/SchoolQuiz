package com.tpov.schoolquiz.fragment

import androidx.fragment.app.activityViewModels
import com.tpov.schoolquiz.activity.MainApp
import com.tpov.schoolquiz.database.MainViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FragmentInfo: BaseFragment()  {
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew(name: String, stars: Int) {
    }
}