package com.tpov.geoquiz.fragment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.tpov.geoquiz.R
import com.tpov.geoquiz.activity.MainApp
import com.tpov.geoquiz.database.MainViewModel
import kotlinx.coroutines.InternalCoroutinesApi

object FragmentManager {
    var currentFrag: BaseFragment? = null

    fun setFragment(newFrag: BaseFragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.title_fragment, newFrag)                       //Заменяем пустой фрагмент
        transaction.commit()
        currentFrag = newFrag
    }
}