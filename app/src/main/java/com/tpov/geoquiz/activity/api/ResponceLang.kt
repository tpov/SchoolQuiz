package com.tpov.geoquiz.activity.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponceLang(

    @SerializedName("text")
    @Expose
    private val text: String,

    @SerializedName("code")
    @Expose
    private val code: Int

) {
    fun getText(): String {
        return text
    }
    fun getCode(): Int {
        return  code
    }
}