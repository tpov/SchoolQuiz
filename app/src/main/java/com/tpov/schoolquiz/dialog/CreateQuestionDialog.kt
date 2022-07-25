package com.tpov.schoolquiz.dialog

import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.activity.MainActivity
import com.tpov.schoolquiz.databinding.CreateQuestionDialogBinding
import kotlinx.coroutines.InternalCoroutinesApi

object CreateQuestionDialog {
    @InternalCoroutinesApi
    fun showDialog(
        activity: MainActivity,
        nameQuiz: String,
        closeDialog: Boolean,
        listener: Listener
    ) {
        val viewModel by lazy {
            ViewModelProvider(activity)[CreateQuestionDialiogViewModel::class.java]
        }

        var numQuestion = 0
        var Answer = false
        var hardQuestion = false

        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(activity)
        val binding = CreateQuestionDialogBinding.inflate(LayoutInflater.from(activity))
        builder.setView(binding.root)
        addTextChangeListeners(binding, viewModel)

        binding.apply {
            if (closeDialog) dialog?.dismiss()
            when (nameQuiz) {
                "" -> {
                    clickAll(binding, false)
                    tvQuestion.setHint(R.string.new_question)
                    tvQuestion.setText("")
                    num.text = numQuestion.toString()

                    bNext.setOnClickListener {
                        val fieledsValid = viewModel.validateInput(tvQuestion.text.toString())
                        if (fieledsValid) {
                            listener.onClick(
                                "",
                                tvQuestion.text.toString(),
                                false,
                                hardQuestion,
                                numQuestion,
                                false,
                                ""
                            )
                            clearDialog(binding)
                            numQuestion++
                            dialog?.dismiss()
                        }
                    }
                }
                "deleteQuiz" -> {
                    CheckBox.text = "Delete all question?"
                    bNext.text = "DELETE"
                    CheckBox.setOnClickListener {
                        hardQuestion = CheckBox.isChecked
                    }

                    clickAll(binding, false)
                    binding.CheckBox.visibility = View.VISIBLE
                    binding.num.visibility = View.GONE

                    tvQuestion.setText("Delete quiz?")
                    tvQuestion.setRawInputType(0x00000000)
                    bNext.setOnClickListener {
                        listener.onClick(
                            "",
                            "",
                            false,
                            hardQuestion,
                            0,
                            false,
                            "Delete quiz?"
                        )
                        dialog?.dismiss()
                    }
                }
                "shareQuiz" -> {
                    CheckBox.text = "Show answer?"
                    bNext.text = "SHARE"
                    CheckBox.setOnClickListener {
                        hardQuestion = CheckBox.isChecked
                    }

                    clickAll(binding, false)
                    binding.CheckBox.visibility = View.VISIBLE
                    binding.num.visibility = View.GONE

                    tvQuestion.setText("Share quiz")
                    tvQuestion.setRawInputType(0x00000000)
                    num.text = numQuestion.toString()

                    bNext.setOnClickListener {
                        listener.onClick(
                            "",
                            "",
                            false,
                            hardQuestion,
                            0,
                            false,
                            "Share quiz"
                        )
                        dialog?.dismiss()
                    }
                }
                else -> {
                    clickAll(binding, false)
                    tvQuestion.setHint(R.string.name)
                    tvQuestion.setText("")
                    num.visibility = View.GONE
                    bNext.text = "GO"

                    bNext.setOnClickListener {
                        listener.onClick(
                            "",
                            tvQuestion.text.toString(),
                            false,
                            hardQuestion,
                            numQuestion,
                            false,
                            nameQuiz
                        )
                        clearDialog(binding)
                        numQuestion++
                        dialog?.dismiss()
                    }
                }
            }
        }

        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    private fun addTextChangeListeners(
        binding: CreateQuestionDialogBinding,
        viewModel: CreateQuestionDialiogViewModel
    ) {
        binding.tvQuestion.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun clearDialog(binding: CreateQuestionDialogBinding) {
        binding.apply {
            tvQuestion.setText("")
            CheckBox.isChecked = false
            tvUnswer.text = ""
            tvCheckBox.text = ""
        }
    }

    private fun clickAll(binding: CreateQuestionDialogBinding, type: Boolean) {
        if (!type) {
            binding.bTrue.visibility = View.GONE
            binding.bFalse.visibility = View.GONE

            binding.bEnd.visibility = View.GONE
            binding.CheckBox.visibility = View.GONE
            binding.tvUnswer.visibility = View.GONE
            binding.tvCheckBox.visibility = View.GONE
            binding.bClear.visibility = View.GONE
        } else {

            binding.bTrue.visibility = View.VISIBLE
            binding.bFalse.visibility = View.VISIBLE

            binding.bEnd.visibility = View.VISIBLE
            binding.CheckBox.visibility = View.VISIBLE
            binding.tvUnswer.visibility = View.VISIBLE
            binding.tvCheckBox.visibility = View.VISIBLE
            binding.bClear.visibility = View.VISIBLE
        }
    }

    interface Listener {
        fun onClick(
            listNameQuestion: String,
            listUserName: String,
            nameAnswerQuestion: Boolean,
            nameTypeQuestion: Boolean,
            numQuestion: Int,
            closeDialog: Boolean,
            name: String
        )
    }
}