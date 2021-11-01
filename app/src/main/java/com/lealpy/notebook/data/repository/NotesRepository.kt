package com.lealpy.notebook.data.repository

import com.lealpy.notebook.data.models.Note
import io.realm.Realm

class NotesRepository {

    private var realm = Realm.getDefaultInstance()

    fun getNoteFromDB(id: Long?): Note? {
        return realm.where(Note::class.java)
            .equalTo("id", id)
            .findFirst()
    }

    fun getNewID () : Long? {
        val currentIdNumber: Number? = realm.where(Note::class.java).max("id")
        return if (currentIdNumber == null) {
            1
        } else {
            currentIdNumber.toLong() + 1
        }
    }

    fun addNoteToDB(note : Note) {
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(note)
        realm.commitTransaction()
    }

    fun changeNoteInDB(note : Note) {
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(note)
        realm.commitTransaction()
    }

    fun deleteNoteFromDB(id: Long?) {
        realm.beginTransaction()
        realm
            .where(Note::class.java)
            .equalTo("id", id)
            .findFirst()
            ?.deleteFromRealm()
        realm.commitTransaction()
    }

}
