package com.tpov.geoquiz

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tpov.geoquiz.activity.MainActivity
import com.tpov.geoquiz.activity.MainApp
import com.tpov.geoquiz.database.CustomRecyclerAdapter
import com.tpov.geoquiz.database.MainViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import android.content.Intent as Intent1

const val EXTRA_UPDATE_CURRENT_INDEX = "com.tpov.geoquiz.update_current_index"
const val EXTRA_CURRENT_INDEX = "com.tpov.geoquiz.current_index"
const val EXTRA_CODE_ANSWER = "com.tpov.geoquiz.code_answer"
const val EXTRA_CODE_ID_USER = "com.tpov.geoquiz.code_question_bank"

@InternalCoroutinesApi
class QuestionActivity : AppCompatActivity(), CustomRecyclerAdapter.UpdateData {
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
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
        Log.d("QuestionActivity", "map1 = ${QUESTION_BANK_ADAPTER.map { (it.textResId) }}")

        mainViewModel.allCrimeNewQuiz.observe(this, {
            it.forEach { item ->
                Log.d("QuestionActivity", "idUser ${idUser}")
                Log.d("QuestionActivity", "item.idListNameQuestion ${item.idListNameQuestion}")

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
        mainViewModel.getQuestionCrimeNewQuiz()
    }

    fun funIntent(
        recyclerView: RecyclerView,
        currentIndex2: Int,
        codeAnswer2: String,
        idUser: String,
        codeAnswer2Array: MutableList<Char>,
        QUESTION_BANK_ADAPTER: MutableList<Quiz>,
    ) {

        Log.d("QuestionActivity", "map1 = ${QUESTION_BANK_ADAPTER.map { (it.textResId) }}")

        Log.d(
            "QuestionActivity",
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