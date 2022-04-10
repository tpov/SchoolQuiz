package com.tpov.geoquiz.activity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class ListFrontList(val id: Int?,
                         val nameQuestion: String,
                         val userName: String,
                         val data: String,
                         val stars: Int)
