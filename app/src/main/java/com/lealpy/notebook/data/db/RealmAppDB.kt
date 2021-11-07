package com.lealpy.notebook.data.db

import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.utils.Const
import io.realm.Realm
import io.realm.kotlin.where

class RealmAppDB : AppDB {

    private var realm = Realm.getDefaultInstance()

    override fun getNoteFromDB(id: Long?): Note? {
        return realm
            .where(Note::class.java)
            .equalTo("id", id)
            .findFirst()
    }

    override fun getLastID () : Number? {
        return realm
            .where(Note::class.java)
            .max("id")
    }

    override fun addNoteToDB(note : Note) {
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(note)
        realm.commitTransaction()
    }

    override fun changeNoteInDB(note : Note) {
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(note)
        realm.commitTransaction()
    }

    override fun deleteNoteFromDB(id: Long?) {
        realm.beginTransaction()
        realm
            .where(Note::class.java)
            .equalTo("id", id)
            .findFirst()
            ?.deleteFromRealm()
        realm.commitTransaction()
    }

    override fun getAllNotesFromDB(): MutableList<Note>? {
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

    override fun getNotesByDate(date : Long): MutableList<Note>? {
        realm.beginTransaction()
        val notes = realm
            .copyFromRealm(
                realm
                    .where(Note::class.java)
                    .between(
                        "dateStart",
                        date,
                        date + (Const.MILLIS_IN_DAY - 1)
                    )
                    .findAll()
            )
        realm.commitTransaction()
        return notes
    }
}