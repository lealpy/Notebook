package com.lealpy.notebook.ui.calendar

import android.R
import androidx.lifecycle.ViewModel
import com.lealpy.notebook.data.repository.NotesRepository
import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel : ViewModel() {


}


    /*
    val events = mutableListOf<EventDay>()
    init  {
        val notes = notesRepository.getAllNotesFromDB()

        notes?.forEach { note ->
            val dateStart: Long = note?.dateStart ?: 0
            val yearStart = SimpleDateFormat("yyyy").format(Date(dateStart)).toInt()
            val monthStart = SimpleDateFormat("MM").format(Date(dateStart)).toInt() - 1
            val dayStart = SimpleDateFormat("dd").format(Date(dateStart)).toInt()

            val eventDay = EventDay(GregorianCalendar(yearStart, monthStart, dayStart, 0, 0),
                R.drawable.ic_notification_overlay)
            events.add(eventDay)
        }
    }
     */

