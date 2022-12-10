package com.tpov.schoolquiz.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tpov.schoolquiz.data.database.entities.QuizDetail
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.ApiQuestion
import com.tpov.schoolquiz.data.database.entities.Quiz
import kotlinx.coroutines.InternalCoroutinesApi

@Database(
    entities = [QuizDetail::class, Question::class, Quiz::class, ApiQuestion::class],
    version = 5,
    exportSchema = true, autoMigrations = [AutoMigration(from = 4, to = 5)]
)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun getQuizDao(): QuizDao

    companion object {
        @Volatile                                                 //Дает доступ к остальным потокам
        var INSTANCE: QuizDatabase? = null

        @InternalCoroutinesApi
        fun getDatabase(context: Context): QuizDatabase {        //Создаем файл базы данных
            return INSTANCE
                ?: synchronized(this) {          //synchronized - Обеспечивает запуск только в одном потоке
                    val instance = Room.databaseBuilder(
                        context.applicationContext,               //applicationContext - Так как мы используем во всех активити
                        QuizDatabase::class.java,
                        "GeoQuiz_list.db"
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                    instance
                }
        }
    }
}