package com.lealpy.notebook.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class NotesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    val currentYear = SimpleDateFormat("yyyy").format(Date())
    val currentMonth = SimpleDateFormat("MM").format(Date())
    val currentDay = SimpleDateFormat("dd").format(Date())
    val currentHour = SimpleDateFormat("HH").format(Date()).toInt()
    val currentMinute = SimpleDateFormat("mm").format(Date())

}