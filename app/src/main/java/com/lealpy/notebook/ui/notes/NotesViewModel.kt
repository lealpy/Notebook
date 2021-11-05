package com.lealpy.notebook.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.data.repository.NotesRepository
import java.text.SimpleDateFormat
import java.util.*

class NotesViewModel : ViewModel() {

    private val notesRepository = NotesRepository()

    private val _notesLD = MutableLiveData<List<Note>>(emptyList())
    val notesLD: LiveData<List<Note>> = _notesLD

    private var date : Long = Date().time

    private val _dateString = MutableLiveData <String> ()
    val dateString : LiveData <String> = _dateString

    private val _startAddNote = MutableLiveData <Long> ()
    val startNewNote : LiveData <Long> = _startAddNote

    fun onGotDate(date: Long) {
        this.date = date
    }

    fun setNotes () {
        val notes = notesRepository.getNotesByDate(date)
        notes?.sortBy { it.dateStart }
        _notesLD.value = notes ?: emptyList()
        _dateString.value = SimpleDateFormat("dd.MM.yyyy").format(Date(date))
    }

    fun onAddNoteBntClicked() {
        _startAddNote.value = date
    }

    fun onDateSelected(dateCalendar: Calendar?) {
        this.date = dateCalendar?.timeInMillis ?: 0
        setNotes()
    }
}