package com.lealpy.notebook.ui.calendar

import android.R
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.applandeo.materialcalendarview.EventDay
import com.lealpy.notebook.data.repository.NotesRepository
import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel : ViewModel() {

    private val notesRepository = NotesRepository()

    private val _events = MutableLiveData<MutableList<EventDay>>(mutableListOf<EventDay>())
    val events : LiveData<MutableList<EventDay>> = _events

    init {
        val notes = notesRepository.getAllNotesFromDB()
        notes?.forEach { note ->
            val dateStart: Long = note?.dateStart ?: 0
            val yearStart = SimpleDateFormat("yyyy").format(Date(dateStart)).toInt()
            val monthStart = SimpleDateFormat("MM").format(Date(dateStart)).toInt() - 1
            val dayStart = SimpleDateFormat("dd").format(Date(dateStart)).toInt()
            val eventDay = EventDay(
                GregorianCalendar(yearStart, monthStart, dayStart, 0, 0),
                R.drawable.ic_notification_overlay
            )
            _events.value?.add(eventDay)
        }
    }

    fun onClickedDate(time: Date) {

    }


}



