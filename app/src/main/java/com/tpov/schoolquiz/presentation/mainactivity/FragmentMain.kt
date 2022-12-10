package com.tpov.schoolquiz.presentation.mainactivity

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.databinding.TitleFragmentBinding
import com.tpov.schoolquiz.databinding.TitleFragmentBinding.*
import com.tpov.schoolquiz.presentation.MainApp
import com.tpov.schoolquiz.presentation.dialog.CreateQuestionDialog
import com.tpov.schoolquiz.presentation.dialog.CreateQuestionSecondDialog
import com.tpov.schoolquiz.presentation.factory.ViewModelFactory
import com.tpov.schoolquiz.presentation.fragment.BaseFragment
import com.tpov.schoolquiz.presentation.fragment.FragmentManager
import com.tpov.schoolquiz.presentation.question.QuestionActivity
import com.tpov.schoolquiz.presentation.question.QuestionViewModel
import com.tpov.shoppinglist.utils.ShareHelper
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
class FragmentMain : BaseFragment(), MainActivityAdapter.Listener {

    private lateinit var questionViewModel: QuestionViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        (requireActivity().application as MainApp).component
    }

    private lateinit var adapter: MainActivityAdapter

    private lateinit var binding: TitleFragmentBinding
    private var createQuiz = false

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
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
                            questionViewModel.getQuiz.observe(this@FragmentMain) {
                                if (startObs == getString(R.string.dialog_short_text_delete)) {
                                    it.forEach { item ->
                                        if (stars == item.id) nameQuiz = item.nameQuestion
                                    }
                                    questionViewModel.deleteQuiz(id, nameTypeQuestion, nameQuiz)
                                }
                            }
                        }
                        getString(R.string.dialog_text_share) -> {
                            var nameQuiz = ""
                            val startObs = getString(R.string.dialog_short_text_share)
                            questionViewModel.getQuiz.observe(this@FragmentMain) {
                                if (startObs == getString(R.string.dialog_short_text_share)) {
                                    it.forEach { item ->
                                        if (stars == item.id) nameQuiz = item.nameQuestion
                                    }
                                }
                            }
                            questionViewModel.getQuestion.observe(this@FragmentMain) {
                                startActivity(
                                    Intent.createChooser(
                                        ShareHelper.shareShopList(nameQuiz, it, nameTypeQuestion),
                                        "Share by"
                                    )
                                )
                            }
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

                                questionViewModel.getQuiz.observe(viewLifecycleOwner) {
                                    adapter.submitList(it)
                                }
                            }
                        },
                    )
                }
            },
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionViewModel = ViewModelProvider(this, viewModelFactory)[QuestionViewModel::class.java]
        adapter = MainActivityAdapter(this@FragmentMain)
        binding.rcView.layoutManager = LinearLayoutManager(activity)
        binding.rcView.adapter = adapter

        questionViewModel.getQuiz.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
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