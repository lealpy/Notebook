package com.lealpy.notebook.ui.note_description

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lealpy.notebook.R
import com.lealpy.notebook.data.models.DatePickerData
import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.data.models.TimePickerData
import com.lealpy.notebook.data.repository.NotesRepository
import com.lealpy.notebook.utils.AppUtils

class NoteDescriptionViewModel(application: Application): AndroidViewModel(application) {

    private val notesRepository = NotesRepository()

    private var noteId: Long? = null
    private var note: Note? = null

    private var dateStart: Long = 0
    private var dateFinish: Long = 0

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

    private val _noteName = MutableLiveData<String>(null)
    val noteName: LiveData<String> = _noteName

    private val _noteDescription = MutableLiveData<String>(null)
    val noteDescription: LiveData<String> = _noteDescription

    private val _dateStringStart = MutableLiveData<String>(null)
    val dateStringStart: LiveData<String> = _dateStringStart

    private val _dateStringFinish = MutableLiveData<String>(null)
    val dateStringFinish: LiveData<String> = _dateStringFinish

    private val _timeStringStart = MutableLiveData<String>(null)
    val timeStringStart: LiveData<String> = _timeStringStart

    private val _timeStringFinish = MutableLiveData<String>(null)
    val timeStringFinish: LiveData<String> = _timeStringFinish

    private val _dateStartPickerData = MutableLiveData<DatePickerData?>(null)
    val dateStartPickerData: LiveData<DatePickerData?> = _dateStartPickerData

    private val _dateFinishPickerData = MutableLiveData<DatePickerData?>(null)
    val dateFinishPickerData: LiveData<DatePickerData?> = _dateFinishPickerData

    private val _timeStartPickerData = MutableLiveData<TimePickerData?>(null)
    val timeStartPickerData: LiveData<TimePickerData?> = _timeStartPickerData

    private val _timeFinishPickerData = MutableLiveData<TimePickerData?>(null)
    val timeFinishPickerData: LiveData<TimePickerData?> = _timeFinishPickerData

    private val _startNotesFragment = MutableLiveData<Long>()
    val startNotesFragment: LiveData <Long> = _startNotesFragment

    fun onGotId(id: Long) {
        noteId = id
        note = notesRepository.getNoteFromDB(noteId)

        dateStart = note?.dateStart ?: 0
        dateFinish = note?.dateFinish ?: 0

        yearStart = AppUtils.getYearIntByTimestamp(dateStart)
        monthStart = AppUtils.getMonthIntByTimestamp(dateStart)
        dayStart = AppUtils.getDayIntByTimestamp(dateStart)
        hourStart = AppUtils.getHourIntByTimestamp(dateStart)
        minuteStart = AppUtils.getMinuteIntByTimestamp(dateStart)
        yearFinish = AppUtils.getYearIntByTimestamp(dateFinish)
        monthFinish = AppUtils.getMonthIntByTimestamp(dateFinish)
        dayFinish = AppUtils.getDayIntByTimestamp(dateFinish)
        hourFinish = AppUtils.getHourIntByTimestamp(dateFinish)
        minuteFinish = AppUtils.getMinuteIntByTimestamp(dateFinish)

        _noteName.value = note?.name ?: ""
        _noteDescription.value = note?.description ?: ""
        _dateStringStart.value = AppUtils.getDateStringByInt(yearStart, monthStart, dayStart)
        _dateStringFinish.value = AppUtils.getDateStringByInt(yearFinish, monthFinish, dayFinish)
        _timeStringStart.value = AppUtils.getTimeStringByInt(hourStart, minuteStart)
        _timeStringFinish.value = AppUtils.getTimeStringByInt(hourFinish, minuteFinish)
    }

    private fun refreshDateLD() {
        _dateStringStart.value = AppUtils.getDateStringByInt(yearStart, monthStart, dayStart)
        _dateStringFinish.value = AppUtils.getDateStringByInt(yearFinish, monthFinish, dayFinish)
    }

    private fun refreshTimeLD() {
        _timeStringStart.value = AppUtils.getTimeStringByInt(hourStart, minuteStart)
        _timeStringFinish.value = AppUtils.getTimeStringByInt(hourFinish, minuteFinish)
    }

    fun onDateStartPickerClicked() {
        val datePickerData = DatePickerData(yearStart, monthStart, dayStart)
        _dateStartPickerData.value = datePickerData
    }

    fun onDateFinishPickerClicked() {
        val datePickerData = DatePickerData(yearFinish, monthFinish, dayFinish)
        _dateFinishPickerData.value = datePickerData
    }

    fun onTimeStartPickerClicked() {
        val timePickerData = TimePickerData(hourStart, minuteStart)
        _timeStartPickerData.value = timePickerData
    }

    fun onTimeFinishPickerClicked() {
        val timePickerData = TimePickerData(hourFinish, minuteFinish)
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
        val timestampStart = AppUtils.getTimestampByInt(yearStart, monthStart, dayStart, hourStart, minuteStart)
        val timestampFinish = AppUtils.getTimestampByInt(yearFinish, monthFinish, dayFinish, hourFinish, minuteFinish)
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
        val timestampStart = AppUtils.getTimestampByInt(yearStart, monthStart, dayStart, hourStart, minuteStart)
        val timestampFinish = AppUtils.getTimestampByInt(yearFinish, monthFinish, dayFinish, hourFinish, minuteFinish)
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
        if (noteName.value != "") {
            changeNoteInDB()
            _startNotesFragment.value =
                AppUtils.getTimestampByInt(yearStart, monthStart, dayStart, hourStart, minuteStart)

            val toastText = getApplication<Application>().resources.getString(R.string.note_changed)
            Toast.makeText(getApplication(), toastText, Toast.LENGTH_SHORT).show()
        }
        else {
            val toastText = getApplication<Application>().resources.getString(R.string.enter_note_name)
            Toast.makeText(getApplication(), toastText, Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeNoteInDB() {
        val note = Note(
            noteId,
            AppUtils.getTimestampByInt(yearStart, monthStart, dayStart, hourStart, minuteStart),
            AppUtils.getTimestampByInt(yearFinish, monthFinish, dayFinish, hourFinish, minuteFinish),
            _noteName.value,
            _noteDescription.value)
        notesRepository.changeNoteInDB(note)
    }

    fun onDeleteNoteClicked() {
        notesRepository.deleteNoteFromDB(note?.id)
        _startNotesFragment.value =
            AppUtils.getTimestampByInt(yearStart, monthStart, dayStart, 0, 0)
        val toastText = getApplication<Application>().resources.getString(R.string.note_deleted)
        Toast.makeText(getApplication(), toastText, Toast.LENGTH_SHORT).show()
    }
}