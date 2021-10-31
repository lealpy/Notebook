package com.lealpy.notebook.data.repository

import android.util.Log
import com.lealpy.notebook.data.models.Note
import io.realm.Realm
import java.lang.Exception
import io.realm.RealmResults

class NotesRepository {

    private var realm = Realm.getDefaultInstance()

    fun getNoteFromDB (id : Long?) : Note? {
        return realm.where(Note::class.java)
            .equalTo("id", id)
            .findFirst()
    }

    fun getLastID () : Long? {
        val currentIdNumber: Number? = realm.where(Note::class.java).max("id")
        return if (currentIdNumber == null) {
            1
        } else {
            currentIdNumber.toLong() + 1
        }
    }

    fun addNoteToDB(note : Note) {
        try {

            realm.beginTransaction()
            realm.copyToRealmOrUpdate(note)
            realm.commitTransaction()

            Log.d("MyLog", "Элемент добавлен в БД")

        } catch (e : Exception) {
            Log.d("MyLog", "Ошибка при добавлении элемента в БД: $e")
        }
    }

    fun changeNoteInDB(id: Long?, dateStart: Long, dateFinish: Long, name: String, description: String) {
        try {

            realm.beginTransaction()

            val note = Note(id, dateStart, dateFinish, name, description)

            realm.copyToRealmOrUpdate(note)
            realm.commitTransaction()


            Log.d("MyLog", "Элемент изменен в БД")

        } catch (e : Exception) {
            Log.d("MyLog", "Ошибка при изменении элемента в БД: $e")
        }
    }

    fun deleteNoteFromDB(id: Long?) {
        try {

            realm.beginTransaction()

            realm
                .where(Note::class.java)
                .equalTo("id", id)
                .findFirst()
                ?.deleteFromRealm()

            //realm.copyToRealmOrUpdate(note)
            realm.commitTransaction()

            Log.d("MyLog", "Элемент удален из БД")

        } catch (e : Exception) {
            Log.d("MyLog", "Ошибка при удалении элемента из БД: $e")
        }
    }

}

