package com.lealpy.notebook.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lealpy.notebook.data.models.Note
import io.realm.Realm
import io.realm.RealmResults
import java.text.SimpleDateFormat
import java.util.*

class NotesViewModel : ViewModel() {

    private val _notesLD = MutableLiveData<List<Note>>(emptyList())
    val notesLD: LiveData<List<Note>> = _notesLD

    private var realm = Realm.getDefaultInstance()

    init {
        val notesDB : RealmResults<Note> = realm.where<Note>(Note::class.java).findAll()
        _notesLD.value = realm.copyFromRealm(notesDB)
    }

    fun getCurrentDate() : String {
        return SimpleDateFormat("dd.MM.yyyy").format(Date())
    }

}