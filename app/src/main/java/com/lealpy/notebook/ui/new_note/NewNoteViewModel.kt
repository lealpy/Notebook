package com.lealpy.notebook.ui.new_note

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
import com.lealpy.notebook.utils.Const

class NewNoteViewModel(application: Application): AndroidViewModel(application) {

    private val notesRepository = NotesRepository()

    private var yearStart = AppUtils.getCurrentYearInt()
    private var monthStart = AppUtils.getCurrentMonthInt()
    private var dayStart = AppUtils.getCurrentDayInt()
    private var hourStart = AppUtils.getCurrentHourInt()
    private var minuteStart = AppUtils.getCurrentMinuteInt()
    private var yearFinish = yearStart
    private var monthFinish = monthStart
    private var dayFinish = dayStart
    private var hourFinish = hourStart + 1
    private var minuteFinish = minuteStart

    private var noteName = ""
    private var noteDescription = ""

    private val _dateStringStart = MutableLiveData(
        AppUtils.getDateStringByInt(yearStart, monthStart, dayStart)
    )
    val dateStringStart: LiveData<String> = _dateStringStart

    private val _dateStringFinish = MutableLiveData(
        AppUtils.getDateStringByInt(yearFinish, monthFinish, dayFinish)
    )
    val dateStringFinish: LiveData<String> = _dateStringFinish

    private val _timeStringStart = MutableLiveData(
        AppUtils.getTimeStringByInt(hourStart, minuteStart)
    )
    val timeStringStart: LiveData<String> = _timeStringStart

    private val _timeStringFinish = MutableLiveData(
        AppUtils.getTimeStringByInt(hourFinish, minuteFinish)
    )
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

    fun onGotDate(date: Long) {
        yearStart = AppUtils.getYearIntByTimestamp(date)
        monthStart = AppUtils.getMonthIntByTimestamp(date)
        dayStart = AppUtils.getDayIntByTimestamp(date)
        hourStart = AppUtils.getHourIntByTimestamp(date)
        minuteStart = AppUtils.getMinuteIntByTimestamp(date)
        yearFinish = yearStart
        monthFinish = monthStart
        dayFinish = dayStart
        hourFinish = hourStart + 1
        minuteFinish = minuteStart

        _dateStringStart.value = AppUtils.getDateStringByTimeStamp(date)
        _timeStringStart.value = AppUtils.getTimeStringByTimeStamp(date)
        _dateStringFinish.value = AppUtils.getDateStringByTimeStamp(date + Const.MILLIS_IN_HOUR)
        _timeStringFinish.value = AppUtils.getTimeStringByTimeStamp(date + Const.MILLIS_IN_HOUR)
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

    fun onAddNoteClicked(name: String, description: String) {
        noteName = name
        noteDescription = description
        if (noteName != "") {
            addNoteToDB()
            _startNotesFragment.value =
                AppUtils.getTimestampByInt(yearStart, monthStart, dayStart, hourStart, minuteStart)

            val toastText = getApplication<Application>().resources.getString(R.string.note_created)
            Toast.makeText(getApplication(), toastText, Toast.LENGTH_SHORT).show()
        }
        else {
            val toastText = getApplication<Application>().resources.getString(R.string.enter_note_name)
            Toast.makeText(getApplication(), toastText, Toast.LENGTH_SHORT).show()
        }
    }

    private fun addNoteToDB() {
        val note = Note(
            notesRepository.getNewID(),
            AppUtils.getTimestampByInt(yearStart, monthStart, dayStart, hourStart, minuteStart),
            AppUtils.getTimestampByInt(yearFinish, monthFinish, dayFinish, hourFinish, minuteFinish),
            noteName,
            noteDescription)
        notesRepository.addNoteToDB(note)
    }
}