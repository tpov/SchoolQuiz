package com.tpov.schoolquiz.presentation.question

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tpov.schoolquiz.data.model.Quiz
import com.tpov.schoolquiz.CustomRecyclerAdapter
import com.tpov.schoolquiz.presentation.question.QuestionViewModel
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.activity.MainActivity
import com.tpov.schoolquiz.activity.MainApp
import kotlinx.coroutines.InternalCoroutinesApi
import android.content.Intent as Intent1

const val EXTRA_UPDATE_CURRENT_INDEX = "com.tpov.geoquiz.update_current_index"
const val EXTRA_CURRENT_INDEX = "com.tpov.geoquiz.current_index"
const val EXTRA_CODE_ANSWER = "com.tpov.geoquiz.code_answer"
const val EXTRA_CODE_ID_USER = "com.tpov.geoquiz.code_question_bank"

// TODO: 25.07.2022 QuestionListActivity -> QuestionListFragment
@InternalCoroutinesApi
class QuestionListActivity : AppCompatActivity(), CustomRecyclerAdapter.UpdateData {
    private val questionViewModel: QuestionViewModel by viewModels {
        QuestionViewModel.QuizModelFactory((applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        var QUESTION_BANK_ADAPTER = mutableListOf<Quiz>()
        var idUser = ""
        var codeAnswer2 = "00000000000000000"
        var currentIndex2: Int = 0

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        intent
        currentIndex2 = intent.getIntExtra(EXTRA_CURRENT_INDEX, 0).toInt()
        codeAnswer2 = intent.getStringExtra(EXTRA_CODE_ANSWER)!!
        idUser = intent.getStringExtra(EXTRA_CODE_ID_USER)!!
        var codeAnswer2Array = codeAnswer2.toMutableList()
        QUESTION_BANK_ADAPTER.clear()
        Log.d("QuestionListActivity", "map1 = ${QUESTION_BANK_ADAPTER.map { (it.textResId) }}")

        questionViewModel.getQuestion.observe(this, {
            it.forEach { item ->
                Log.d("QuestionListActivity", "idUser ${idUser}")
                Log.d("QuestionListActivity", "item.idListNameQuestion ${item.idListNameQuestion}")

                if (idUser == item.idListNameQuestion) {
                    if (!item.typeQuestion) QUESTION_BANK_ADAPTER.add(
                        Quiz(
                            item.nameQuestion,
                            item.answerQuestion
                        )
                    )
                }
            }
            funIntent(
                recyclerView,
                currentIndex2,
                codeAnswer2,
                idUser,
                codeAnswer2Array,
                QUESTION_BANK_ADAPTER
            )
        })
    }

    private fun funIntent(
        recyclerView: RecyclerView,
        currentIndex2: Int,
        codeAnswer2: String,
        idUser: String,
        codeAnswer2Array: MutableList<Char>,
        QUESTION_BANK_ADAPTER: MutableList<Quiz>,
    ) {

        Log.d("QuestionListActivity", "map1 = ${QUESTION_BANK_ADAPTER.map { (it.textResId) }}")

        Log.d(
            "QuestionListActivity",
            "adapter = ${QUESTION_BANK_ADAPTER.map { (it.textResId) }}, ${codeAnswer2Array}, ${currentIndex2}, ${QUESTION_BANK_ADAPTER}"
        )
        recyclerView.adapter = CustomRecyclerAdapter(
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