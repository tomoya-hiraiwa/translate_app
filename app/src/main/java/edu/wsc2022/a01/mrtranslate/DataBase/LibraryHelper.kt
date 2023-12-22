package edu.wsc2022.a01.mrtranslate.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LibraryHelper(context: Context): SQLiteOpenHelper(context,"db",null,1) {
    companion object{
      const val  wordTable = "word"
       const val textTable = "text"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.let {
            it.execSQL("create table ${wordTable} ("+" id integer primary key autoincrement, fromLg text, toLg text, fromText text, toText text)")
            it.execSQL("create table ${textTable} ("+" id integer primary key autoincrement, fromLg text, toLg text, fromText text, toText text)")
        }
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}