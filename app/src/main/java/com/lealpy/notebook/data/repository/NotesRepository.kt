package com.lealpy.notebook.data.repository

import com.lealpy.notebook.data.models.Note
import io.realm.Realm
import io.realm.kotlin.where
import java.util.*

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

    fun getAllNotesFromDB(): MutableList<Note>? {
        realm.beginTransaction()
        val notes = realm
            .copyFromRealm(
                realm
                    .where<Note>()
                    .findAllAsync()
            )
        realm.commitTransaction()
        return notes
    }

    fun getNotesByDate(date : Long): MutableList<Note>? {
        val dateAtDayStart = date - ((date + getGMT()) % MILLIS_IN_DAY)
        realm.beginTransaction()
        val notes = realm
            .copyFromRealm(
                realm
                    .where(Note::class.java)
                    .between("dateStart", dateAtDayStart, dateAtDayStart + (MILLIS_IN_DAY-1))
                    .findAll()
            )
        realm.commitTransaction()
        return notes
    }

    private fun getGMT() : Long {
        return GregorianCalendar().timeZone.rawOffset.toLong()
    }

    companion object {
        const val MILLIS_IN_DAY = 86400000
    }
}

