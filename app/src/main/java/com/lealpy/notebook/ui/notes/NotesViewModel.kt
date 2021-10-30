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

    val currentYear = SimpleDateFormat("yyyy").format(Date()).toInt()
    val currentMonth = SimpleDateFormat("MM").format(Date()).toInt()
    val currentDay = SimpleDateFormat("dd").format(Date()).toInt()
    val currentHour = SimpleDateFormat("HH").format(Date()).toInt()
    val currentMinute = SimpleDateFormat("mm").format(Date()).toInt()

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

}