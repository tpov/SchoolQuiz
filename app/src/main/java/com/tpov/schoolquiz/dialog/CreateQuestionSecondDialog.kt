package com.tpov.schoolquiz.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.databinding.CreateQuestionDialogBinding

object CreateQuestionSecondDialog {
    fun showDialog(context: Context, listener: Listener) {
        var numQuestion = 1
        var unswer = false
        var hardQuestion = false

        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = CreateQuestionDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)

        binding.apply {
            clearDialog(binding, numQuestion)

            bClear.setOnClickListener {
                clearDialog(binding, numQuestion)
            }
            bFalse.setOnClickListener {
                unswer = false
                clickButton(binding, false)
                tvUnswer.setText(R.string.false_button)
            }
            bTrue.setOnClickListener {
                unswer = true
                clickButton(binding, false)
                tvUnswer.setText(R.string.true_button)
            }

            CheckBox.setOnClickListener {
                if (CheckBox.isChecked) {
                    hardQuestion = true
                    tvCheckBox.setText(R.string.hard_question)
                } else {
                    hardQuestion = false
                    tvCheckBox.setText(R.string.light_question)
                }
            }

            bNext.setOnClickListener {
                listener.onClick(
                    tvQuestion.text.toString(),
                    "",
                    unswer,
                    hardQuestion,
                    numQuestion,
                    false
                )

                numQuestion++
                hardQuestion = false
                clearDialog(binding, numQuestion)
                num.text = numQuestion.toString()
            }

            bEnd.setOnClickListener {
                listener.onClick(
                    tvQuestion.text.toString(),
                    "",
                    unswer,
                    hardQuestion,
                    numQuestion,
                    true
                )
                dialog?.dismiss()
            }
        }

        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()

    }

    private fun clearDialog(binding: CreateQuestionDialogBinding, numQuestion: Int) {
        clickButton(binding, true)

        binding.apply {
            tvQuestion.setHint(R.string.question)
            tvQuestion.setText("")
            num.text = numQuestion.toString()

            tvQuestion.setText("")
            CheckBox.isChecked = false
            tvUnswer.text = ""
            tvCheckBox.setText(R.string.light_question)
        }
    }

    private fun clickButton(binding: CreateQuestionDialogBinding, type: Boolean) {
        if (!type) {
            binding.bTrue.isEnabled = false
            binding.bFalse.isEnabled = false
            binding.bTrue.isClickable = false
            binding.bFalse.isClickable = false

            binding.bNext.isEnabled = true
            binding.bNext.isClickable = true
        } else {
            binding.bTrue.isEnabled = true
            binding.bFalse.isEnabled = true
            binding.bTrue.isClickable = true
            binding.bFalse.isClickable = true

            binding.bNext.isEnabled = false
            binding.bNext.isClickable = false
        }
    }

    interface Listener {
        fun onClick(
            listNameQuestion: String,
            listUserName: String,
            nameAnswerQuestion: Boolean,
            nameTypeQuestion: Boolean,
            numQuestion: Int,
            closeDialog: Boolean
        )
    }
}