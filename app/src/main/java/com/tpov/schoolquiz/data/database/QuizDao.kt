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

interface QuizDao {
    @Insert
    suspend fun insertQuizDetail(note: QuizDetail)
    @Insert
    suspend fun insertQuiz(note: Quiz)
    @Insert
    suspend fun insertQuestion(name: Question)
    @Insert
    suspend fun insertListApiQuestion(name: List<ApiQuestion>)

    @Query("SELECT * FROM table_data")
    fun getQuizDetail() : LiveData<List<QuizDetail>>
    @Query("SELECT * FROM table_data WHERE idNameQuiz LIKE :idName")
    fun getQuizDetailByName(idName: String?) : LiveData<List<QuizDetail>>
    @Query("SELECT * FROM front_list")
    fun getQuiz() : LiveData<List<Quiz>>
    @Query("SELECT* FROM new_user_table")
    fun getQuestion() : LiveData<List<Question>>
    @Query("SELECT * FROM table_generate_question")
    fun getApiQuestion(): LiveData<List<ApiQuestion>>
    @Query("SELECT * FROM table_data")
    suspend fun getQuizDetailList(): List<QuizDetail>
    @Query("SELECT * FROM table_generate_question")
    suspend fun getListApiQuestion(): List<ApiQuestion>
    @Query("SELECT * FROM new_user_table WHERE idListNameQuestion LIKE :idGeoQuiz")
    fun getQuestionByIdGeoQuiz(idGeoQuiz: String): LiveData<List<Question>>
    @Query("SELECT * FROM new_user_table WHERE idListNameQuestion LIKE :idUser" )
    suspend fun getListQuestionByIdUser(idUser: String) : List<Question>
    @Query("SELECT * FROM table_data WHERE updateAnswer LIKE :updateUnswer AND idNameQuiz LIKE :idUser" )
    suspend fun getListQuizDetailByUpdateANDIdUser(updateUnswer: Boolean, idUser: String) : List<QuizDetail>
    @Query("SELECT * FROM front_list WHERE nameQuestion LIKE :nameQuestion")
    suspend fun getListQuizByNameQuestion(nameQuestion: String) : List<Quiz>
    @Query("SELECT * FROM table_generate_question WHERE date LIKE :systemDate")
    suspend fun getListApiQuestionBySystemDate(systemDate: String): List<ApiQuestion>

    @Query("DELETE FROM front_list WHERE nameQuestion IS :id")
    suspend fun deleteQuizByNameQuestion(id: String)
    @Query("DELETE FROM table_data WHERE idNameQuiz IS :name")
    suspend fun deleteQuizDetailByIdNameQuiz(name: String)
    @Query("DELETE FROM new_user_table WHERE idListNameQuestion IS :nameQuiz")
    suspend fun deleteQuestionByNameQuiz(nameQuiz: String)
    @Query("DELETE FROM table_generate_question WHERE id LIKE :questionId")
    suspend fun deleteApiQuestionById(questionId: Int)

    @Update
    suspend fun updateQuizDetail(quizDetail: QuizDetail)
    @Update
    suspend fun updateQuiz(quiz: Quiz)
    @Update
    suspend fun updateApiQuestion(generateQuestion: ApiQuestion)


}