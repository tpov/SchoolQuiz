package com.tpov.schoolquiz.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.tpov.schoolquiz.data.database.CrimeDao;
import com.tpov.schoolquiz.data.database.CrimeDatabase;

import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CrimeDatabase_Impl extends CrimeDatabase {
  private volatile CrimeDao _crimeDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `table_data` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `idNameQuiz` TEXT NOT NULL, `userName` TEXT, `data` TEXT NOT NULL, `codeAnswer` TEXT, `codeMap` TEXT, `currentIndex` INTEGER NOT NULL, `isCheater` INTEGER NOT NULL, `constCurrentIndex` INTEGER NOT NULL, `points` INTEGER NOT NULL, `persentPoints` INTEGER NOT NULL, `cheatPoints` INTEGER NOT NULL, `charMap` TEXT, `i` INTEGER NOT NULL, `j` INTEGER NOT NULL, `updateAnswer` INTEGER NOT NULL, `leftUnswer` INTEGER, `numQuestion` INTEGER, `numAnswer` INTEGER)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `new_user_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `nameQuestion` TEXT NOT NULL, `answerQuestion` INTEGER NOT NULL, `typeQuestion` INTEGER NOT NULL, `idListNameQuestion` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `front_list` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `nameQuestion` TEXT NOT NULL, `user_name` TEXT NOT NULL, `data` TEXT NOT NULL, `stars` INTEGER NOT NULL, `numQ` INTEGER NOT NULL, `numA` INTEGER NOT NULL, `numHQ` INTEGER NOT NULL, `starsAll` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `table_generate_question` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `date` TEXT NOT NULL, `question` TEXT NOT NULL, `answer` TEXT NOT NULL, `questionTranslate` TEXT NOT NULL, `answerTranslate` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f978f2a63da0799359ec44926d5991de')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `table_data`");
        _db.execSQL("DROP TABLE IF EXISTS `new_user_table`");
        _db.execSQL("DROP TABLE IF EXISTS `front_list`");
        _db.execSQL("DROP TABLE IF EXISTS `table_generate_question`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTableData = new HashMap<String, TableInfo.Column>(19);
        _columnsTableData.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("idNameQuiz", new TableInfo.Column("idNameQuiz", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("userName", new TableInfo.Column("userName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("data", new TableInfo.Column("data", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("codeAnswer", new TableInfo.Column("codeAnswer", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("codeMap", new TableInfo.Column("codeMap", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("currentIndex", new TableInfo.Column("currentIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("isCheater", new TableInfo.Column("isCheater", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("constCurrentIndex", new TableInfo.Column("constCurrentIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("points", new TableInfo.Column("points", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("persentPoints", new TableInfo.Column("persentPoints", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("cheatPoints", new TableInfo.Column("cheatPoints", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("charMap", new TableInfo.Column("charMap", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("i", new TableInfo.Column("i", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("j", new TableInfo.Column("j", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("updateAnswer", new TableInfo.Column("updateAnswer", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("leftUnswer", new TableInfo.Column("leftUnswer", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("numQuestion", new TableInfo.Column("numQuestion", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableData.put("numAnswer", new TableInfo.Column("numAnswer", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTableData = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTableData = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTableData = new TableInfo("table_data", _columnsTableData, _foreignKeysTableData, _indicesTableData);
        final TableInfo _existingTableData = TableInfo.read(_db, "table_data");
        if (! _infoTableData.equals(_existingTableData)) {
          return new RoomOpenHelper.ValidationResult(false, "table_data(com.tpov.geoquiz.entities.QuizDetail).\n"
                  + " Expected:\n" + _infoTableData + "\n"
                  + " Found:\n" + _existingTableData);
        }
        final HashMap<String, TableInfo.Column> _columnsNewUserTable = new HashMap<String, TableInfo.Column>(5);
        _columnsNewUserTable.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewUserTable.put("nameQuestion", new TableInfo.Column("nameQuestion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewUserTable.put("answerQuestion", new TableInfo.Column("answerQuestion", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewUserTable.put("typeQuestion", new TableInfo.Column("typeQuestion", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNewUserTable.put("idListNameQuestion", new TableInfo.Column("idListNameQuestion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNewUserTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNewUserTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNewUserTable = new TableInfo("new_user_table", _columnsNewUserTable, _foreignKeysNewUserTable, _indicesNewUserTable);
        final TableInfo _existingNewUserTable = TableInfo.read(_db, "new_user_table");
        if (! _infoNewUserTable.equals(_existingNewUserTable)) {
          return new RoomOpenHelper.ValidationResult(false, "new_user_table(com.tpov.geoquiz.entities.Question).\n"
                  + " Expected:\n" + _infoNewUserTable + "\n"
                  + " Found:\n" + _existingNewUserTable);
        }
        final HashMap<String, TableInfo.Column> _columnsFrontList = new HashMap<String, TableInfo.Column>(9);
        _columnsFrontList.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFrontList.put("nameQuestion", new TableInfo.Column("nameQuestion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFrontList.put("user_name", new TableInfo.Column("user_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFrontList.put("data", new TableInfo.Column("data", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFrontList.put("stars", new TableInfo.Column("stars", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFrontList.put("numQ", new TableInfo.Column("numQ", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFrontList.put("numA", new TableInfo.Column("numA", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFrontList.put("numHQ", new TableInfo.Column("numHQ", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFrontList.put("starsAll", new TableInfo.Column("starsAll", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFrontList = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFrontList = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFrontList = new TableInfo("front_list", _columnsFrontList, _foreignKeysFrontList, _indicesFrontList);
        final TableInfo _existingFrontList = TableInfo.read(_db, "front_list");
        if (! _infoFrontList.equals(_existingFrontList)) {
          return new RoomOpenHelper.ValidationResult(false, "front_list(com.tpov.geoquiz.entities.Quiz).\n"
                  + " Expected:\n" + _infoFrontList + "\n"
                  + " Found:\n" + _existingFrontList);
        }
        final HashMap<String, TableInfo.Column> _columnsTableGenerateQuestion = new HashMap<String, TableInfo.Column>(6);
        _columnsTableGenerateQuestion.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableGenerateQuestion.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableGenerateQuestion.put("question", new TableInfo.Column("question", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableGenerateQuestion.put("answer", new TableInfo.Column("answer", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableGenerateQuestion.put("questionTranslate", new TableInfo.Column("questionTranslate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableGenerateQuestion.put("answerTranslate", new TableInfo.Column("answerTranslate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTableGenerateQuestion = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTableGenerateQuestion = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTableGenerateQuestion = new TableInfo("table_generate_question", _columnsTableGenerateQuestion, _foreignKeysTableGenerateQuestion, _indicesTableGenerateQuestion);
        final TableInfo _existingTableGenerateQuestion = TableInfo.read(_db, "table_generate_question");
        if (! _infoTableGenerateQuestion.equals(_existingTableGenerateQuestion)) {
          return new RoomOpenHelper.ValidationResult(false, "table_generate_question(com.tpov.geoquiz.entities.ApiQuestion).\n"
                  + " Expected:\n" + _infoTableGenerateQuestion + "\n"
                  + " Found:\n" + _existingTableGenerateQuestion);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "f978f2a63da0799359ec44926d5991de", "1625aac2da39fd2344a2596f0209a0f0");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "table_data","new_user_table","front_list","table_generate_question");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `table_data`");
      _db.execSQL("DELETE FROM `new_user_table`");
      _db.execSQL("DELETE FROM `front_list`");
      _db.execSQL("DELETE FROM `table_generate_question`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CrimeDao.class, CrimeDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList(new CrimeDatabase_AutoMigration_1_3_Impl());
  }

  @Override
  public CrimeDao getCrimeDao() {
    if (_crimeDao != null) {
      return _crimeDao;
    } else {
      synchronized(this) {
        if(_crimeDao == null) {
          _crimeDao = new CrimeDao_Impl(this);
        }
        return _crimeDao;
      }
    }
  }
}
