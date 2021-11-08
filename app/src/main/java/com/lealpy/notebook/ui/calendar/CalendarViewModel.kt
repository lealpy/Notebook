package com.lealpy.notebook.ui.calendar

import android.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.applandeo.materialcalendarview.EventDay
import com.lealpy.notebook.data.repository.NotesRepository
import com.lealpy.notebook.utils.AppUtils
import java.util.*

class CalendarViewModel: ViewModel() {

    private val notesRepository = NotesRepository()

    private val _events = MutableLiveData<MutableList<EventDay>>(mutableListOf())
    val events: LiveData<MutableList<EventDay>> = _events

    private val _startAddNote = MutableLiveData <Long>()
    val startNewNote: LiveData <Long> = _startAddNote

    init {
        val notes = notesRepository.getAllNotesFromDB()
        notes?.forEach { note ->
            val dateStart: Long = note.dateStart ?: 0
            val yearStart = AppUtils.getYearIntByTimestamp(dateStart)
            val monthStart = AppUtils.getMonthIntByTimestamp(dateStart)
            val dayStart = AppUtils.getDayIntByTimestamp(dateStart)
            val hourStart = AppUtils.getHourIntByTimestamp(dateStart)
            val minuteStart = AppUtils.getMinuteIntByTimestamp(dateStart)
            val eventDay = EventDay(
                GregorianCalendar(yearStart, monthStart, dayStart, hourStart, minuteStart),
                R.drawable.ic_notification_overlay
            )
            _events.value?.add(eventDay)
        }
    }

    fun onAddNoteBtnClicked() {
        _startAddNote.value = Date().time
    }
}