import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tpov.schoolquiz.activity.FrontActivity
import com.tpov.schoolquiz.activity.MainActivity
import com.tpov.schoolquiz.activity.MainApp
import com.tpov.schoolquiz.MainViewModel
import com.tpov.schoolquiz.TitleAdapter
import com.tpov.schoolquiz.databinding.TitleFragmentBinding
import com.tpov.schoolquiz.databinding.TitleFragmentBinding.*
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.fragment.BaseFragment
import com.tpov.schoolquiz.fragment.FragmentManager
import com.tpov.schoolquiz.dialog.CreateQuestionDialog
import com.tpov.schoolquiz.dialog.CreateQuestionSecondDialog
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.shoppinglist.utils.ShareHelper
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FragmentTitle: BaseFragment(), TitleAdapter.Listener {

    private lateinit var binding: TitleFragmentBinding
    //private lateinit var adapter: TitleAdapter
    private var createQuiz = false

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }


    override fun onClickNew(name: String, stars: Int) {

        var closeDialog = false
        CreateQuestionDialog.showDialog(
            activity as FrontActivity, name, closeDialog,
            object : CreateQuestionDialog.Listener {
                override fun onClick(
                    listNameQuestion: String,
                    listUserName: String,
                    nameAnswerQuestion: Boolean,
                    nameTypeQuestion: Boolean,
                    numQuestion: Int,
                    closeDialog: Boolean,
                    nameQuiz: String,

                ) {
                    when (nameQuiz) {
                        "" -> {
                            createQuiz = true
                            startSecondDialog(listUserName, listNameQuestion)
                        }
                        "Delete quiz?" -> {
                            var nameQuiz = ""
                            var startObs = "delete"
                            Log.d("Delete quiz?", "1")
                            mainViewModel.getFrontList()
                            mainViewModel.allFrontList.observe(this@FragmentTitle, {
                                if (startObs == "delete") {
                                    Log.d("Delete quiz?", "2")
                                    it.forEach { item ->
                                        Log.d("Delete quiz?", "3")
                                        if (stars == item.id) nameQuiz = item.nameQuestion
                                    }
                                    Log.d("Delete quiz?", "$id, $nameTypeQuestion, $nameQuiz")
                                    mainViewModel.deleteFrontList(id, nameTypeQuestion, nameQuiz)
                                }
                            })
                        }
                        "Share quiz" -> {
                            var nameQuiz = ""
                            var startObs = "Share"
                            mainViewModel.getFrontList()
                            mainViewModel.allFrontList.observe(this@FragmentTitle, {
                                if (startObs == "Share") {
                                    it.forEach { item ->
                                        if (stars == item.id) nameQuiz = item.nameQuestion
                                    }
                                }

                                mainViewModel.getQuestionCrimeNewQuiz()
                            })
                            mainViewModel.allCrimeNewQuiz.observe(this@FragmentTitle, {

                                startActivity(Intent.createChooser(
                                    ShareHelper.shareShopList(nameQuiz, it, nameTypeQuestion),
                                    "Share by"))
                            })
                        }
                        else -> {
                            val intent = Intent(activity, MainActivity::class.java)
                            intent.putExtra(MainActivity.NAME_QUESTION, nameQuiz)
                            intent.putExtra(MainActivity.NAME_USER, listUserName)
                            intent.putExtra(MainActivity.STARS, stars.toString())
                            startActivity(intent)
                        }
                    }
                }

                fun startSecondDialog(listNameQuestionSecond: String, listNameQuestion: String) {
                    CreateQuestionSecondDialog.showDialog(
                        activity as AppCompatActivity,
                        object : CreateQuestionSecondDialog.Listener {
                            override fun onClick(
                                nameQuestion: String,
                                listUserName: String,
                                nameAnswerQuestion: Boolean,
                                nameTypeQuestion: Boolean,
                                numQuestion: Int,
                                closeDialog: Boolean
                            ) {
                                val nameList = Question(
                                    null,
                                    nameQuestion,
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

        mainViewModel.allQuizAdapter.observe(viewLifecycleOwner, {
            val adapter = TitleAdapter(this@FragmentTitle)
            adapter.submitList(it)
            binding.rcView.layoutManager = LinearLayoutManager(activity)
            binding.rcView.adapter = adapter

            Log.d("onViewCreated", "${it.toString()}")
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        mainViewModel.insertFrontList(frontList)
        if (createQuiz) mainViewModel.insertCrimeNewQuiz(nameList)

        createQuiz = false
    }
    //Тег по которому диалог определяет что нужно именно сделать с квестом. И id самого квеста
    override fun deleteItem(id: Int) {
        FragmentManager.currentFrag?.onClickNew("deleteQuiz", id)
    }

    override fun onClick(nameQuiz: String, stars: Int) {
        FragmentManager.currentFrag?.onClickNew(nameQuiz, stars)
    }

    override fun shareItem(name: String, id: Int) {
        FragmentManager.currentFrag?.onClickNew("shareQuiz", id)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentTitle()
    }
}