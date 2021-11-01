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

    private var date : Long? = null

    private val _dateString = MutableLiveData <String> (
        SimpleDateFormat("dd.MM.yyyy").format(Date())
            )
    val dateString : LiveData <String> = _dateString

    init {
        val notesDB : RealmResults<Note> = realm.where<Note>(Note::class.java).findAll()
        _notesLD.value = realm.copyFromRealm(notesDB)
    }

    fun onGotDate(date: Long) {
        this.date = date
        _dateString.value = SimpleDateFormat("dd.MM.yyyy").format(Date(date))
    }

}