package com.lealpy.notebook.ui.new_note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.data.repository.NotesRepository
import java.text.SimpleDateFormat
import java.util.*

class NewNoteViewModel : ViewModel() {

    private val notesRepository = NotesRepository()

    private var yearStart = SimpleDateFormat("yyyy").format(Date()).toInt()
    private var monthStart = SimpleDateFormat("MM").format(Date()).toInt() - 1
    private var dayStart = SimpleDateFormat("dd").format(Date()).toInt()
    private var hourStart = SimpleDateFormat("HH").format(Date()).toInt()
    private var minuteStart = SimpleDateFormat("mm").format(Date()).toInt()
    private var yearFinish = yearStart
    private var monthFinish = monthStart
    private var dayFinish = dayStart
    private var hourFinish = hourStart + 1
    private var minuteFinish = minuteStart

    private var noteName = ""
    private var noteDescription = ""

    private val _dateStringStart = MutableLiveData<String> (
        getDateString(yearStart, monthStart, dayStart)
    )
    val dateStringStart : LiveData<String> = _dateStringStart

    private val _dateStringFinish = MutableLiveData<String> (
        getDateString(yearFinish, monthFinish, dayFinish)
    )
    val dateStringFinish : LiveData<String> = _dateStringFinish

    private val _timeStringStart = MutableLiveData<String> (
        getTimeString(hourStart, minuteStart)
    )
    val timeStringStart : LiveData<String> = _timeStringStart

    private val _timeStringFinish = MutableLiveData<String> (
        getTimeString(hourFinish, minuteFinish)
    )
    val timeStringFinish : LiveData<String> = _timeStringFinish

    private val _dateStartPickerData = MutableLiveData<DatePickerData?> (null)
    val dateStartPickerData : LiveData<DatePickerData?> = _dateStartPickerData

    private val _dateFinishPickerData = MutableLiveData<DatePickerData?> (null)
    val dateFinishPickerData : LiveData<DatePickerData?> = _dateFinishPickerData

    private val _timeStartPickerData = MutableLiveData<TimePickerData?> (null)
    val timeStartPickerData : LiveData<TimePickerData?> = _timeStartPickerData

    private val _timeFinishPickerData = MutableLiveData<TimePickerData?> (null)
    val timeFinishPickerData : LiveData<TimePickerData?> = _timeFinishPickerData



    private fun getTimestamp(year: Int, month: Int, day: Int, hour: Int, minute: Int) : Long {
        val calendar: Calendar = GregorianCalendar(year, month, day, hour, minute)
        return calendar.timeInMillis
    }



    private fun getDateString(year : Int, month : Int, day : Int) : String {
        return SimpleDateFormat("dd.MM.yyyy").format(Date(getTimestamp(year, month, day, 0, 0)))
    }

    private fun getTimeString(hour : Int, minute : Int) : String {
        return SimpleDateFormat("HH:mm").format(Date(getTimestamp(1970, 0, 1, hour, minute)))
    }



    private fun refreshDateLD() {
        _dateStringStart.value = getDateString(yearStart, monthStart, dayStart)
        _dateStringFinish.value = getDateString(yearFinish, monthFinish, dayFinish)
    }

    private fun refreshTimeLD() {
        _timeStringStart.value = getTimeString(hourStart, minuteStart)
        _timeStringFinish.value = getTimeString(hourFinish, minuteFinish)
    }



    fun onDateStartPickerClicked() {
        val datePickerData = DatePickerData (yearStart, monthStart, dayStart)
        _dateStartPickerData.value = datePickerData
    }

    fun onDateFinishPickerClicked() {
        val datePickerData = DatePickerData (yearFinish, monthFinish, dayFinish)
        _dateFinishPickerData.value = datePickerData
    }

    fun onTimeStartPickerClicked() {
        val timePickerData = TimePickerData (hourStart, minuteStart)
        _timeStartPickerData.value = timePickerData
    }

    fun onTimeFinishPickerClicked() {
        val timePickerData = TimePickerData (hourFinish, minuteFinish)
        _timeFinishPickerData.value = timePickerData
    }



    fun onDateStartPicked(year: Int, month: Int, dayOfMonth: Int) {
        yearStart = year
        monthStart = month
        dayStart = dayOfMonth
        _dateStartPickerData.value = null
        checkSelectedStartBeforeFinish()
    }

    fun onDateFinishPicked(year: Int, month: Int, dayOfMonth: Int) {
        yearFinish = year
        monthFinish = month
        dayFinish = dayOfMonth
        _dateFinishPickerData.value = null
        checkSelectedFinishAfterStart()
    }

    fun onTimeStartPicked(hour: Int, minute: Int) {
        hourStart = hour
        minuteStart = minute
        _timeStartPickerData.value = null
        checkSelectedStartBeforeFinish()
    }

    fun onTimeFinishPicked(hour: Int, minute: Int) {
        hourFinish = hour
        minuteFinish = minute
        _timeFinishPickerData.value = null
        checkSelectedFinishAfterStart()
    }



    private fun checkSelectedStartBeforeFinish() {
        val timestampStart = getTimestamp(yearStart, monthStart, dayStart, hourStart, minuteStart)
        val timestampFinish = getTimestamp(yearFinish, monthFinish, dayFinish, hourFinish, minuteFinish)
        if (timestampStart > timestampFinish ) {
            yearFinish = yearStart
            monthFinish = monthStart
            dayFinish = dayStart
            hourFinish = hourStart
            minuteFinish = minuteStart
        }
        refreshDateLD()
        refreshTimeLD()
    }

    private fun checkSelectedFinishAfterStart() {
        val timestampStart = getTimestamp(yearStart, monthStart, dayStart, hourStart, minuteStart)
        val timestampFinish = getTimestamp(yearFinish, monthFinish, dayFinish, hourFinish, minuteFinish)
        if (timestampStart > timestampFinish ) {
            yearStart = yearFinish
            monthStart = monthFinish
            dayStart = dayFinish
            hourStart = hourFinish
            minuteStart = minuteFinish
        }
        refreshDateLD()
        refreshTimeLD()
    }



    fun onAddNoteClicked(name: String, description: String) {
        noteName = name
        noteDescription = description
    }

    fun addNoteToDB() {
        val note = Note(
            notesRepository.getNewID(),
            getTimestamp(yearStart, monthStart, dayStart, hourStart, minuteStart),
            getTimestamp(yearFinish, monthFinish, dayFinish, hourFinish, minuteFinish),
            noteName,
            noteDescription)

        notesRepository.addNoteToDB(note)
    }

}



