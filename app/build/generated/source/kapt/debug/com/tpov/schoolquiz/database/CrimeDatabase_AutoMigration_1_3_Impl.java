package com.tpov.schoolquiz.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.lang.Override;
import java.lang.SuppressWarnings;

@SuppressWarnings({"unchecked", "deprecation"})
class CrimeDatabase_AutoMigration_1_3_Impl extends Migration {
  public CrimeDatabase_AutoMigration_1_3_Impl() {
    super(1, 3);
  }

  @Override
  public void migrate(@NonNull SupportSQLiteDatabase database) {
  }
}
