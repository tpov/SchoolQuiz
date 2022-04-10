package com.tpov.geoquiz.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_data")

data class Crime(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "idNameQuiz")
    val idNameQuiz: String,

    @ColumnInfo(name = "userName")
    val userName: String?,

    @ColumnInfo(name = "data")
    val data: String,

    @ColumnInfo(name = "codeAnswer")
    val codeAnswer: String? ,

    @ColumnInfo(name = "codeMap")
    val codeMap: String? ,

    @ColumnInfo(name = "currentIndex")
    val currentIndex: Int,

    @ColumnInfo(name = "isCheater")
    val isCheater: Boolean,

    @ColumnInfo(name = "constCurrentIndex")
    val constCurrentIndex: Int ,

    @ColumnInfo(name = "points")
    val points: Int,

    @ColumnInfo(name = "persentPoints")
    val persentPoints: Int,

    @ColumnInfo(name = "cheatPoints")
    val cheatPoints: Int,

    @ColumnInfo(name = "charMap")
    val charMap: String?,

    @ColumnInfo(name = "i")
    val i: Int,

    @ColumnInfo(name = "j")
    val j: Int,

    @ColumnInfo(name = "updateAnswer")
    val updateAnswer: Boolean,

    @ColumnInfo(name = "leftUnswer")
    val leftUnswer: Int?,

    @ColumnInfo(name = "numQuestion")
    val numQuestion: Int?,

    @ColumnInfo(name = "numAnswer")
    val numAnswer: Int?
)