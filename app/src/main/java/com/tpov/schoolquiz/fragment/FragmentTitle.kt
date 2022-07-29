import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tpov.schoolquiz.presentation.mainactivity.MainActivity
import com.tpov.schoolquiz.presentation.MainApp
import com.tpov.schoolquiz.presentation.question.QuestionViewModel
import com.tpov.schoolquiz.TitleAdapter
import com.tpov.schoolquiz.databinding.TitleFragmentBinding
import com.tpov.schoolquiz.databinding.TitleFragmentBinding.*
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.fragment.BaseFragment
import com.tpov.schoolquiz.fragment.FragmentManager
import com.tpov.schoolquiz.dialog.CreateQuestionDialog
import com.tpov.schoolquiz.dialog.CreateQuestionSecondDialog
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.presentation.question.QuestionActivity
import com.tpov.shoppinglist.utils.ShareHelper
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FragmentTitle: BaseFragment(), TitleAdapter.Listener {

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
                        "" -> {
                            createQuiz = true
                            startSecondDialog(listUserName, listNameQuestion)
                        }
                        "Delete quiz?" -> {
                            var nameQuiz = ""
                            val startObs = "delete"
                            Log.d("Delete quiz?", "1")
                            questionViewModel.getQuiz.observe(this@FragmentTitle, {
                                if (startObs == "delete") {
                                    Log.d("Delete quiz?", "2")
                                    it.forEach { item ->
                                        Log.d("Delete quiz?", "3")
                                        if (stars == item.id) nameQuiz = item.nameQuestion
                                    }
                                    Log.d("Delete quiz?", "$id, $nameTypeQuestion, $nameQuiz")
                                    questionViewModel.deleteQuiz(id, nameTypeQuestion, nameQuiz)
                                }
                            })
                        }
                        "Share quiz" -> {
                            var nameQuiz = ""
                            val startObs = "Share"
                            questionViewModel.getQuiz.observe(this@FragmentTitle, {
                                if (startObs == "Share") {
                                    it.forEach { item ->
                                        if (stars == item.id) nameQuiz = item.nameQuestion
                                    }
                                }

                            })
                            questionViewModel.getQuestion.observe(this@FragmentTitle, {

                                startActivity(Intent.createChooser(
                                    ShareHelper.shareShopList(nameQuiz, it, nameTypeQuestion),
                                    "Share by"))
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

                fun startSecondDialog(listNameQuestionSecond: String, listNameQuestion: String) {
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
        Log.d("onViewCreated", "onViewCreated")

        questionViewModel.getQuiz.observe(viewLifecycleOwner, {
            val adapter = TitleAdapter(this@FragmentTitle)
            adapter.submitList(it)
            binding.rcView.layoutManager = LinearLayoutManager(activity)
            binding.rcView.adapter = adapter

            Log.d("onViewCreated", "$it")
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        /*if (item.itemId == R.id.ListQuestion_Button) {
            FragmentManager.currentFrag?.onClickNew("", stars)
        }*/
        return super.onOptionsItemSelected(item)
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
        0)
        questionViewModel.insertQuiz(frontList)
        if (createQuiz) questionViewModel.insertQuestion(nameList)

        createQuiz = false
    }
    //Тег по которому диалог определяет что нужно именно сделать с квестом. И id самого квеста
    override fun deleteItem(id: Int) {
        FragmentManager.currentFrag?.onClickNew("deleteQuiz", id)
    }

    override fun onClick(name: String, stars: Int) {
        FragmentManager.currentFrag?.onClickNew(name, stars)
    }

    override fun shareItem(name: String, stars: Int) {
        FragmentManager.currentFrag?.onClickNew("shareQuiz", stars)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentTitle()
    }
}