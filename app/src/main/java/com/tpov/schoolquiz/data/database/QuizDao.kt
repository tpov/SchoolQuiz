package com.tpov.schoolquiz.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tpov.schoolquiz.data.database.entities.ApiQuestion
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.data.database.entities.QuizDetail

@Dao

interface QuizDao {
    @Insert
    fun insertQuizDetail(note: QuizDetail)
    @Insert
    fun insertQuiz(note: Quiz)
    @Insert
    fun insertQuestion(name: Question)
    @Insert
    fun insertListApiQuestion(name: List<ApiQuestion>)

    @Query("SELECT * FROM table_data")
    fun getQuizDetail() : LiveData<List<QuizDetail>>
    @Query("SELECT * FROM table_data WHERE idNameQuiz LIKE :idName")
    fun getQuizDetailByName(idName: String?) : LiveData<List<QuizDetail>>
    @Query("SELECT * FROM front_list")
    fun getQuiz() : LiveData<List<Quiz>>
    @Query("SELECT * FROM front_list")
    fun getQuizList() : List<Quiz>

    @Query("SELECT * FROM new_user_table")
    fun getQuestionList() : List<Question>

    @Query("SELECT* FROM new_user_table")
    fun getQuestion() : LiveData<List<Question>>
    @Query("SELECT * FROM table_generate_question")
    fun getApiQuestion(): LiveData<List<ApiQuestion>>
    @Query("SELECT * FROM table_data")
    fun getQuizDetailList(): List<QuizDetail>
    @Query("SELECT * FROM table_generate_question")
    fun getListApiQuestion(): List<ApiQuestion>
    @Query("SELECT * FROM new_user_table WHERE idListNameQuestion LIKE :idGeoQuiz")
    fun getQuestionByIdGeoQuiz(idGeoQuiz: String): LiveData<List<Question>>
    @Query("SELECT * FROM new_user_table WHERE nameQuestion LIKE :nameQuestion")
    fun getListQuestionByIdUser(nameQuestion: String) : List<Question>
    @Query("SELECT * FROM table_data WHERE updateAnswer LIKE :updateUnswer AND idNameQuiz LIKE :idUser")
    fun getListQuizDetailByUpdateANDIdUser(updateUnswer: Boolean, idUser: String) : List<QuizDetail>
    @Query("SELECT * FROM front_list WHERE nameQuestion LIKE :nameQuestion")
    fun getListQuizByNameQuestion(nameQuestion: String) : List<Quiz>
    @Query("SELECT * FROM table_generate_question WHERE date LIKE :systemDate")
    fun getListApiQuestionBySystemDate(systemDate: String): List<ApiQuestion>

    @Query("DELETE FROM front_list WHERE nameQuestion IS :id")
    fun deleteQuizByNameQuestion(id: String)
    @Query("DELETE FROM table_data WHERE idNameQuiz IS :name")
    fun deleteQuizDetailByIdNameQuiz(name: String)
    @Query("DELETE FROM new_user_table WHERE idListNameQuestion IS :nameQuiz")
    fun deleteQuestionByNameQuiz(nameQuiz: String)
    @Query("DELETE FROM table_generate_question WHERE id LIKE :questionId")
    fun deleteApiQuestionById(questionId: Int)

    @Update
    suspend fun updateQuizDetail(quizDetail: QuizDetail)
    @Update
    suspend fun updateQuiz(quiz: Quiz)
    @Update
    suspend fun updateApiQuestion(generateQuestion: ApiQuestion)


}