package com.lealpy.notebook.ui.note_description

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lealpy.notebook.data.models.DatePickerData
import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.data.models.TimePickerData
import com.lealpy.notebook.data.repository.NotesRepository
import java.text.SimpleDateFormat
import java.util.*

class NoteDescriptionViewModel : ViewModel() {

    private val notesRepository = NotesRepository()

    private var noteId : Long? = null
    private var note : Note? = null

    private var dateStart : Long = 0
    private var dateFinish : Long = 0

    private var yearStart = 0
    private var monthStart = 0
    private var dayStart = 0
    private var hourStart = 0
    private var minuteStart = 0
    private var yearFinish = 0
    private var monthFinish = 0
    private var dayFinish = 0
    private var hourFinish = 0
    private var minuteFinish = 0

    private val _noteName = MutableLiveData<String> (null)
    val noteName : LiveData<String> = _noteName

    private val _noteDescription = MutableLiveData<String> (null)
    val noteDescription : LiveData<String> = _noteDescription

    private val _dateStringStart = MutableLiveData<String> (null)
    val dateStringStart : LiveData<String> = _dateStringStart

    private val _dateStringFinish = MutableLiveData<String> (null)
    val dateStringFinish : LiveData<String> = _dateStringFinish

    private val _timeStringStart = MutableLiveData<String> (null)
    val timeStringStart : LiveData<String> = _timeStringStart

    private val _timeStringFinish = MutableLiveData<String> (null)
    val timeStringFinish : LiveData<String> = _timeStringFinish

    private val _dateStartPickerData = MutableLiveData<DatePickerData?> (null)
    val dateStartPickerData : LiveData<DatePickerData?> = _dateStartPickerData

    private val _dateFinishPickerData = MutableLiveData<DatePickerData?> (null)
    val dateFinishPickerData : LiveData<DatePickerData?> = _dateFinishPickerData

    private val _timeStartPickerData = MutableLiveData<TimePickerData?> (null)
    val timeStartPickerData : LiveData<TimePickerData?> = _timeStartPickerData

    private val _timeFinishPickerData = MutableLiveData<TimePickerData?> (null)
    val timeFinishPickerData : LiveData<TimePickerData?> = _timeFinishPickerData



    fun onGotId(id: Long) {
        noteId = id
        note = notesRepository.getNoteFromDB(noteId)

        dateStart = note?.dateStart ?: 0
        dateFinish = note?.dateFinish ?: 0

        yearStart = SimpleDateFormat("yyyy").format(Date(dateStart)).toInt()
        monthStart = SimpleDateFormat("MM").format(Date(dateStart)).toInt() -1
        dayStart = SimpleDateFormat("dd").format(Date(dateStart)).toInt()
        hourStart = SimpleDateFormat("HH").format(Date(dateStart)).toInt()
        minuteStart = SimpleDateFormat("mm").format(Date(dateStart)).toInt()
        yearFinish = SimpleDateFormat("yyyy").format(Date(dateFinish)).toInt()
        monthFinish = SimpleDateFormat("MM").format(Date(dateFinish)).toInt() -1
        dayFinish = SimpleDateFormat("dd").format(Date(dateFinish)).toInt()
        hourFinish = SimpleDateFormat("HH").format(Date(dateFinish)).toInt()
        minuteFinish = SimpleDateFormat("mm").format(Date(dateFinish)).toInt()

        _noteName.value = note?.name ?: ""
        _noteDescription.value = note?.description ?: ""
        _dateStringStart.value = getDateString(yearStart, monthStart, dayStart)
        _dateStringFinish.value = getDateString(yearFinish, monthFinish, dayFinish)
        _timeStringStart.value = getTimeString(hourStart, minuteStart)
        _timeStringFinish.value = getTimeString(hourFinish, minuteFinish)

    }


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



    fun onChangeNoteClicked(name: String, description: String) {
        _noteName.value = name
        _noteDescription.value = description
    }

    fun changeNoteInDB() {
        val note = Note(
            noteId,
            getTimestamp(yearStart, monthStart, dayStart, hourStart, minuteStart),
            getTimestamp(yearFinish, monthFinish, dayFinish, hourFinish, minuteFinish),
            _noteName.value,
            _noteDescription.value)

        notesRepository.changeNoteInDB(note)
    }

    fun deleteNoteFromDB() {
        notesRepository.deleteNoteFromDB(note?.id)
    }

}