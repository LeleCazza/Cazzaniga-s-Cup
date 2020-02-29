package com.trezza.superscudetto.db

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.trezza.superscudetto.R
import com.trezza.superscudetto.entity.*


class SuperscudettoDbLifecycle(val context: Context) : DBLifecycle("Superscudetto", 1) {

  private var TAG = "DATABASE"

  override fun initializeDb(sqLiteDatabase: SQLiteDatabase, dbName: String, version: Int) {
    val createDbQuery = context.resources
      .openRawResource(R.raw.create_superscudetto).asString()
    DatabaseUtils.createDbFromSqlStatements(
      context,
      dbName,
      version,
      createDbQuery
    )
    //inserisco le superscudettoDb nel database
    insert(lele)
    insert(fra)
    insert(babbo)
    //inserisco i vecchi punteggi registrati nel databse
    inserisciDati(Risultati.getStorico(risultatiBarcano2018, risultatiMorbidiAmici2018, risultatiVincera2018, 2018).iterator())
    inserisciDati(Risultati.getStorico(risultatiBarcano2019, risultatiMorbidiAmici2019, risultatiVincera2019, 2019).iterator())
  }

  private fun inserisciDati(iterator: Iterator<Risultati>){
    while(iterator.hasNext()){
      Log.i("INSERISCO RISULTATO", "DAJJEE")
      insert(iterator.next())
    }
  }

  override fun dbVersionChanged(
    sqLiteDatabase: SQLiteDatabase?,
    dbName: String,
    oldVersion: Int,
    newVersion: Int
  ) {
    sqLiteDatabase?.apply {
      var dropDbQuery = context.resources
        .openRawResource(R.raw.drop_squadre).asString()
      execSQL(dropDbQuery)
      dropDbQuery = context.resources
        .openRawResource(R.raw.drop_risultati).asString()
      execSQL(dropDbQuery)
      initializeDb(this, dbName, newVersion)
    }
  }

  fun insert(squadre: Squadre): Long {
    Log.d(TAG, "INSERT_DB")
    return sqLiteDatabase.insert(
      "SQUADRE",
      "descrizione",
      ContentValues().apply {
        put("_id", squadre.id)
        put("descrizione", squadre.descrizione)
        put("nome", squadre.nome)
        put("massimoPunti", squadre.massimoPunti)
      }
    )
  }

  fun insert(risultati: Risultati): Long {
    Log.d(TAG, "INSERT_DB")
    return sqLiteDatabase.insert(
      "RISULTATI",
      "descrizione",
      ContentValues().apply {
        put("descrizione", risultati.descrizione)
        put("squadra", risultati.squadra)
        put("giornata", risultati.giornata)
        put("punti", risultati.punti)
        put("anno", risultati.anno)      }
    )
  }

  fun delete(id: Int) : Int{
    Log.d(TAG, "DELETE_DB")
    return sqLiteDatabase.delete(
      "SQUADRE",
      "${"_id"} = ?",
      arrayOf("$id")
    )
  }

  fun update(squadre: Squadre): Int {
    Log.d(TAG, "UPDATE_PUNTI_DB");
    return sqLiteDatabase.update(
      "SQUADRE",
      ContentValues().apply {
        put("_id", squadre.id)
        put("descrizione", squadre.descrizione)
        put("nome", squadre.nome)
        put("massimoPunti", squadre.massimoPunti)
      },
      "${"_id"} = ?",
      arrayOf("${squadre.id}")
    )
  }

  fun findById(id: Int): Squadre? {
    Log.d(TAG, "FIND_DB")
    sqLiteDatabase.query(
      "SQUADRE",
      null,
      " ${"_id"} = ?",
      arrayOf("$id"),
      null,
      null,
      null
    ).use { cursor ->
      if (cursor.moveToNext()) {
        val descrizione = cursor.getString(cursor.getColumnIndex("descrizione"))
        val nome = cursor.getString(cursor.getColumnIndex("nome"))
        val punti = cursor.getDouble(cursor.getColumnIndex("massimoPunti"))
        return Squadre(id, descrizione, nome, punti)
      }
    }
    return null
  }

  fun select(anno: String): List<Risultati>{
    val Risultati = mutableListOf<Risultati>()
    Log.d(TAG, "SELECT_DB")
    sqLiteDatabase.query(
      "RISULTATI",
      null,
      "${"anno"} = ? ",
      arrayOf("$anno"),
      null,
      null,
      null,
      null
    ).use { cursor ->
      while (cursor.moveToNext()) {
        val descrizione = cursor.getString(cursor.getColumnIndex("descrizione"))
        val squadra = cursor.getString(cursor.getColumnIndex("squadra"))
        val giornata = cursor.getInt(cursor.getColumnIndex("giornata"))
        val punti = cursor.getDouble(cursor.getColumnIndex("punti"))
        val anno = cursor.getInt(cursor.getColumnIndex("anno"))
        Risultati.add(Risultati(descrizione,squadra,giornata,punti,anno))
      }
    }
    return Risultati
  }

  fun select(id: String, anno: String): List<Risultati>{
    val Risultati = mutableListOf<Risultati>()
    Log.d(TAG, "SELECT_DB")
    sqLiteDatabase.query(
      "RISULTATI",
      null,
      " ${"squadra"} = ? AND ${"anno"} = ? ",
      arrayOf("$id","$anno"),
      null,
      null,
      null
    ).use { cursor ->
      while (cursor.moveToNext()) {
        val descrizione = cursor.getString(cursor.getColumnIndex("descrizione"))
        val squadra = cursor.getString(cursor.getColumnIndex("squadra"))
        val giornata = cursor.getInt(cursor.getColumnIndex("giornata"))
        val punti = cursor.getDouble(cursor.getColumnIndex("punti"))
        val anno = cursor.getInt(cursor.getColumnIndex("anno"))
        Risultati.add(Risultati(descrizione,squadra,giornata,punti,anno))
      }
    }
    return Risultati
  }
}


