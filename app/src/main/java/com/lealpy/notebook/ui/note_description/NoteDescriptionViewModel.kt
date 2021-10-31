package com.lealpy.notebook.ui.note_description

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.data.repository.NotesRepository
import io.realm.Realm
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NoteDescriptionViewModel : ViewModel() {

    private var notesRepository = NotesRepository()

//-------------------------Присвоить IDшник в LiveData-----------------------------------------//
    private val _idLD = MutableLiveData<Long>(1)
    val idLD: LiveData<Long> = _idLD

    val note = notesRepository.getNoteFromDB(idLD.value)

    val yearFinish = SimpleDateFormat("yyyy").format(note?.dateFinish?.let { Date(it) }).toInt()
    val monthFinish = SimpleDateFormat("MM").format(note?.dateFinish?.let { Date(it) }).toInt()
    val dayFinish = SimpleDateFormat("dd").format(note?.dateFinish?.let { Date(it) }).toInt()
    val hourFinish = SimpleDateFormat("HH").format(note?.dateFinish?.let { Date(it) }).toInt()
    val minuteFinish = SimpleDateFormat("mm").format(note?.dateFinish?.let { Date(it) }).toInt()


    val yearStart = SimpleDateFormat("yyyy").format(note?.dateStart?.let { Date(it) }).toInt()
    val monthStart = SimpleDateFormat("MM").format(note?.dateStart?.let { Date(it) }).toInt()
    val dayStart = SimpleDateFormat("dd").format(note?.dateStart?.let { Date(it) }).toInt()
    val hourStart = SimpleDateFormat("HH").format(note?.dateStart?.let { Date(it) }).toInt()
    val minuteStart = SimpleDateFormat("mm").format(note?.dateStart?.let { Date(it) }).toInt()


    fun getTimestamp(year : Int, month : Int, day : Int, hour : Int, minute : Int) : Long {
        val calendar: Calendar = GregorianCalendar(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    fun getTimeString(hour : Int, minute : Int) : String {
        return SimpleDateFormat("HH:mm").format(Date(getTimestamp(1970,0,1, hour, minute)))
    }

    fun getDateString(year : Int, month : Int, day : Int) : String {
        return SimpleDateFormat("dd.MM.yyyy").format(Date(getTimestamp(year,month,day, 0, 0)))
    }

    fun getTimeStringByTimestamp(timestamp : Long?) : String {
        return SimpleDateFormat("HH:mm").format(timestamp?.let { Date(it) })
    }

    fun getDateStringByTimestamp(timestamp : Long?) : String {
        return SimpleDateFormat("dd.MM.yyyy").format(timestamp?.let { Date(it) })
    }

    fun changeNoteInDB(id: Long?, dateStart: Long, dateFinish: Long, name: String, description: String) {

        notesRepository.changeNoteInDB(id, dateStart, dateFinish, name, description)
    }

    fun deleteNoteFromDB() {
        notesRepository.deleteNoteFromDB(note?.id)
    }

}