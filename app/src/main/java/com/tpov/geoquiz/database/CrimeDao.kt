package com.tpov.geoquiz.database

import androidx.room.Dao
import androidx.room.Insert
import com.tpov.geoquiz.entities.Crime
import androidx.room.Query
import androidx.room.Update
import com.tpov.geoquiz.entities.CrimeNewQuiz
import com.tpov.geoquiz.entities.FrontList
import kotlinx.coroutines.flow.Flow

@Dao

interface CrimeDao {
    @Insert
    suspend fun insertAll(note: Crime)
    @Insert
    suspend fun insertAllFrontList(note: FrontList)
    @Insert
    suspend fun insertCrimeNewQuiz(name: CrimeNewQuiz)

    @Query("SELECT * FROM table_data")
    fun getAllCrime() : Flow<List<Crime>>     //Запускаем через Корутину
    @Query("SELECT * FROM table_data WHERE idNameQuiz LIKE :idName")
    fun getNameCrime(idName: String?) : Flow<List<Crime>>?                     //Запускаем через Корутину
    @Query("SELECT * FROM front_list")
    fun getAllCrimeFrontList() : Flow<List<FrontList>>     //Запускаем через Корутину
    @Query("SELECT* FROM new_user_table")
    fun getCrimeNewQuiz() : Flow<List<CrimeNewQuiz>>

    @Query("SELECT * FROM table_data")
    suspend fun getCrime() : List<Crime>
    @Query("SELECT * FROM new_user_table")
    suspend fun getAllIdQuestion() : List<CrimeNewQuiz>
    @Query("SELECT * FROM front_list")
    suspend fun getFrontList() : List<FrontList>

    @Query("SELECT * FROM new_user_table WHERE idListNameQuestion LIKE :idGeoQuiz")
    fun getGeoQuiz(idGeoQuiz: String): Flow<List<CrimeNewQuiz>>

    @Query("SELECT * FROM new_user_table WHERE idListNameQuestion LIKE :idUser" )
    suspend fun getIdUserQuestion(idUser: String) : List<CrimeNewQuiz>
    @Query("SELECT * FROM table_data WHERE updateAnswer LIKE :updateUnswer AND idNameQuiz LIKE :idUser" )
    suspend fun getUpdateCrimeGeoQuiz(updateUnswer: Boolean, idUser: String) : List<Crime>

    @Query("SELECT * FROM front_list WHERE nameQuestion LIKE :nameQuestion")
    suspend fun getFrontListGeoQuiz(nameQuestion: String) : List<FrontList>
    @Query("SELECT * FROM front_list")
    fun getFrontListAdapter() : Flow<List<FrontList>>

    @Query("DELETE FROM front_list WHERE nameQuestion IS :id")
    suspend fun deleteFrontList(id: String)
    @Query("DELETE FROM table_data WHERE idNameQuiz IS :name")
    suspend fun deleteCrimeName(name: String)
    @Query("DELETE FROM new_user_table WHERE idListNameQuestion IS :nameQuiz")
    suspend fun deleteCrimeNewQuizName(nameQuiz: String)

    @Update
    suspend fun updateCrime(crime: Crime)
    @Update
    suspend fun updateFrontList(frontList: FrontList)

}