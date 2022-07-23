package com.tpov.schoolquiz.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "new_user_table")
data class Question(
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
