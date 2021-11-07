package com.lealpy.notebook.data.repository

import com.lealpy.notebook.data.db.RealmAppDB
import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.utils.Const
import java.util.*

class NotesRepository {

    private var realmDB = RealmAppDB()

    fun getNoteFromDB(id: Long?): Note? {
        return realmDB.getNoteFromDB(id)
    }

    fun getNewID () : Long {
        val lastId: Number? = realmDB.getLastID()
        return if (lastId == null) {
            1
        } else {
            lastId.toLong() + 1
        }
    }

    fun addNoteToDB(note : Note) {
        realmDB.addNoteToDB(note)
    }

    fun changeNoteInDB(note : Note) {
        realmDB.changeNoteInDB(note)
    }

    fun deleteNoteFromDB(id: Long?) {
        realmDB.deleteNoteFromDB(id)
    }

    fun getAllNotesFromDB(): MutableList<Note>? {
        return realmDB.getAllNotesFromDB()
    }

    fun getNotesByDate(date : Long): MutableList<Note>? {
        val dateAtDayStart = date - ((date + getGMT()) % Const.MILLIS_IN_DAY)
        return realmDB.getNotesByDate(dateAtDayStart)
    }

    private fun getGMT() : Long {
        return GregorianCalendar()
            .timeZone
            .rawOffset
            .toLong()
    }
}