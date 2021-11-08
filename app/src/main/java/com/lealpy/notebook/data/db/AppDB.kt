package com.lealpy.notebook.data.db

import com.lealpy.notebook.data.models.Note

interface AppDB {

    fun getNoteFromDB(id: Long?): Note?

    fun getLastID(): Number?

    fun addNoteToDB(note: Note)

    fun changeNoteInDB(note: Note)

    fun deleteNoteFromDB(id: Long?)

    fun getAllNotesFromDB(): MutableList<Note>?

    fun getNotesByDate(date: Long): MutableList<Note>?

}