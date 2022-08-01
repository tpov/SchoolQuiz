package com.tpov.schoolquiz.presentation.question

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tpov.schoolquiz.data.model.Quiz
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.presentation.mainactivity.MainActivity
import com.tpov.schoolquiz.presentation.MainApp
import com.tpov.schoolquiz.presentation.factory.ViewModelFactory
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject
import android.content.Intent as Intent1

const val EXTRA_UPDATE_CURRENT_INDEX = "com.tpov.geoquiz.update_current_index"
const val EXTRA_CURRENT_INDEX = "com.tpov.geoquiz.current_index"
const val EXTRA_CODE_ANSWER = "com.tpov.geoquiz.code_answer"
const val EXTRA_CODE_ID_USER = "com.tpov.geoquiz.code_question_bank"

// TODO: 25.07.2022 QuestionListActivity -> QuestionListFragment
@InternalCoroutinesApi
class QuestionListActivity : AppCompatActivity(), QuestionListRecyclerAdapter.UpdateData {

    private lateinit var questionViewModel: QuestionViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as MainApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_question)
        questionViewModel = ViewModelProvider(this, viewModelFactory)[QuestionViewModel::class.java]

        var questionBankAdapter = mutableListOf<Quiz>()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        intent
        val currentIndex: Int = intent.getIntExtra(EXTRA_CURRENT_INDEX, 0).toInt()
        val codeAnswer: String = intent.getStringExtra(EXTRA_CODE_ANSWER)!!
        val idUser: String = intent.getStringExtra(EXTRA_CODE_ID_USER)!!
        val codeAnswerArray = codeAnswer.toMutableList()
        questionBankAdapter.clear()
        Log.d("QuestionListActivity", "map1 = ${questionBankAdapter.map { (it.textResId) }}")

        questionViewModel.getQuestion.observe(this, {
            it.forEach { item ->

                if (idUser == item.idListNameQuestion) {
                    if (!item.typeQuestion) questionBankAdapter.add(
                        Quiz(
                            item.nameQuestion,
                            item.answerQuestion
                        )
                    )
                }
            }
            funIntent(
                recyclerView,
                currentIndex,
                codeAnswerArray,
                questionBankAdapter
            )
        })
    }

    private fun funIntent(
        recyclerView: RecyclerView,
        currentIndex2: Int,
        codeAnswer2Array: MutableList<Char>,
        QUESTION_BANK_ADAPTER: MutableList<Quiz>,
    ) {
        recyclerView.adapter = QuestionListRecyclerAdapter(
            QUESTION_BANK_ADAPTER.map { (it.textResId) },
            this,
            codeAnswer2Array,
            currentIndex2,
            QUESTION_BANK_ADAPTER
        )
    }

    override fun closeList(position: Int) {

        val intent1 = Intent1(this, MainActivity::class.java)
        intent1.putExtra(EXTRA_UPDATE_CURRENT_INDEX, position)
        setResult(position, intent1)
        finish()
    }
}