import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tpov.geoquiz.activity.MainActivity
import com.tpov.geoquiz.activity.MainApp
import com.tpov.geoquiz.database.MainViewModel
import com.tpov.geoquiz.database.TitleAdapter
import com.tpov.geoquiz.databinding.TitleFragmentBinding
import com.tpov.geoquiz.databinding.TitleFragmentBinding.*
import com.tpov.geoquiz.entities.CrimeNewQuiz
import com.tpov.geoquiz.fragment.BaseFragment
import com.tpov.geoquiz.fragment.FragmentManager
import com.tpov.geoquiz.dialog.CreateQuestionDialog
import com.tpov.geoquiz.dialog.CreateQuestionSecondDialog
import com.tpov.geoquiz.entities.FrontList
import com.tpov.shoppinglist.utils.ShareHelper
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FragmentTitle: BaseFragment(), TitleAdapter.Listener {

    private lateinit var binding: TitleFragmentBinding
    //private lateinit var adapter: TitleAdapter


    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }


    override fun onClickNew(name: String, stars: Int) {
        //var closeDialog = mainViewModel.isGeoQuizFrontnoSuspend()
        var closeDialog = false
        CreateQuestionDialog.showDialog(
            activity as AppCompatActivity, name, closeDialog,
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
                            insertFrontList(listUserName, listNameQuestion)
                            startSecondDialog(listUserName)
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

                fun startSecondDialog(listNameQuestionSecond: String) {
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
                                val nameList = CrimeNewQuiz(
                                    null,
                                    nameQuestion,
                                    nameAnswerQuestion,
                                    nameTypeQuestion,
                                    listNameQuestionSecond,
                                )
                                mainViewModel.insertCrimeNewQuiz(nameList)
                            }
                        },
                    )
                }
            },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("onViewCreated", "onViewCreated")

        mainViewModel.allFrontListAdapter.observe(viewLifecycleOwner, {
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
    fun insertFrontList(listNameQuestion: String, listUserName: String) {
        val frontList = FrontList(
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
    }
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