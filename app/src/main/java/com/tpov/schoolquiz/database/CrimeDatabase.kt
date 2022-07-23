package com.tpov.schoolquiz.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tpov.schoolquiz.entities.QuizDetail
import com.tpov.schoolquiz.entities.Question
import com.tpov.schoolquiz.entities.ApiQuestion
import com.tpov.schoolquiz.entities.Quiz
import kotlinx.coroutines.InternalCoroutinesApi

@Database(
    entities = [QuizDetail::class, Question::class, Quiz::class, ApiQuestion::class],
    version = 3,
    exportSchema = true, autoMigrations = [AutoMigration(from = 1, to = 3)]
)
abstract class CrimeDatabase : RoomDatabase() {
    abstract fun getCrimeDao(): CrimeDao

    companion object {
        @Volatile                                                 //Дает доступ к остальным потокам
        var INSTANCE: CrimeDatabase? = null

        @InternalCoroutinesApi
        fun getDatabase(context: Context): CrimeDatabase {        //Создаем файл базы данных
            return INSTANCE
                ?: synchronized(this) {          //synchronized - Обеспечивает запуск только в одном потоке
                    val instance = Room.databaseBuilder(
                        context.applicationContext,               //applicationContext - Так как мы используем во всех активити
                        CrimeDatabase::class.java,
                        "GeoQuiz_list.db"
                    ).fallbackToDestructiveMigration().build()
                    instance
                }
        }
    }
}