package com.tpov.schoolquiz.presentation.mainactivity

import androidx.lifecycle.ViewModel
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.data.model.Question
import com.tpov.shoppinglist.utils.TimeManager

fun quiz(
    listNameQuestion: String,
    listUserName: String
) = Quiz(
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

fun ViewModel.questionBank() = listOf(
    Question(R.string.question_light1, true, false),
    Question(R.string.question_light2, true, false),
    Question(R.string.question_light3, false, false),
    Question(R.string.question_light4, false, false),
    Question(R.string.question_light5, true, false),
    Question(R.string.question_light6, true, false),
    Question(R.string.question_light7, true, false),
    Question(R.string.question_light8, true, false),
    Question(R.string.question_light9, false, false),
    Question(R.string.question_light10, true, false),
    Question(R.string.question_light11, true, false),
    Question(R.string.question_light12, true, false),
    Question(R.string.question_light13, false, false),
    Question(R.string.question_light14, true, false),
    Question(R.string.question_light15, false, false),
    Question(R.string.question_light16, true, false),
    Question(R.string.question_light17, true, false),
    Question(R.string.question_light18, true, false),
    Question(R.string.question_light19, false, false),
    Question(R.string.question_light20, false, false),
    Question(R.string.question_hard1, false, true),
    Question(R.string.question_hard2, false, true),
    Question(R.string.question_hard3, false, true),
    Question(R.string.question_hard4, true, true),
    Question(R.string.question_hard5, true, true),
    Question(R.string.question_hard6, false, true),
    Question(R.string.question_hard7, false, true),
    Question(R.string.question_hard8, true, true),
    Question(R.string.question_hard9, false, true),
    Question(R.string.question_hard10, false, true),
    Question(R.string.question_hard11, true, true),
    Question(R.string.question_hard12, false, true),
    Question(R.string.question_hard13, false, true),
    Question(R.string.question_hard14, false, true),
    Question(R.string.question_hard15, true, true),
    Question(R.string.question_hard16, false, true),
    Question(R.string.question_hard17, true, true),
    Question(R.string.question_hard18, true, true),
    Question(R.string.question_hard19, false, true),
    Question(R.string.question_hard20, false, true)
)