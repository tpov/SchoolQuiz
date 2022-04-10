package com.tpov.geoquiz.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tpov.geoquiz.Question


@Entity(tableName = "new_user_table")
data class CrimeNewQuiz(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "nameQuestion")
    val nameQuestion: String,

    @ColumnInfo(name = "answerQuestion")
    val answerQuestion: Boolean,

    @ColumnInfo(name = "typeQuestion")
    val typeQuestion: Boolean,

    @ColumnInfo(name = "idListNameQuestion")
    val idListNameQuestion: String,
)
