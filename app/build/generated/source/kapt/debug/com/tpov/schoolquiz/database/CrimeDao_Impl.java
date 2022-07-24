package com.tpov.schoolquiz.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;

import com.tpov.schoolquiz.data.database.CrimeDao;
import com.tpov.schoolquiz.data.database.entities.QuizDetail;
import com.tpov.schoolquiz.data.database.entities.Question;
import com.tpov.schoolquiz.data.database.entities.ApiQuestion;
import com.tpov.schoolquiz.data.database.entities.Quiz;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CrimeDao_Impl implements CrimeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QuizDetail> __insertionAdapterOfCrime;

  private final EntityInsertionAdapter<Quiz> __insertionAdapterOfFrontList;

  private final EntityInsertionAdapter<Question> __insertionAdapterOfCrimeNewQuiz;

  private final EntityInsertionAdapter<ApiQuestion> __insertionAdapterOfEntityGenerateQuestion;

  private final EntityDeletionOrUpdateAdapter<QuizDetail> __updateAdapterOfCrime;

  private final EntityDeletionOrUpdateAdapter<Quiz> __updateAdapterOfFrontList;

  private final EntityDeletionOrUpdateAdapter<ApiQuestion> __updateAdapterOfEntityGenerateQuestion;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFrontList;

  private final SharedSQLiteStatement __preparedStmtOfDeleteCrimeName;

  private final SharedSQLiteStatement __preparedStmtOfDeleteCrimeNewQuizName;

  private final SharedSQLiteStatement __preparedStmtOfDeleteGenerationQuestion;

  public CrimeDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCrime = new EntityInsertionAdapter<QuizDetail>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `table_data` (`id`,`idNameQuiz`,`userName`,`data`,`codeAnswer`,`codeMap`,`currentIndex`,`isCheater`,`constCurrentIndex`,`points`,`persentPoints`,`cheatPoints`,`charMap`,`i`,`j`,`updateAnswer`,`leftUnswer`,`numQuestion`,`numAnswer`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, QuizDetail value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getIdNameQuiz() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getIdNameQuiz());
        }
        if (value.getUserName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUserName());
        }
        if (value.getData() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getData());
        }
        if (value.getCodeAnswer() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCodeAnswer());
        }
        if (value.getCodeMap() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCodeMap());
        }
        stmt.bindLong(7, value.getCurrentIndex());
        final int _tmp = value.isCheater() ? 1 : 0;
        stmt.bindLong(8, _tmp);
        stmt.bindLong(9, value.getConstCurrentIndex());
        stmt.bindLong(10, value.getPoints());
        stmt.bindLong(11, value.getPersentPoints());
        stmt.bindLong(12, value.getCheatPoints());
        if (value.getCharMap() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getCharMap());
        }
        stmt.bindLong(14, value.getI());
        stmt.bindLong(15, value.getJ());
        final int _tmp_1 = value.getUpdateAnswer() ? 1 : 0;
        stmt.bindLong(16, _tmp_1);
        if (value.getLeftUnswer() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindLong(17, value.getLeftUnswer());
        }
        if (value.getNumQuestion() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindLong(18, value.getNumQuestion());
        }
        if (value.getNumAnswer() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindLong(19, value.getNumAnswer());
        }
      }
    };
    this.__insertionAdapterOfFrontList = new EntityInsertionAdapter<Quiz>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `front_list` (`id`,`nameQuestion`,`user_name`,`data`,`stars`,`numQ`,`numA`,`numHQ`,`starsAll`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Quiz value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getNameQuestion() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNameQuestion());
        }
        if (value.getUserName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUserName());
        }
        if (value.getData() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getData());
        }
        stmt.bindLong(5, value.getStars());
        stmt.bindLong(6, value.getNumQ());
        stmt.bindLong(7, value.getNumA());
        stmt.bindLong(8, value.getNumHQ());
        stmt.bindLong(9, value.getStarsAll());
      }
    };
    this.__insertionAdapterOfCrimeNewQuiz = new EntityInsertionAdapter<Question>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `new_user_table` (`id`,`nameQuestion`,`answerQuestion`,`typeQuestion`,`idListNameQuestion`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Question value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getNameQuestion() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNameQuestion());
        }
        final int _tmp = value.getAnswerQuestion() ? 1 : 0;
        stmt.bindLong(3, _tmp);
        final int _tmp_1 = value.getTypeQuestion() ? 1 : 0;
        stmt.bindLong(4, _tmp_1);
        if (value.getIdListNameQuestion() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getIdListNameQuestion());
        }
      }
    };
    this.__insertionAdapterOfEntityGenerateQuestion = new EntityInsertionAdapter<ApiQuestion>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `table_generate_question` (`id`,`date`,`question`,`answer`,`questionTranslate`,`answerTranslate`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ApiQuestion value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getDate() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDate());
        }
        if (value.getQuestion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getQuestion());
        }
        if (value.getAnswer() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAnswer());
        }
        if (value.getQuestionTranslate() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getQuestionTranslate());
        }
        if (value.getAnswerTranslate() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAnswerTranslate());
        }
      }
    };
    this.__updateAdapterOfCrime = new EntityDeletionOrUpdateAdapter<QuizDetail>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `table_data` SET `id` = ?,`idNameQuiz` = ?,`userName` = ?,`data` = ?,`codeAnswer` = ?,`codeMap` = ?,`currentIndex` = ?,`isCheater` = ?,`constCurrentIndex` = ?,`points` = ?,`persentPoints` = ?,`cheatPoints` = ?,`charMap` = ?,`i` = ?,`j` = ?,`updateAnswer` = ?,`leftUnswer` = ?,`numQuestion` = ?,`numAnswer` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, QuizDetail value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getIdNameQuiz() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getIdNameQuiz());
        }
        if (value.getUserName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUserName());
        }
        if (value.getData() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getData());
        }
        if (value.getCodeAnswer() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCodeAnswer());
        }
        if (value.getCodeMap() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCodeMap());
        }
        stmt.bindLong(7, value.getCurrentIndex());
        final int _tmp = value.isCheater() ? 1 : 0;
        stmt.bindLong(8, _tmp);
        stmt.bindLong(9, value.getConstCurrentIndex());
        stmt.bindLong(10, value.getPoints());
        stmt.bindLong(11, value.getPersentPoints());
        stmt.bindLong(12, value.getCheatPoints());
        if (value.getCharMap() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getCharMap());
        }
        stmt.bindLong(14, value.getI());
        stmt.bindLong(15, value.getJ());
        final int _tmp_1 = value.getUpdateAnswer() ? 1 : 0;
        stmt.bindLong(16, _tmp_1);
        if (value.getLeftUnswer() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindLong(17, value.getLeftUnswer());
        }
        if (value.getNumQuestion() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindLong(18, value.getNumQuestion());
        }
        if (value.getNumAnswer() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindLong(19, value.getNumAnswer());
        }
        if (value.getId() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindLong(20, value.getId());
        }
      }
    };
    this.__updateAdapterOfFrontList = new EntityDeletionOrUpdateAdapter<Quiz>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `front_list` SET `id` = ?,`nameQuestion` = ?,`user_name` = ?,`data` = ?,`stars` = ?,`numQ` = ?,`numA` = ?,`numHQ` = ?,`starsAll` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Quiz value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getNameQuestion() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNameQuestion());
        }
        if (value.getUserName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUserName());
        }
        if (value.getData() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getData());
        }
        stmt.bindLong(5, value.getStars());
        stmt.bindLong(6, value.getNumQ());
        stmt.bindLong(7, value.getNumA());
        stmt.bindLong(8, value.getNumHQ());
        stmt.bindLong(9, value.getStarsAll());
        if (value.getId() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindLong(10, value.getId());
        }
      }
    };
    this.__updateAdapterOfEntityGenerateQuestion = new EntityDeletionOrUpdateAdapter<ApiQuestion>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `table_generate_question` SET `id` = ?,`date` = ?,`question` = ?,`answer` = ?,`questionTranslate` = ?,`answerTranslate` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ApiQuestion value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getDate() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDate());
        }
        if (value.getQuestion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getQuestion());
        }
        if (value.getAnswer() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAnswer());
        }
        if (value.getQuestionTranslate() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getQuestionTranslate());
        }
        if (value.getAnswerTranslate() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAnswerTranslate());
        }
        if (value.getId() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteFrontList = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM front_list WHERE nameQuestion IS ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteCrimeName = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM table_data WHERE idNameQuiz IS ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteCrimeNewQuizName = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM new_user_table WHERE idListNameQuestion IS ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteGenerationQuestion = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM table_generate_question WHERE id LIKE ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final QuizDetail note, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCrime.insert(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object insertAllFrontList(final Quiz note, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFrontList.insert(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object insertCrimeNewQuiz(final Question name, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCrimeNewQuiz.insert(name);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object insertGenerateQuestion(final List<ApiQuestion> name,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEntityGenerateQuestion.insert(name);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateCrime(final QuizDetail quizDetail, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCrime.handle(quizDetail);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateFrontList(final Quiz quiz, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfFrontList.handle(quiz);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateGenerationQuestion(final ApiQuestion generateQuestion,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfEntityGenerateQuestion.handle(generateQuestion);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteFrontList(final String id, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFrontList.acquire();
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
        }
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteFrontList.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteCrimeName(final String name, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCrimeName.acquire();
        int _argIndex = 1;
        if (name == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, name);
        }
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteCrimeName.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteCrimeNewQuizName(final String nameQuiz,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCrimeNewQuizName.acquire();
        int _argIndex = 1;
        if (nameQuiz == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, nameQuiz);
        }
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteCrimeNewQuizName.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteGenerationQuestion(final int questionId,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteGenerationQuestion.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, questionId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteGenerationQuestion.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Flow<List<QuizDetail>> getAllCrime() {
    final String _sql = "SELECT * FROM table_data";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"table_data"}, new Callable<List<QuizDetail>>() {
      @Override
      public List<QuizDetail> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfIdNameQuiz = CursorUtil.getColumnIndexOrThrow(_cursor, "idNameQuiz");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final int _cursorIndexOfCodeAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "codeAnswer");
          final int _cursorIndexOfCodeMap = CursorUtil.getColumnIndexOrThrow(_cursor, "codeMap");
          final int _cursorIndexOfCurrentIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "currentIndex");
          final int _cursorIndexOfIsCheater = CursorUtil.getColumnIndexOrThrow(_cursor, "isCheater");
          final int _cursorIndexOfConstCurrentIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "constCurrentIndex");
          final int _cursorIndexOfPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "points");
          final int _cursorIndexOfPersentPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "persentPoints");
          final int _cursorIndexOfCheatPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "cheatPoints");
          final int _cursorIndexOfCharMap = CursorUtil.getColumnIndexOrThrow(_cursor, "charMap");
          final int _cursorIndexOfI = CursorUtil.getColumnIndexOrThrow(_cursor, "i");
          final int _cursorIndexOfJ = CursorUtil.getColumnIndexOrThrow(_cursor, "j");
          final int _cursorIndexOfUpdateAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "updateAnswer");
          final int _cursorIndexOfLeftUnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "leftUnswer");
          final int _cursorIndexOfNumQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "numQuestion");
          final int _cursorIndexOfNumAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "numAnswer");
          final List<QuizDetail> _result = new ArrayList<QuizDetail>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final QuizDetail _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpIdNameQuiz;
            if (_cursor.isNull(_cursorIndexOfIdNameQuiz)) {
              _tmpIdNameQuiz = null;
            } else {
              _tmpIdNameQuiz = _cursor.getString(_cursorIndexOfIdNameQuiz);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpData;
            if (_cursor.isNull(_cursorIndexOfData)) {
              _tmpData = null;
            } else {
              _tmpData = _cursor.getString(_cursorIndexOfData);
            }
            final String _tmpCodeAnswer;
            if (_cursor.isNull(_cursorIndexOfCodeAnswer)) {
              _tmpCodeAnswer = null;
            } else {
              _tmpCodeAnswer = _cursor.getString(_cursorIndexOfCodeAnswer);
            }
            final String _tmpCodeMap;
            if (_cursor.isNull(_cursorIndexOfCodeMap)) {
              _tmpCodeMap = null;
            } else {
              _tmpCodeMap = _cursor.getString(_cursorIndexOfCodeMap);
            }
            final int _tmpCurrentIndex;
            _tmpCurrentIndex = _cursor.getInt(_cursorIndexOfCurrentIndex);
            final boolean _tmpIsCheater;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCheater);
            _tmpIsCheater = _tmp != 0;
            final int _tmpConstCurrentIndex;
            _tmpConstCurrentIndex = _cursor.getInt(_cursorIndexOfConstCurrentIndex);
            final int _tmpPoints;
            _tmpPoints = _cursor.getInt(_cursorIndexOfPoints);
            final int _tmpPersentPoints;
            _tmpPersentPoints = _cursor.getInt(_cursorIndexOfPersentPoints);
            final int _tmpCheatPoints;
            _tmpCheatPoints = _cursor.getInt(_cursorIndexOfCheatPoints);
            final String _tmpCharMap;
            if (_cursor.isNull(_cursorIndexOfCharMap)) {
              _tmpCharMap = null;
            } else {
              _tmpCharMap = _cursor.getString(_cursorIndexOfCharMap);
            }
            final int _tmpI;
            _tmpI = _cursor.getInt(_cursorIndexOfI);
            final int _tmpJ;
            _tmpJ = _cursor.getInt(_cursorIndexOfJ);
            final boolean _tmpUpdateAnswer;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfUpdateAnswer);
            _tmpUpdateAnswer = _tmp_1 != 0;
            final Integer _tmpLeftUnswer;
            if (_cursor.isNull(_cursorIndexOfLeftUnswer)) {
              _tmpLeftUnswer = null;
            } else {
              _tmpLeftUnswer = _cursor.getInt(_cursorIndexOfLeftUnswer);
            }
            final Integer _tmpNumQuestion;
            if (_cursor.isNull(_cursorIndexOfNumQuestion)) {
              _tmpNumQuestion = null;
            } else {
              _tmpNumQuestion = _cursor.getInt(_cursorIndexOfNumQuestion);
            }
            final Integer _tmpNumAnswer;
            if (_cursor.isNull(_cursorIndexOfNumAnswer)) {
              _tmpNumAnswer = null;
            } else {
              _tmpNumAnswer = _cursor.getInt(_cursorIndexOfNumAnswer);
            }
            _item = new QuizDetail(_tmpId,_tmpIdNameQuiz,_tmpUserName,_tmpData,_tmpCodeAnswer,_tmpCodeMap,_tmpCurrentIndex,_tmpIsCheater,_tmpConstCurrentIndex,_tmpPoints,_tmpPersentPoints,_tmpCheatPoints,_tmpCharMap,_tmpI,_tmpJ,_tmpUpdateAnswer,_tmpLeftUnswer,_tmpNumQuestion,_tmpNumAnswer);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<QuizDetail>> getNameCrime(final String idName) {
    final String _sql = "SELECT * FROM table_data WHERE idNameQuiz LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (idName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, idName);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"table_data"}, new Callable<List<QuizDetail>>() {
      @Override
      public List<QuizDetail> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfIdNameQuiz = CursorUtil.getColumnIndexOrThrow(_cursor, "idNameQuiz");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final int _cursorIndexOfCodeAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "codeAnswer");
          final int _cursorIndexOfCodeMap = CursorUtil.getColumnIndexOrThrow(_cursor, "codeMap");
          final int _cursorIndexOfCurrentIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "currentIndex");
          final int _cursorIndexOfIsCheater = CursorUtil.getColumnIndexOrThrow(_cursor, "isCheater");
          final int _cursorIndexOfConstCurrentIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "constCurrentIndex");
          final int _cursorIndexOfPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "points");
          final int _cursorIndexOfPersentPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "persentPoints");
          final int _cursorIndexOfCheatPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "cheatPoints");
          final int _cursorIndexOfCharMap = CursorUtil.getColumnIndexOrThrow(_cursor, "charMap");
          final int _cursorIndexOfI = CursorUtil.getColumnIndexOrThrow(_cursor, "i");
          final int _cursorIndexOfJ = CursorUtil.getColumnIndexOrThrow(_cursor, "j");
          final int _cursorIndexOfUpdateAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "updateAnswer");
          final int _cursorIndexOfLeftUnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "leftUnswer");
          final int _cursorIndexOfNumQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "numQuestion");
          final int _cursorIndexOfNumAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "numAnswer");
          final List<QuizDetail> _result = new ArrayList<QuizDetail>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final QuizDetail _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpIdNameQuiz;
            if (_cursor.isNull(_cursorIndexOfIdNameQuiz)) {
              _tmpIdNameQuiz = null;
            } else {
              _tmpIdNameQuiz = _cursor.getString(_cursorIndexOfIdNameQuiz);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpData;
            if (_cursor.isNull(_cursorIndexOfData)) {
              _tmpData = null;
            } else {
              _tmpData = _cursor.getString(_cursorIndexOfData);
            }
            final String _tmpCodeAnswer;
            if (_cursor.isNull(_cursorIndexOfCodeAnswer)) {
              _tmpCodeAnswer = null;
            } else {
              _tmpCodeAnswer = _cursor.getString(_cursorIndexOfCodeAnswer);
            }
            final String _tmpCodeMap;
            if (_cursor.isNull(_cursorIndexOfCodeMap)) {
              _tmpCodeMap = null;
            } else {
              _tmpCodeMap = _cursor.getString(_cursorIndexOfCodeMap);
            }
            final int _tmpCurrentIndex;
            _tmpCurrentIndex = _cursor.getInt(_cursorIndexOfCurrentIndex);
            final boolean _tmpIsCheater;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCheater);
            _tmpIsCheater = _tmp != 0;
            final int _tmpConstCurrentIndex;
            _tmpConstCurrentIndex = _cursor.getInt(_cursorIndexOfConstCurrentIndex);
            final int _tmpPoints;
            _tmpPoints = _cursor.getInt(_cursorIndexOfPoints);
            final int _tmpPersentPoints;
            _tmpPersentPoints = _cursor.getInt(_cursorIndexOfPersentPoints);
            final int _tmpCheatPoints;
            _tmpCheatPoints = _cursor.getInt(_cursorIndexOfCheatPoints);
            final String _tmpCharMap;
            if (_cursor.isNull(_cursorIndexOfCharMap)) {
              _tmpCharMap = null;
            } else {
              _tmpCharMap = _cursor.getString(_cursorIndexOfCharMap);
            }
            final int _tmpI;
            _tmpI = _cursor.getInt(_cursorIndexOfI);
            final int _tmpJ;
            _tmpJ = _cursor.getInt(_cursorIndexOfJ);
            final boolean _tmpUpdateAnswer;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfUpdateAnswer);
            _tmpUpdateAnswer = _tmp_1 != 0;
            final Integer _tmpLeftUnswer;
            if (_cursor.isNull(_cursorIndexOfLeftUnswer)) {
              _tmpLeftUnswer = null;
            } else {
              _tmpLeftUnswer = _cursor.getInt(_cursorIndexOfLeftUnswer);
            }
            final Integer _tmpNumQuestion;
            if (_cursor.isNull(_cursorIndexOfNumQuestion)) {
              _tmpNumQuestion = null;
            } else {
              _tmpNumQuestion = _cursor.getInt(_cursorIndexOfNumQuestion);
            }
            final Integer _tmpNumAnswer;
            if (_cursor.isNull(_cursorIndexOfNumAnswer)) {
              _tmpNumAnswer = null;
            } else {
              _tmpNumAnswer = _cursor.getInt(_cursorIndexOfNumAnswer);
            }
            _item = new QuizDetail(_tmpId,_tmpIdNameQuiz,_tmpUserName,_tmpData,_tmpCodeAnswer,_tmpCodeMap,_tmpCurrentIndex,_tmpIsCheater,_tmpConstCurrentIndex,_tmpPoints,_tmpPersentPoints,_tmpCheatPoints,_tmpCharMap,_tmpI,_tmpJ,_tmpUpdateAnswer,_tmpLeftUnswer,_tmpNumQuestion,_tmpNumAnswer);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Quiz>> getAllCrimeFrontList() {
    final String _sql = "SELECT * FROM front_list";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"front_list"}, new Callable<List<Quiz>>() {
      @Override
      public List<Quiz> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "nameQuestion");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "user_name");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final int _cursorIndexOfStars = CursorUtil.getColumnIndexOrThrow(_cursor, "stars");
          final int _cursorIndexOfNumQ = CursorUtil.getColumnIndexOrThrow(_cursor, "numQ");
          final int _cursorIndexOfNumA = CursorUtil.getColumnIndexOrThrow(_cursor, "numA");
          final int _cursorIndexOfNumHQ = CursorUtil.getColumnIndexOrThrow(_cursor, "numHQ");
          final int _cursorIndexOfStarsAll = CursorUtil.getColumnIndexOrThrow(_cursor, "starsAll");
          final List<Quiz> _result = new ArrayList<Quiz>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Quiz _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpNameQuestion;
            if (_cursor.isNull(_cursorIndexOfNameQuestion)) {
              _tmpNameQuestion = null;
            } else {
              _tmpNameQuestion = _cursor.getString(_cursorIndexOfNameQuestion);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpData;
            if (_cursor.isNull(_cursorIndexOfData)) {
              _tmpData = null;
            } else {
              _tmpData = _cursor.getString(_cursorIndexOfData);
            }
            final int _tmpStars;
            _tmpStars = _cursor.getInt(_cursorIndexOfStars);
            final int _tmpNumQ;
            _tmpNumQ = _cursor.getInt(_cursorIndexOfNumQ);
            final int _tmpNumA;
            _tmpNumA = _cursor.getInt(_cursorIndexOfNumA);
            final int _tmpNumHQ;
            _tmpNumHQ = _cursor.getInt(_cursorIndexOfNumHQ);
            final int _tmpStarsAll;
            _tmpStarsAll = _cursor.getInt(_cursorIndexOfStarsAll);
            _item = new Quiz(_tmpId,_tmpNameQuestion,_tmpUserName,_tmpData,_tmpStars,_tmpNumQ,_tmpNumA,_tmpNumHQ,_tmpStarsAll);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Question>> getCrimeNewQuiz() {
    final String _sql = "SELECT* FROM new_user_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"new_user_table"}, new Callable<List<Question>>() {
      @Override
      public List<Question> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "nameQuestion");
          final int _cursorIndexOfAnswerQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "answerQuestion");
          final int _cursorIndexOfTypeQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "typeQuestion");
          final int _cursorIndexOfIdListNameQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "idListNameQuestion");
          final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Question _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpNameQuestion;
            if (_cursor.isNull(_cursorIndexOfNameQuestion)) {
              _tmpNameQuestion = null;
            } else {
              _tmpNameQuestion = _cursor.getString(_cursorIndexOfNameQuestion);
            }
            final boolean _tmpAnswerQuestion;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAnswerQuestion);
            _tmpAnswerQuestion = _tmp != 0;
            final boolean _tmpTypeQuestion;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfTypeQuestion);
            _tmpTypeQuestion = _tmp_1 != 0;
            final String _tmpIdListNameQuestion;
            if (_cursor.isNull(_cursorIndexOfIdListNameQuestion)) {
              _tmpIdListNameQuestion = null;
            } else {
              _tmpIdListNameQuestion = _cursor.getString(_cursorIndexOfIdListNameQuestion);
            }
            _item = new Question(_tmpId,_tmpNameQuestion,_tmpAnswerQuestion,_tmpTypeQuestion,_tmpIdListNameQuestion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ApiQuestion>> getAllGenerateQuestion() {
    final String _sql = "SELECT * FROM table_generate_question";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"table_generate_question"}, new Callable<List<ApiQuestion>>() {
      @Override
      public List<ApiQuestion> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
          final int _cursorIndexOfQuestionTranslate = CursorUtil.getColumnIndexOrThrow(_cursor, "questionTranslate");
          final int _cursorIndexOfAnswerTranslate = CursorUtil.getColumnIndexOrThrow(_cursor, "answerTranslate");
          final List<ApiQuestion> _result = new ArrayList<ApiQuestion>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ApiQuestion _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpAnswer;
            if (_cursor.isNull(_cursorIndexOfAnswer)) {
              _tmpAnswer = null;
            } else {
              _tmpAnswer = _cursor.getString(_cursorIndexOfAnswer);
            }
            final String _tmpQuestionTranslate;
            if (_cursor.isNull(_cursorIndexOfQuestionTranslate)) {
              _tmpQuestionTranslate = null;
            } else {
              _tmpQuestionTranslate = _cursor.getString(_cursorIndexOfQuestionTranslate);
            }
            final String _tmpAnswerTranslate;
            if (_cursor.isNull(_cursorIndexOfAnswerTranslate)) {
              _tmpAnswerTranslate = null;
            } else {
              _tmpAnswerTranslate = _cursor.getString(_cursorIndexOfAnswerTranslate);
            }
            _item = new ApiQuestion(_tmpId,_tmpDate,_tmpQuestion,_tmpAnswer,_tmpQuestionTranslate,_tmpAnswerTranslate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getCrime(final Continuation<? super List<QuizDetail>> arg0) {
    final String _sql = "SELECT * FROM table_data";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<QuizDetail>>() {
      @Override
      public List<QuizDetail> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfIdNameQuiz = CursorUtil.getColumnIndexOrThrow(_cursor, "idNameQuiz");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final int _cursorIndexOfCodeAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "codeAnswer");
          final int _cursorIndexOfCodeMap = CursorUtil.getColumnIndexOrThrow(_cursor, "codeMap");
          final int _cursorIndexOfCurrentIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "currentIndex");
          final int _cursorIndexOfIsCheater = CursorUtil.getColumnIndexOrThrow(_cursor, "isCheater");
          final int _cursorIndexOfConstCurrentIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "constCurrentIndex");
          final int _cursorIndexOfPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "points");
          final int _cursorIndexOfPersentPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "persentPoints");
          final int _cursorIndexOfCheatPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "cheatPoints");
          final int _cursorIndexOfCharMap = CursorUtil.getColumnIndexOrThrow(_cursor, "charMap");
          final int _cursorIndexOfI = CursorUtil.getColumnIndexOrThrow(_cursor, "i");
          final int _cursorIndexOfJ = CursorUtil.getColumnIndexOrThrow(_cursor, "j");
          final int _cursorIndexOfUpdateAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "updateAnswer");
          final int _cursorIndexOfLeftUnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "leftUnswer");
          final int _cursorIndexOfNumQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "numQuestion");
          final int _cursorIndexOfNumAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "numAnswer");
          final List<QuizDetail> _result = new ArrayList<QuizDetail>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final QuizDetail _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpIdNameQuiz;
            if (_cursor.isNull(_cursorIndexOfIdNameQuiz)) {
              _tmpIdNameQuiz = null;
            } else {
              _tmpIdNameQuiz = _cursor.getString(_cursorIndexOfIdNameQuiz);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpData;
            if (_cursor.isNull(_cursorIndexOfData)) {
              _tmpData = null;
            } else {
              _tmpData = _cursor.getString(_cursorIndexOfData);
            }
            final String _tmpCodeAnswer;
            if (_cursor.isNull(_cursorIndexOfCodeAnswer)) {
              _tmpCodeAnswer = null;
            } else {
              _tmpCodeAnswer = _cursor.getString(_cursorIndexOfCodeAnswer);
            }
            final String _tmpCodeMap;
            if (_cursor.isNull(_cursorIndexOfCodeMap)) {
              _tmpCodeMap = null;
            } else {
              _tmpCodeMap = _cursor.getString(_cursorIndexOfCodeMap);
            }
            final int _tmpCurrentIndex;
            _tmpCurrentIndex = _cursor.getInt(_cursorIndexOfCurrentIndex);
            final boolean _tmpIsCheater;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCheater);
            _tmpIsCheater = _tmp != 0;
            final int _tmpConstCurrentIndex;
            _tmpConstCurrentIndex = _cursor.getInt(_cursorIndexOfConstCurrentIndex);
            final int _tmpPoints;
            _tmpPoints = _cursor.getInt(_cursorIndexOfPoints);
            final int _tmpPersentPoints;
            _tmpPersentPoints = _cursor.getInt(_cursorIndexOfPersentPoints);
            final int _tmpCheatPoints;
            _tmpCheatPoints = _cursor.getInt(_cursorIndexOfCheatPoints);
            final String _tmpCharMap;
            if (_cursor.isNull(_cursorIndexOfCharMap)) {
              _tmpCharMap = null;
            } else {
              _tmpCharMap = _cursor.getString(_cursorIndexOfCharMap);
            }
            final int _tmpI;
            _tmpI = _cursor.getInt(_cursorIndexOfI);
            final int _tmpJ;
            _tmpJ = _cursor.getInt(_cursorIndexOfJ);
            final boolean _tmpUpdateAnswer;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfUpdateAnswer);
            _tmpUpdateAnswer = _tmp_1 != 0;
            final Integer _tmpLeftUnswer;
            if (_cursor.isNull(_cursorIndexOfLeftUnswer)) {
              _tmpLeftUnswer = null;
            } else {
              _tmpLeftUnswer = _cursor.getInt(_cursorIndexOfLeftUnswer);
            }
            final Integer _tmpNumQuestion;
            if (_cursor.isNull(_cursorIndexOfNumQuestion)) {
              _tmpNumQuestion = null;
            } else {
              _tmpNumQuestion = _cursor.getInt(_cursorIndexOfNumQuestion);
            }
            final Integer _tmpNumAnswer;
            if (_cursor.isNull(_cursorIndexOfNumAnswer)) {
              _tmpNumAnswer = null;
            } else {
              _tmpNumAnswer = _cursor.getInt(_cursorIndexOfNumAnswer);
            }
            _item = new QuizDetail(_tmpId,_tmpIdNameQuiz,_tmpUserName,_tmpData,_tmpCodeAnswer,_tmpCodeMap,_tmpCurrentIndex,_tmpIsCheater,_tmpConstCurrentIndex,_tmpPoints,_tmpPersentPoints,_tmpCheatPoints,_tmpCharMap,_tmpI,_tmpJ,_tmpUpdateAnswer,_tmpLeftUnswer,_tmpNumQuestion,_tmpNumAnswer);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @Override
  public Object getAllIdQuestion(final Continuation<? super List<Question>> arg0) {
    final String _sql = "SELECT * FROM new_user_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Question>>() {
      @Override
      public List<Question> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "nameQuestion");
          final int _cursorIndexOfAnswerQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "answerQuestion");
          final int _cursorIndexOfTypeQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "typeQuestion");
          final int _cursorIndexOfIdListNameQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "idListNameQuestion");
          final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Question _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpNameQuestion;
            if (_cursor.isNull(_cursorIndexOfNameQuestion)) {
              _tmpNameQuestion = null;
            } else {
              _tmpNameQuestion = _cursor.getString(_cursorIndexOfNameQuestion);
            }
            final boolean _tmpAnswerQuestion;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAnswerQuestion);
            _tmpAnswerQuestion = _tmp != 0;
            final boolean _tmpTypeQuestion;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfTypeQuestion);
            _tmpTypeQuestion = _tmp_1 != 0;
            final String _tmpIdListNameQuestion;
            if (_cursor.isNull(_cursorIndexOfIdListNameQuestion)) {
              _tmpIdListNameQuestion = null;
            } else {
              _tmpIdListNameQuestion = _cursor.getString(_cursorIndexOfIdListNameQuestion);
            }
            _item = new Question(_tmpId,_tmpNameQuestion,_tmpAnswerQuestion,_tmpTypeQuestion,_tmpIdListNameQuestion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @Override
  public Object getFrontList(final Continuation<? super List<Quiz>> arg0) {
    final String _sql = "SELECT * FROM front_list";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Quiz>>() {
      @Override
      public List<Quiz> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "nameQuestion");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "user_name");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final int _cursorIndexOfStars = CursorUtil.getColumnIndexOrThrow(_cursor, "stars");
          final int _cursorIndexOfNumQ = CursorUtil.getColumnIndexOrThrow(_cursor, "numQ");
          final int _cursorIndexOfNumA = CursorUtil.getColumnIndexOrThrow(_cursor, "numA");
          final int _cursorIndexOfNumHQ = CursorUtil.getColumnIndexOrThrow(_cursor, "numHQ");
          final int _cursorIndexOfStarsAll = CursorUtil.getColumnIndexOrThrow(_cursor, "starsAll");
          final List<Quiz> _result = new ArrayList<Quiz>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Quiz _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpNameQuestion;
            if (_cursor.isNull(_cursorIndexOfNameQuestion)) {
              _tmpNameQuestion = null;
            } else {
              _tmpNameQuestion = _cursor.getString(_cursorIndexOfNameQuestion);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpData;
            if (_cursor.isNull(_cursorIndexOfData)) {
              _tmpData = null;
            } else {
              _tmpData = _cursor.getString(_cursorIndexOfData);
            }
            final int _tmpStars;
            _tmpStars = _cursor.getInt(_cursorIndexOfStars);
            final int _tmpNumQ;
            _tmpNumQ = _cursor.getInt(_cursorIndexOfNumQ);
            final int _tmpNumA;
            _tmpNumA = _cursor.getInt(_cursorIndexOfNumA);
            final int _tmpNumHQ;
            _tmpNumHQ = _cursor.getInt(_cursorIndexOfNumHQ);
            final int _tmpStarsAll;
            _tmpStarsAll = _cursor.getInt(_cursorIndexOfStarsAll);
            _item = new Quiz(_tmpId,_tmpNameQuestion,_tmpUserName,_tmpData,_tmpStars,_tmpNumQ,_tmpNumA,_tmpNumHQ,_tmpStarsAll);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @Override
  public Object getGenerateQuestion(final Continuation<? super List<ApiQuestion>> arg0) {
    final String _sql = "SELECT * FROM table_generate_question";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ApiQuestion>>() {
      @Override
      public List<ApiQuestion> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
          final int _cursorIndexOfQuestionTranslate = CursorUtil.getColumnIndexOrThrow(_cursor, "questionTranslate");
          final int _cursorIndexOfAnswerTranslate = CursorUtil.getColumnIndexOrThrow(_cursor, "answerTranslate");
          final List<ApiQuestion> _result = new ArrayList<ApiQuestion>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ApiQuestion _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpAnswer;
            if (_cursor.isNull(_cursorIndexOfAnswer)) {
              _tmpAnswer = null;
            } else {
              _tmpAnswer = _cursor.getString(_cursorIndexOfAnswer);
            }
            final String _tmpQuestionTranslate;
            if (_cursor.isNull(_cursorIndexOfQuestionTranslate)) {
              _tmpQuestionTranslate = null;
            } else {
              _tmpQuestionTranslate = _cursor.getString(_cursorIndexOfQuestionTranslate);
            }
            final String _tmpAnswerTranslate;
            if (_cursor.isNull(_cursorIndexOfAnswerTranslate)) {
              _tmpAnswerTranslate = null;
            } else {
              _tmpAnswerTranslate = _cursor.getString(_cursorIndexOfAnswerTranslate);
            }
            _item = new ApiQuestion(_tmpId,_tmpDate,_tmpQuestion,_tmpAnswer,_tmpQuestionTranslate,_tmpAnswerTranslate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @Override
  public Flow<List<Question>> getGeoQuiz(final String idGeoQuiz) {
    final String _sql = "SELECT * FROM new_user_table WHERE idListNameQuestion LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (idGeoQuiz == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, idGeoQuiz);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"new_user_table"}, new Callable<List<Question>>() {
      @Override
      public List<Question> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "nameQuestion");
          final int _cursorIndexOfAnswerQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "answerQuestion");
          final int _cursorIndexOfTypeQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "typeQuestion");
          final int _cursorIndexOfIdListNameQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "idListNameQuestion");
          final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Question _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpNameQuestion;
            if (_cursor.isNull(_cursorIndexOfNameQuestion)) {
              _tmpNameQuestion = null;
            } else {
              _tmpNameQuestion = _cursor.getString(_cursorIndexOfNameQuestion);
            }
            final boolean _tmpAnswerQuestion;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAnswerQuestion);
            _tmpAnswerQuestion = _tmp != 0;
            final boolean _tmpTypeQuestion;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfTypeQuestion);
            _tmpTypeQuestion = _tmp_1 != 0;
            final String _tmpIdListNameQuestion;
            if (_cursor.isNull(_cursorIndexOfIdListNameQuestion)) {
              _tmpIdListNameQuestion = null;
            } else {
              _tmpIdListNameQuestion = _cursor.getString(_cursorIndexOfIdListNameQuestion);
            }
            _item = new Question(_tmpId,_tmpNameQuestion,_tmpAnswerQuestion,_tmpTypeQuestion,_tmpIdListNameQuestion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getIdUserQuestion(final String idUser,
      final Continuation<? super List<Question>> arg1) {
    final String _sql = "SELECT * FROM new_user_table WHERE idListNameQuestion LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (idUser == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, idUser);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Question>>() {
      @Override
      public List<Question> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "nameQuestion");
          final int _cursorIndexOfAnswerQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "answerQuestion");
          final int _cursorIndexOfTypeQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "typeQuestion");
          final int _cursorIndexOfIdListNameQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "idListNameQuestion");
          final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Question _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpNameQuestion;
            if (_cursor.isNull(_cursorIndexOfNameQuestion)) {
              _tmpNameQuestion = null;
            } else {
              _tmpNameQuestion = _cursor.getString(_cursorIndexOfNameQuestion);
            }
            final boolean _tmpAnswerQuestion;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAnswerQuestion);
            _tmpAnswerQuestion = _tmp != 0;
            final boolean _tmpTypeQuestion;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfTypeQuestion);
            _tmpTypeQuestion = _tmp_1 != 0;
            final String _tmpIdListNameQuestion;
            if (_cursor.isNull(_cursorIndexOfIdListNameQuestion)) {
              _tmpIdListNameQuestion = null;
            } else {
              _tmpIdListNameQuestion = _cursor.getString(_cursorIndexOfIdListNameQuestion);
            }
            _item = new Question(_tmpId,_tmpNameQuestion,_tmpAnswerQuestion,_tmpTypeQuestion,_tmpIdListNameQuestion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @Override
  public Object getUpdateCrimeGeoQuiz(final boolean updateUnswer, final String idUser,
      final Continuation<? super List<QuizDetail>> arg2) {
    final String _sql = "SELECT * FROM table_data WHERE updateAnswer LIKE ? AND idNameQuiz LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final int _tmp = updateUnswer ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    if (idUser == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, idUser);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<QuizDetail>>() {
      @Override
      public List<QuizDetail> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfIdNameQuiz = CursorUtil.getColumnIndexOrThrow(_cursor, "idNameQuiz");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final int _cursorIndexOfCodeAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "codeAnswer");
          final int _cursorIndexOfCodeMap = CursorUtil.getColumnIndexOrThrow(_cursor, "codeMap");
          final int _cursorIndexOfCurrentIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "currentIndex");
          final int _cursorIndexOfIsCheater = CursorUtil.getColumnIndexOrThrow(_cursor, "isCheater");
          final int _cursorIndexOfConstCurrentIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "constCurrentIndex");
          final int _cursorIndexOfPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "points");
          final int _cursorIndexOfPersentPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "persentPoints");
          final int _cursorIndexOfCheatPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "cheatPoints");
          final int _cursorIndexOfCharMap = CursorUtil.getColumnIndexOrThrow(_cursor, "charMap");
          final int _cursorIndexOfI = CursorUtil.getColumnIndexOrThrow(_cursor, "i");
          final int _cursorIndexOfJ = CursorUtil.getColumnIndexOrThrow(_cursor, "j");
          final int _cursorIndexOfUpdateAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "updateAnswer");
          final int _cursorIndexOfLeftUnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "leftUnswer");
          final int _cursorIndexOfNumQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "numQuestion");
          final int _cursorIndexOfNumAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "numAnswer");
          final List<QuizDetail> _result = new ArrayList<QuizDetail>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final QuizDetail _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpIdNameQuiz;
            if (_cursor.isNull(_cursorIndexOfIdNameQuiz)) {
              _tmpIdNameQuiz = null;
            } else {
              _tmpIdNameQuiz = _cursor.getString(_cursorIndexOfIdNameQuiz);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpData;
            if (_cursor.isNull(_cursorIndexOfData)) {
              _tmpData = null;
            } else {
              _tmpData = _cursor.getString(_cursorIndexOfData);
            }
            final String _tmpCodeAnswer;
            if (_cursor.isNull(_cursorIndexOfCodeAnswer)) {
              _tmpCodeAnswer = null;
            } else {
              _tmpCodeAnswer = _cursor.getString(_cursorIndexOfCodeAnswer);
            }
            final String _tmpCodeMap;
            if (_cursor.isNull(_cursorIndexOfCodeMap)) {
              _tmpCodeMap = null;
            } else {
              _tmpCodeMap = _cursor.getString(_cursorIndexOfCodeMap);
            }
            final int _tmpCurrentIndex;
            _tmpCurrentIndex = _cursor.getInt(_cursorIndexOfCurrentIndex);
            final boolean _tmpIsCheater;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCheater);
            _tmpIsCheater = _tmp_1 != 0;
            final int _tmpConstCurrentIndex;
            _tmpConstCurrentIndex = _cursor.getInt(_cursorIndexOfConstCurrentIndex);
            final int _tmpPoints;
            _tmpPoints = _cursor.getInt(_cursorIndexOfPoints);
            final int _tmpPersentPoints;
            _tmpPersentPoints = _cursor.getInt(_cursorIndexOfPersentPoints);
            final int _tmpCheatPoints;
            _tmpCheatPoints = _cursor.getInt(_cursorIndexOfCheatPoints);
            final String _tmpCharMap;
            if (_cursor.isNull(_cursorIndexOfCharMap)) {
              _tmpCharMap = null;
            } else {
              _tmpCharMap = _cursor.getString(_cursorIndexOfCharMap);
            }
            final int _tmpI;
            _tmpI = _cursor.getInt(_cursorIndexOfI);
            final int _tmpJ;
            _tmpJ = _cursor.getInt(_cursorIndexOfJ);
            final boolean _tmpUpdateAnswer;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfUpdateAnswer);
            _tmpUpdateAnswer = _tmp_2 != 0;
            final Integer _tmpLeftUnswer;
            if (_cursor.isNull(_cursorIndexOfLeftUnswer)) {
              _tmpLeftUnswer = null;
            } else {
              _tmpLeftUnswer = _cursor.getInt(_cursorIndexOfLeftUnswer);
            }
            final Integer _tmpNumQuestion;
            if (_cursor.isNull(_cursorIndexOfNumQuestion)) {
              _tmpNumQuestion = null;
            } else {
              _tmpNumQuestion = _cursor.getInt(_cursorIndexOfNumQuestion);
            }
            final Integer _tmpNumAnswer;
            if (_cursor.isNull(_cursorIndexOfNumAnswer)) {
              _tmpNumAnswer = null;
            } else {
              _tmpNumAnswer = _cursor.getInt(_cursorIndexOfNumAnswer);
            }
            _item = new QuizDetail(_tmpId,_tmpIdNameQuiz,_tmpUserName,_tmpData,_tmpCodeAnswer,_tmpCodeMap,_tmpCurrentIndex,_tmpIsCheater,_tmpConstCurrentIndex,_tmpPoints,_tmpPersentPoints,_tmpCheatPoints,_tmpCharMap,_tmpI,_tmpJ,_tmpUpdateAnswer,_tmpLeftUnswer,_tmpNumQuestion,_tmpNumAnswer);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg2);
  }

  @Override
  public Object getFrontListGeoQuiz(final String nameQuestion,
      final Continuation<? super List<Quiz>> arg1) {
    final String _sql = "SELECT * FROM front_list WHERE nameQuestion LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (nameQuestion == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, nameQuestion);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Quiz>>() {
      @Override
      public List<Quiz> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "nameQuestion");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "user_name");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final int _cursorIndexOfStars = CursorUtil.getColumnIndexOrThrow(_cursor, "stars");
          final int _cursorIndexOfNumQ = CursorUtil.getColumnIndexOrThrow(_cursor, "numQ");
          final int _cursorIndexOfNumA = CursorUtil.getColumnIndexOrThrow(_cursor, "numA");
          final int _cursorIndexOfNumHQ = CursorUtil.getColumnIndexOrThrow(_cursor, "numHQ");
          final int _cursorIndexOfStarsAll = CursorUtil.getColumnIndexOrThrow(_cursor, "starsAll");
          final List<Quiz> _result = new ArrayList<Quiz>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Quiz _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpNameQuestion;
            if (_cursor.isNull(_cursorIndexOfNameQuestion)) {
              _tmpNameQuestion = null;
            } else {
              _tmpNameQuestion = _cursor.getString(_cursorIndexOfNameQuestion);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpData;
            if (_cursor.isNull(_cursorIndexOfData)) {
              _tmpData = null;
            } else {
              _tmpData = _cursor.getString(_cursorIndexOfData);
            }
            final int _tmpStars;
            _tmpStars = _cursor.getInt(_cursorIndexOfStars);
            final int _tmpNumQ;
            _tmpNumQ = _cursor.getInt(_cursorIndexOfNumQ);
            final int _tmpNumA;
            _tmpNumA = _cursor.getInt(_cursorIndexOfNumA);
            final int _tmpNumHQ;
            _tmpNumHQ = _cursor.getInt(_cursorIndexOfNumHQ);
            final int _tmpStarsAll;
            _tmpStarsAll = _cursor.getInt(_cursorIndexOfStarsAll);
            _item = new Quiz(_tmpId,_tmpNameQuestion,_tmpUserName,_tmpData,_tmpStars,_tmpNumQ,_tmpNumA,_tmpNumHQ,_tmpStarsAll);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @Override
  public Flow<List<Quiz>> getFrontListAdapter() {
    final String _sql = "SELECT * FROM front_list";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"front_list"}, new Callable<List<Quiz>>() {
      @Override
      public List<Quiz> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "nameQuestion");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "user_name");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final int _cursorIndexOfStars = CursorUtil.getColumnIndexOrThrow(_cursor, "stars");
          final int _cursorIndexOfNumQ = CursorUtil.getColumnIndexOrThrow(_cursor, "numQ");
          final int _cursorIndexOfNumA = CursorUtil.getColumnIndexOrThrow(_cursor, "numA");
          final int _cursorIndexOfNumHQ = CursorUtil.getColumnIndexOrThrow(_cursor, "numHQ");
          final int _cursorIndexOfStarsAll = CursorUtil.getColumnIndexOrThrow(_cursor, "starsAll");
          final List<Quiz> _result = new ArrayList<Quiz>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Quiz _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpNameQuestion;
            if (_cursor.isNull(_cursorIndexOfNameQuestion)) {
              _tmpNameQuestion = null;
            } else {
              _tmpNameQuestion = _cursor.getString(_cursorIndexOfNameQuestion);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpData;
            if (_cursor.isNull(_cursorIndexOfData)) {
              _tmpData = null;
            } else {
              _tmpData = _cursor.getString(_cursorIndexOfData);
            }
            final int _tmpStars;
            _tmpStars = _cursor.getInt(_cursorIndexOfStars);
            final int _tmpNumQ;
            _tmpNumQ = _cursor.getInt(_cursorIndexOfNumQ);
            final int _tmpNumA;
            _tmpNumA = _cursor.getInt(_cursorIndexOfNumA);
            final int _tmpNumHQ;
            _tmpNumHQ = _cursor.getInt(_cursorIndexOfNumHQ);
            final int _tmpStarsAll;
            _tmpStarsAll = _cursor.getInt(_cursorIndexOfStarsAll);
            _item = new Quiz(_tmpId,_tmpNameQuestion,_tmpUserName,_tmpData,_tmpStars,_tmpNumQ,_tmpNumA,_tmpNumHQ,_tmpStarsAll);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<ApiQuestion> getDateGenerationQuestion(final String systemDate) {
    final String _sql = "SELECT * FROM table_generate_question WHERE date LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (systemDate == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, systemDate);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
      final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
      final int _cursorIndexOfQuestionTranslate = CursorUtil.getColumnIndexOrThrow(_cursor, "questionTranslate");
      final int _cursorIndexOfAnswerTranslate = CursorUtil.getColumnIndexOrThrow(_cursor, "answerTranslate");
      final List<ApiQuestion> _result = new ArrayList<ApiQuestion>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ApiQuestion _item;
        final Integer _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getInt(_cursorIndexOfId);
        }
        final String _tmpDate;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmpDate = null;
        } else {
          _tmpDate = _cursor.getString(_cursorIndexOfDate);
        }
        final String _tmpQuestion;
        if (_cursor.isNull(_cursorIndexOfQuestion)) {
          _tmpQuestion = null;
        } else {
          _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
        }
        final String _tmpAnswer;
        if (_cursor.isNull(_cursorIndexOfAnswer)) {
          _tmpAnswer = null;
        } else {
          _tmpAnswer = _cursor.getString(_cursorIndexOfAnswer);
        }
        final String _tmpQuestionTranslate;
        if (_cursor.isNull(_cursorIndexOfQuestionTranslate)) {
          _tmpQuestionTranslate = null;
        } else {
          _tmpQuestionTranslate = _cursor.getString(_cursorIndexOfQuestionTranslate);
        }
        final String _tmpAnswerTranslate;
        if (_cursor.isNull(_cursorIndexOfAnswerTranslate)) {
          _tmpAnswerTranslate = null;
        } else {
          _tmpAnswerTranslate = _cursor.getString(_cursorIndexOfAnswerTranslate);
        }
        _item = new ApiQuestion(_tmpId,_tmpDate,_tmpQuestion,_tmpAnswer,_tmpQuestionTranslate,_tmpAnswerTranslate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
