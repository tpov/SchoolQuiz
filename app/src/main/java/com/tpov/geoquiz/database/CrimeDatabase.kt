package com.tpov.geoquiz.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tpov.geoquiz.entities.Crime
import com.tpov.geoquiz.entities.CrimeNewQuiz
import com.tpov.geoquiz.entities.EntityGenerateQuestion
import com.tpov.geoquiz.entities.FrontList
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [Crime::class, CrimeNewQuiz::class, FrontList::class, EntityGenerateQuestion::class], version = 1, exportSchema = true)      //, autoMigrations = [AutoMigration(from = 1, to = 2)]
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