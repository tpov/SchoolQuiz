package com.tpov.schoolquiz.data.model

data class Profile(
    val tpovId: Int,
    val login: String,
    val name: String,
    val birthday: String,
    val points: Points,
    val datePremium: String,
    val buy: Buy,
    val trophy: String,
    val friends: String,
    val city: String,
    val logo: Int,
    val timeInGames: TimeInGames
)

data class TimeInGames(
    val allTime: String,
    val timeInQuiz: String,
    val timeInChat: String,
    val smsPoints: Int
)

data class Buy(
    val heart: Byte,
    val goldHeart: Byte,
    val quizPlace: Byte,
    val theme: String,
    val music: String,
    val logo: String
)

data class Points(
    val gold: Int,
    val skill: Double,
    val skillInSesone: Double,
    val nolics: Int
)
