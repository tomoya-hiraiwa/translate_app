package edu.wsc2022.a01.mrtranslate.ViewModel

import android.content.ContentValues
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.wsc2022.a01.mrtranslate.DataBase.LibraryHelper
import edu.wsc2022.a01.mrtranslate.UI.LibData
import edu.wsc2022.a01.mrtranslate.UI.LibraryData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TranslateViewModel: ViewModel() {
    var libList = MutableLiveData<MutableList<LibraryData>>()
    private var originWordList = mutableListOf<LibraryData>()
    private var originTextList = mutableListOf<LibraryData>()
    suspend fun addData(data: LibData,helper: LibraryHelper,type: Int){
        val db = helper.writableDatabase
        val table = if (type == 1) LibraryHelper.wordTable else LibraryHelper.textTable
        val con = ContentValues().apply {
            put("fromLg",data.fromLg)
            put("toLg",data.toLg)
            put("fromText",data.fromText)
            put("toText",data.toText)
        }
        withContext(Dispatchers.IO){
            db.insert(table,null,con)
        }
    }
    suspend fun getData(helper: LibraryHelper,type: Int){
        val db = helper.readableDatabase
        val table = if (type == 1) LibraryHelper.wordTable else LibraryHelper.textTable
        originTextList.clear()
        originWordList.clear()
        withContext(Dispatchers.IO){
            val cursor = db.query(table,null,null,null,null,null,null)
            while (cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val fromLg = cursor.getString(cursor.getColumnIndexOrThrow("fromLg"))
                val toLg = cursor.getString(cursor.getColumnIndexOrThrow("toLg"))
                val fromText = cursor.getString(cursor.getColumnIndexOrThrow("fromText"))
                val toText = cursor.getString(cursor.getColumnIndexOrThrow("toText"))
                if (type == 1){
                    originWordList.add(LibraryData(id, fromLg, toLg, fromText, toText))
                }
                else{
                    originTextList.add(LibraryData(id, fromLg, toLg, fromText, toText))
                }
            }
            withContext(Dispatchers.Main){
                libList.value = if (type == 1) originWordList else originTextList
            }
        }
    }
    suspend fun deleteAllData(helper: LibraryHelper){
        val db = helper.writableDatabase
        withContext(Dispatchers.IO){
        db.delete(LibraryHelper.textTable,null,null)
        db.delete(LibraryHelper.wordTable,null,null)
        }
    }
}