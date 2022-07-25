package com.tpov.schoolquiz.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import com.tpov.schoolquiz.data.database.entities.QuizDetail
import androidx.room.Query
import androidx.room.Update
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.ApiQuestion
import com.tpov.schoolquiz.data.database.entities.Quiz
import kotlinx.coroutines.flow.Flow

@Dao

interface CrimeDao {
    @Insert
    suspend fun insertAll(note: QuizDetail)
    @Insert
    suspend fun insertAllFrontList(note: Quiz)
    @Insert
    suspend fun insertCrimeNewQuiz(name: Question)
    @Insert
    suspend fun insertGenerateQuestion(name: List<ApiQuestion>)

    @Query("SELECT * FROM table_data")
    fun getAllCrime() : LiveData<List<QuizDetail>>    //Запускаем через Корутину
    @Query("SELECT * FROM table_data WHERE idNameQuiz LIKE :idName")
    fun getNameCrime(idName: String?) : Flow<List<QuizDetail>>?                     //Запускаем через Корутину
    @Query("SELECT * FROM front_list")
    fun getAllCrimeFrontList() : Flow<List<Quiz>>     //Запускаем через Корутину
    @Query("SELECT* FROM new_user_table")
    fun getCrimeNewQuiz() : LiveData<List<Question>>
    @Query("SELECT * FROM table_generate_question")
    fun getAllGenerateQuestion(): Flow<List<ApiQuestion>>

    @Query("SELECT * FROM table_data")
    suspend fun getQuiz() : List<QuizDetail>
    @Query("SELECT * FROM new_user_table")
    fun getAllIdQuestion() : LiveData<List<Question>>
    @Query("SELECT * FROM front_list")
    fun getFrontList() : LiveData<List<Quiz>>
    @Query("SELECT * FROM table_generate_question")
    fun getGenerateQuestion(): List<ApiQuestion>

    @Query("SELECT * FROM new_user_table WHERE idListNameQuestion LIKE :idGeoQuiz")
    fun getGeoQuiz(idGeoQuiz: String): Flow<List<Question>>
    @Query("SELECT * FROM new_user_table WHERE idListNameQuestion LIKE :idUser" )
    suspend fun getIdUserQuestion(idUser: String) : List<Question>
    @Query("SELECT * FROM table_data WHERE updateAnswer LIKE :updateUnswer AND idNameQuiz LIKE :idUser" )
    suspend fun getUpdateCrimeGeoQuiz(updateUnswer: Boolean, idUser: String) : List<QuizDetail>
    @Query("SELECT * FROM front_list WHERE nameQuestion LIKE :nameQuestion")
    suspend fun getFrontListGeoQuiz(nameQuestion: String) : List<Quiz>
    @Query("SELECT * FROM front_list")
    fun getFrontListAdapter() : Flow<List<Quiz>>
    @Query("SELECT * FROM table_generate_question WHERE date LIKE :systemDate")
    fun getDateGenerationQuestion(systemDate: String): List<ApiQuestion>
    /*
    @Query("SELECT * FROM table_generate_question WHERE date LIKE 0")
    fun getNotNulLDateGenerationQuestion()*/

    @Query("DELETE FROM front_list WHERE nameQuestion IS :id")
    suspend fun deleteFrontList(id: String)
    @Query("DELETE FROM table_data WHERE idNameQuiz IS :name")
    suspend fun deleteCrimeName(name: String)
    @Query("DELETE FROM new_user_table WHERE idListNameQuestion IS :nameQuiz")
    suspend fun deleteCrimeNewQuizName(nameQuiz: String)
    @Query("DELETE FROM table_generate_question WHERE id LIKE :questionId")
    suspend fun deleteGenerationQuestion(questionId: Int)

    @Update
    suspend fun updateCrime(quizDetail: QuizDetail)
    @Update
    fun updateFrontList(quiz: Quiz)
    @Update
    suspend fun updateGenerationQuestion(generateQuestion: ApiQuestion)

}