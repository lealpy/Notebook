package com.lealpy.notebook.ui.new_note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lealpy.notebook.data.models.Note
import io.realm.Realm
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NewNoteViewModel : ViewModel() {

    private val _nameLD = MutableLiveData<String>("")
    val nameLD: LiveData<String> = _nameLD

    private var realm = Realm.getDefaultInstance()

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

    fun addNoteToDB(dateStart : Long, dateFinish : Long, name : String, description : String) {
        try {

            realm.beginTransaction()

            val currentIdNumber: Number? = realm.where(Note::class.java).max("id")
            val id : Long? = if (currentIdNumber == null) {
                1
            } else {
                currentIdNumber.toLong() + 1
            }

            val note = Note(id, dateStart, dateFinish, name, description)

            realm.copyToRealmOrUpdate(note)
            realm.commitTransaction()

            Log.d("MyLog", "Элемент добавлен в БД")

        } catch (e : Exception) {
            Log.d("MyLog", "Ошибка при добавлении элемента в БД: $e")
        }
    }

}