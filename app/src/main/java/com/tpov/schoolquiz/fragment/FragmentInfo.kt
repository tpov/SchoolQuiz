package com.tpov.schoolquiz.fragment

import androidx.fragment.app.activityViewModels
import com.tpov.schoolquiz.activity.MainApp
import com.tpov.schoolquiz.presentation.question.QuestionViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FragmentInfo: BaseFragment()  {
    private val questionViewModel: QuestionViewModel by activityViewModels {
        QuestionViewModel.QuizModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew(name: String, stars: Int) {
    }
}