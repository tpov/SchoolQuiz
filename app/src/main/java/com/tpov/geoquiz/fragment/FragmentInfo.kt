package com.tpov.geoquiz.fragment

import androidx.fragment.app.activityViewModels
import com.tpov.geoquiz.activity.MainApp
import com.tpov.geoquiz.database.MainViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FragmentInfo: BaseFragment()  {
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew(name: String, stars: Int) {
    }
}