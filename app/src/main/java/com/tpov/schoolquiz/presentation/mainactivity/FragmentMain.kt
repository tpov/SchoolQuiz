package com.tpov.schoolquiz.presentation.mainactivity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.databinding.TitleFragmentBinding
import com.tpov.schoolquiz.databinding.TitleFragmentBinding.*
import com.tpov.schoolquiz.presentation.MainApp
import com.tpov.schoolquiz.presentation.dialog.CreateQuestionDialog
import com.tpov.schoolquiz.presentation.dialog.CreateQuestionSecondDialog
import com.tpov.schoolquiz.presentation.fragment.BaseFragment
import com.tpov.schoolquiz.presentation.fragment.FragmentManager
import com.tpov.schoolquiz.presentation.question.QuestionActivity
import com.tpov.schoolquiz.presentation.question.QuestionViewModel
import com.tpov.shoppinglist.utils.ShareHelper
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FragmentMain : BaseFragment(), MainActivityAdapter.Listener {

    private lateinit var binding: TitleFragmentBinding
    private var createQuiz = false

    private val questionViewModel: QuestionViewModel by activityViewModels {
        QuestionViewModel.QuizModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew(name: String, stars: Int) {

        val closeDialog = false
        CreateQuestionDialog.showDialog(
            activity as MainActivity, name, closeDialog,
            object : CreateQuestionDialog.Listener {
                override fun onClick(
                    listNameQuestion: String,
                    listUserName: String,
                    nameAnswerQuestion: Boolean,
                    nameTypeQuestion: Boolean,
                    numQuestion: Int,
                    closeDialog: Boolean,
                    name: String,

                    ) {
                    when (name) {
                        CREATE_QUIZ -> {
                            createQuiz = true
                            startSecondDialog(listUserName)
                        }
                        getString(R.string.dialog_text_delete) -> {
                            var nameQuiz = ""
                            val startObs = getString(R.string.dialog_short_text_delete)
                            questionViewModel.getQuiz.observe(this@FragmentMain, {
                                if (startObs == getString(R.string.dialog_short_text_delete)) {
                                    it.forEach { item ->
                                        if (stars == item.id) nameQuiz = item.nameQuestion
                                    }
                                    questionViewModel.deleteQuiz(id, nameTypeQuestion, nameQuiz)
                                }
                            })
                        }
                        getString(R.string.dialog_text_share) -> {
                            var nameQuiz = ""
                            val startObs = getString(R.string.dialog_short_text_share)
                            questionViewModel.getQuiz.observe(this@FragmentMain, {
                                if (startObs == getString(R.string.dialog_short_text_share)) {
                                    it.forEach { item ->
                                        if (stars == item.id) nameQuiz = item.nameQuestion
                                    }
                                }
                            })
                            questionViewModel.getQuestion.observe(this@FragmentMain, {
                                startActivity(
                                    Intent.createChooser(
                                        ShareHelper.shareShopList(nameQuiz, it, nameTypeQuestion),
                                        "Share by"
                                    )
                                )
                            })
                        }
                        else -> {
                            val intent = Intent(activity, QuestionActivity::class.java)
                            intent.putExtra(QuestionActivity.NAME_QUESTION, name)
                            intent.putExtra(QuestionActivity.NAME_USER, listUserName)
                            intent.putExtra(QuestionActivity.STARS, stars.toString())
                            startActivity(intent)
                        }
                    }
                }

                fun startSecondDialog(listNameQuestionSecond: String) {
                    CreateQuestionSecondDialog.showDialog(
                        activity as AppCompatActivity,
                        object : CreateQuestionSecondDialog.Listener {
                            override fun onClick(
                                listNameQuestion: String,
                                listUserName: String,
                                nameAnswerQuestion: Boolean,
                                nameTypeQuestion: Boolean,
                                numQuestion: Int,
                                closeDialog: Boolean
                            ) {
                                val nameList = Question(
                                    null,
                                    listNameQuestion,
                                    nameAnswerQuestion,
                                    nameTypeQuestion,
                                    listNameQuestionSecond,
                                )
                                insertFrontList(listUserName, listNameQuestion, nameList)
                            }
                        },
                    )
                }
            },
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionViewModel.getQuiz.observe(viewLifecycleOwner, {
            val adapter = MainActivityAdapter(this@FragmentMain)
            adapter.submitList(it)
            binding.rcView.layoutManager = LinearLayoutManager(activity)
            binding.rcView.adapter = adapter
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflate(inflater, container, false)
        return binding.root
    }

    @InternalCoroutinesApi
    fun insertFrontList(listNameQuestion: String, listUserName: String, nameList: Question) {
        val frontList = Quiz(
            null,
            listNameQuestion,
            listUserName,
            TimeManager.getCurrentTime(),
            0,
            0,
            0,
            0,
            0
        )
        questionViewModel.insertQuiz(frontList)
        if (createQuiz) questionViewModel.insertQuestion(nameList)

        createQuiz = false
    }

    //Тег по которому диалог определяет что нужно именно сделать с квестом. И id самого квеста
    override fun deleteItem(id: Int) {
        FragmentManager.currentFrag?.onClickNew(DELETE_QUIZ, id)
    }

    override fun onClick(name: String, stars: Int) {
        FragmentManager.currentFrag?.onClickNew(name, stars)
    }

    override fun shareItem(name: String, stars: Int) {
        FragmentManager.currentFrag?.onClickNew(SHARE_QUIZ, stars)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMain()

        const val CREATE_QUIZ = ""
        const val DELETE_QUIZ = "deleteQuiz"
        const val SHARE_QUIZ = "shareQuiz"
    }
}