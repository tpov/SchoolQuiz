package com.tpov.geoquiz.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tpov.geoquiz.Question

@Entity(tableName = "front_list")
data class FrontList (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "nameQuestion")
    val nameQuestion: String,

    @ColumnInfo(name = "user_name")
    val userName: String,

    @ColumnInfo(name = "data")
    val data: String,

    @ColumnInfo(name = "stars")
    val stars: Int,

    @ColumnInfo(name = "numQ")
    val numQ: Int,

    @ColumnInfo(name = "numA")
    val numA: Int,

    @ColumnInfo(name = "numHQ")
    val numHQ: Int,

    @ColumnInfo(name = "starsAll")
    val starsAll: Int
)