package com.tpov.geoquiz.dialog

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.tpov.geoquiz.R
import com.tpov.geoquiz.databinding.CreateQuestionDialogBinding
import com.tpov.geoquiz.entities.FrontList

object CreateQuestionDialog {
    fun showDialog(context: Context, nameQuiz: String, closeDialog: Boolean, listener: Listener) {
        var numQuestion = 0
        var unswer = false
        var hardQuestion = false

        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = CreateQuestionDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)

        binding.apply {
            if (closeDialog) dialog?.dismiss()
            when (nameQuiz) {
                "" -> {
                    clickAll(binding, false)
                    tvQuestion.setHint(R.string.new_question)
                    tvQuestion.setText("")
                    num.text = numQuestion.toString()

                    bNext.setOnClickListener {
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
                "deleteQuiz" -> {
                    CheckBox.text = "Delete all question?"
                    bNext.text = "DELETE"
                    CheckBox.setOnClickListener {
                        hardQuestion = CheckBox.isChecked
                    }

                    clickAll(binding, false)
                    binding.CheckBox.visibility = View.VISIBLE
                    binding.num.visibility= View.GONE

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
                    binding.num.visibility= View.GONE

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
        fun onClick(listNameQuestion: String,
                    listUserName: String,
                    nameAnswerQuestion: Boolean,
                    nameTypeQuestion: Boolean,
                    numQuestion: Int,
                    closeDialog: Boolean,
                    name: String
        )
    }
}