package com.trezza.superscudetto.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase

abstract class DBLifecycle(
  val dbName: String,
  val version: Int = 1
) {

  lateinit var sqLiteDatabase: SQLiteDatabase

  fun onAttach(context: Context) {
    if (!::sqLiteDatabase.isInitialized) {
      val dbFile = context.getDatabasePath(dbName)
      val existingDb = dbFile.exists()
      sqLiteDatabase = context.openOrCreateDatabase(
        dbName,
        Context.MODE_PRIVATE,
        null
      )
      if (!existingDb) {
        initializeDb(sqLiteDatabase, dbName, version)
      } else {
        val oldVersion = sqLiteDatabase.version
        if (version != oldVersion) {
          dbVersionChanged(sqLiteDatabase, dbName, oldVersion, version)
        }
      }
    }
  }

  open fun dbVersionChanged(
    sqLiteDatabase: SQLiteDatabase?,
    dbName: String,
    oldVersion: Int,
    newVersion: Int
  ) {
  }

  abstract fun initializeDb(sqLiteDatabase: SQLiteDatabase, dbName: String, version: Int)

  fun onDetach() {
    sqLiteDatabase.close()
  }
}

