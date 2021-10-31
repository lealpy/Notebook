package com.lealpy.notebook.ui.new_note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.data.repository.NotesRepository
import java.text.SimpleDateFormat
import java.util.*

class NewNoteViewModel : ViewModel() {

//-----------------Repository-----------------------------------------------------

    private var notesRepository = NotesRepository()

    fun addNoteToDB() {
        val note = Note(
            notesRepository.getLastID(),
            _timestampStart.value,
            _timestampFinish.value,
            _noteName.value,
            _description.value)

        notesRepository.addNoteToDB(note)
    }

//-----------------LD for date and time values-------------------------------------

    private val currentYear = SimpleDateFormat("yyyy").format(Date()).toInt()
    private val currentMonth = SimpleDateFormat("MM").format(Date()).toInt()
    private val currentDay = SimpleDateFormat("dd").format(Date()).toInt()
    private val currentHour = SimpleDateFormat("HH").format(Date()).toInt()
    private val currentMinute = SimpleDateFormat("mm").format(Date()).toInt()

    private val _yearStart = MutableLiveData<Int>(currentYear)
    val yearStart: LiveData<Int> = _yearStart

    private val _monthStart = MutableLiveData<Int>(currentMonth - 1)
    val monthStart: LiveData<Int> = _monthStart

    private val _dayStart = MutableLiveData<Int>(currentDay)
    val dayStart: LiveData<Int> = _dayStart

    private val _hourStart = MutableLiveData<Int>(currentHour)
    val hourStart: LiveData<Int> = _hourStart

    private val _minuteStart = MutableLiveData<Int>(currentMinute)
    val minuteStart: LiveData<Int> = _minuteStart

    private val _yearFinish = MutableLiveData<Int>(currentYear)
    val yearFinish: LiveData<Int> = _yearFinish

    private val _monthFinish = MutableLiveData<Int>(currentMonth - 1)
    val monthFinish: LiveData<Int> = _monthFinish

    private val _dayFinish = MutableLiveData<Int>(currentDay)
    val dayFinish: LiveData<Int> = _dayFinish

    private val _hourFinish = MutableLiveData<Int>(currentHour + 1)
    val hourFinish: LiveData<Int> = _hourFinish

    private val _minuteFinish = MutableLiveData<Int>(currentMinute)
    val minuteFinish: LiveData<Int> = _minuteFinish

//-----------------LD for Timestamp-------------------------------------

    private val _timestampStart = MutableLiveData<Long> (
        getTimestamp(_yearStart.value, _monthStart.value, _dayStart.value, _hourStart.value, _minuteStart.value)
    )
    val timestampStart : LiveData<Long> = _timestampStart

    private val _timestampFinish = MutableLiveData<Long> (
        getTimestamp(_yearFinish.value, _monthFinish.value, _dayFinish.value, _hourFinish.value, _minuteFinish.value)
    )
    val timestampFinish : LiveData<Long> = _timestampFinish



    private fun getTimestamp(year: Int?, month: Int?, day: Int?, hour: Int?, minute: Int?) : Long {
        val calendar: Calendar = if(year != null && month != null && day != null && hour != null && minute != null) { // Криво, мне не нравится
            GregorianCalendar(year, month, day, hour, minute)
        } else {
            GregorianCalendar(1970, 0, 1, 0, 0)
        }
        return calendar.timeInMillis
    }

//-----------------LD for view fields-------------------------------------

    private val _noteName = MutableLiveData<String> ("")
    val noteName : LiveData<String> = _noteName

    private val _description = MutableLiveData<String> ("")
    val description : LiveData<String> = _description

    private val _dateStringStart = MutableLiveData<String> (
        getDateString(_yearStart.value, _monthStart.value, _dayStart.value)
    )
    val dateStringStart : LiveData<String> = _dateStringStart

    private val _dateStringFinish = MutableLiveData<String> (
        getDateString(_yearFinish.value, _monthFinish.value, _dayFinish.value)
    )
    val dateStringFinish : LiveData<String> = _dateStringFinish

    private val _timeStringStart = MutableLiveData<String> (
        getTimeString(_hourStart.value, _minuteStart.value)
    )
    val timeStringStart : LiveData<String> = _timeStringStart

    private val _timeStringFinish = MutableLiveData<String> (
        getTimeString(_hourFinish.value, _minuteFinish.value)
    )
    val timeStringFinish : LiveData<String> = _timeStringFinish



    private fun getDateString(year : Int?, month : Int?, day : Int?) : String {
        return if(year != null && month != null && day != null) {
            SimpleDateFormat("dd.MM.yyyy").format(Date(getTimestamp(year, month, day, 0, 0)))
        }
        else ""
    }

    private fun getTimeString(hour : Int?, minute : Int?) : String {
        return if(hour != null && minute != null) {
            SimpleDateFormat("HH:mm").format(Date(getTimestamp(1970, 0, 1, hour, minute)))
        }
        else ""
    }

    //-----------------Refresh Live Data functions-------------------------------------

    private fun refreshTimestamp() {
        _timestampStart.value = getTimestamp(_yearStart.value, _monthStart.value, _dayStart.value, _hourStart.value, _minuteStart.value)
        _timestampFinish.value = getTimestamp(_yearFinish.value, _monthFinish.value, _dayFinish.value, _hourFinish.value, _minuteFinish.value)
    }

    private fun refreshDateLD() {
        _dateStringStart.value = getDateString(_yearStart.value, _monthStart.value, _dayStart.value)
        _dateStringFinish.value = getDateString(_yearFinish.value, _monthFinish.value, _dayFinish.value)
        refreshTimestamp()
    }

    private fun refreshTimeLD() {
        _timeStringStart.value = getTimeString(_hourStart.value, _minuteStart.value)
        _timeStringFinish.value = getTimeString(_hourFinish.value, _minuteFinish.value)
        refreshTimestamp()
    }

    //-----------------callbacks from fragment-------------------------------------

    private fun equateStartToFinish () {
        _yearStart.value = _yearFinish.value
        _monthStart.value = _monthFinish.value
        _dayStart.value = _dayFinish.value
        _hourStart.value = _hourFinish.value
        _minuteStart.value = _minuteFinish.value
        refreshDateLD()
        refreshTimeLD()
    }

    private fun equateFinishToStart () {
        _yearFinish.value = _yearStart.value
        _monthFinish.value = _monthStart.value
        _dayFinish.value = _dayStart.value
        _hourFinish.value = _hourStart.value
        _minuteFinish.value = _minuteStart.value
        refreshDateLD()
        refreshTimeLD()
    }

    fun onDateStartClicked(year: Int, month: Int, day: Int) {
        _yearStart.value = year
        _monthStart.value = month
        _dayStart.value = day
        refreshDateLD()
        if (_timestampFinish.value != null && _timestampStart.value != null && _timestampFinish.value!! < _timestampStart.value!!) {
            equateFinishToStart ()
        }
    }

    fun onDateFinishClicked(year: Int, month: Int, day: Int) {
        _yearFinish.value = year
        _monthFinish.value = month
        _dayFinish.value = day
        refreshDateLD()
        if (_timestampFinish.value != null && _timestampStart.value != null && _timestampFinish.value!! < _timestampStart.value!!) {
            equateStartToFinish()
        }
    }

    fun onTimeStartClicked(hour: Int, minute: Int) {
        _hourStart.value = hour
        _minuteStart.value = minute
        refreshTimeLD()
        if (_timestampFinish.value != null && _timestampStart.value != null && _timestampFinish.value!! < _timestampStart.value!!) {
            equateFinishToStart()
        }
    }

    fun onTimeFinishClicked(hour: Int, minute: Int) {
        _hourFinish.value = hour
        _minuteFinish.value = minute
        refreshTimeLD()
        if (_timestampFinish.value != null && _timestampStart.value != null && _timestampFinish.value!! < _timestampStart.value!!) {
            equateStartToFinish ()
        }
    }

    fun onAddNoteClicked(name: String, description: String) {
        _noteName.value = name
        _description.value = description
    }

}





