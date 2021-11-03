package com.lealpy.notebook.ui.notes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.data.repository.NotesRepository
import java.text.SimpleDateFormat
import java.util.*

class NotesViewModel : ViewModel() {

    private val notesRepository = NotesRepository()

    private var notes : MutableList<Note>? = null

    private val _notesLD = MutableLiveData<List<Note>>(emptyList())
    val notesLD: LiveData<List<Note>> = _notesLD

    private var date : Long = Date().time

    private val _dateString = MutableLiveData <String> ()
    val dateString : LiveData <String> = _dateString

    private fun getGMT() : Long {
        return GregorianCalendar().timeZone.rawOffset.toLong()
    }

    fun onGotDate(date: Long?) {
        if (date != null) {
            this.date = date
        }
    }

    fun setNotes () {
        notes = notesRepository.getNotesByDate(date)
        notes?.sortBy { it.dateStart }
        _notesLD.value = notes ?: emptyList()
        _dateString.value = SimpleDateFormat("dd.MM.yyyy")?.format(Date(date))
    }

}

/*

    fun onGotDate(date: Long?) {
        if(date != null) {
            this.date = date
            _dateString.value = SimpleDateFormat("dd.MM.yyyy")?.format(Date(date))
            notes = notesRepository.getNotesByDate(date)
            notes?.sortBy{it.dateStart}
            _notesLD.value = notes ?: emptyList()
        }
        else {
            this.date =
        }
    }
 */