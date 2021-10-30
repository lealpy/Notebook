package com.lealpy.notebook.ui.new_note

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lealpy.notebook.R
import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.databinding.FragmentNewNoteBinding
import com.lealpy.notebook.ui.notes.NotesFragment
import io.realm.Realm
import java.lang.Exception
import java.text.SimpleDateFormat
import kotlin.math.min

class NewNoteFragment : Fragment() {

    private lateinit var viewModel: NewNoteViewModel
    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(this).get(NewNoteViewModel::class.java)

        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initViews()

        return root
    }

    private fun initViews() {
        var yearStart = viewModel.currentYear
        var monthStart = viewModel.currentMonth - 1 // В календаре индекс января = 0
        var dayStart = viewModel.currentDay
        var hourStart = viewModel.currentHour
        var minuteStart = viewModel.currentMinute

        var yearFinish = yearStart
        var monthFinish = monthStart
        var dayFinish = dayStart
        var hourFinish = hourStart + 1
        var minuteFinish = minuteStart

        binding.dateStart.text = viewModel.getDateString(yearStart, monthStart, dayStart)
        binding.timeStart.text = viewModel.getTimeString(hourStart, minuteStart)

        binding.dateFinish.text = viewModel.getDateString(yearFinish, monthFinish, dayFinish)
        binding.timeFinish.text = viewModel.getTimeString(hourFinish, minuteFinish)

//-------------------------Date and time pickers-----------------------------------------(start)

        binding.dateStart.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener{ _, year, month, day ->
                    val dateStart = viewModel.getTimestamp(year, month, day, 0, 0)
                    val dateFinish = viewModel.getTimestamp(yearFinish, monthFinish, dayFinish, 0, 0)

                    if(dateFinish < dateStart) {
                        yearFinish = year
                        monthFinish = month
                        dayFinish = day
                        binding.dateFinish.text = viewModel.getDateString(yearFinish, monthFinish, dayFinish)
                    }

                    yearStart = year
                    monthStart = month
                    dayStart = day

                    binding.dateStart.text = viewModel.getDateString(yearStart, monthStart, dayStart)
                },
                yearStart,
                monthStart,
                dayStart)
                .show()
        }

        binding.dateFinish.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener{ _, year, month, day ->

                    val dateStart = viewModel.getTimestamp(yearStart, monthStart, dayStart, 0, 0)
                    val dateFinish = viewModel.getTimestamp(year, month, day, 0, 0)

                    if(dateFinish < dateStart) {
                        yearStart = year
                        monthStart = month
                        dayStart = day
                        binding.dateStart.text = viewModel.getDateString(yearStart, monthStart, dayStart)
                    }

                    yearFinish = year
                    monthFinish = month
                    dayFinish = day
                    binding.dateFinish.text = viewModel.getDateString(yearFinish, monthFinish, dayFinish)
                },
                yearFinish,
                monthFinish,
                dayFinish)
                .show()
        }

        binding.timeStart.setOnClickListener {
            TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener{ _, hour, minute ->

                    val dateStart = viewModel.getTimestamp(1970, 0, 1, hour, minute)
                    val dateFinish = viewModel.getTimestamp(1970, 0, 1, hourFinish, minuteFinish)

                    if(dateFinish < dateStart) {
                        hourFinish = hour
                        minuteFinish = minute
                        binding.timeFinish.text = viewModel.getTimeString(hourFinish, minuteFinish)
                    }

                    hourStart = hour
                    minuteStart = minute
                    binding.timeStart.text = viewModel.getTimeString(hourStart, minuteStart)

                },
                hourStart,
                minuteStart,
                true)
                .show()
        }

        binding.timeFinish.setOnClickListener {
            TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener{ _, hour, minute ->

                    val dateStart = viewModel.getTimestamp(1970, 0, 1, hourStart, minuteStart)
                    val dateFinish = viewModel.getTimestamp(1970, 0, 1, hour, minute)

                    if(dateFinish < dateStart) {
                        hourStart = hour
                        minuteStart = minute
                        binding.timeStart.text = viewModel.getTimeString(hourStart, minuteStart)
                    }

                    hourFinish = hour
                    minuteFinish = minute
                    binding.timeFinish.text = viewModel.getTimeString(hourFinish, minuteFinish)

                },
                hourFinish,
                minuteFinish,
                true)
                .show()
        }

//-------------------------Date and time pickers-----------------------------------------(finish)


        binding.addTaskButton.setOnClickListener {
            val dateStart = viewModel.getTimestamp(yearStart, monthStart, dayStart, hourStart, minuteStart)
            val dateFinish = viewModel.getTimestamp(yearFinish, monthFinish, dayFinish, hourFinish, minuteFinish)
            val name = binding.noteName.text.toString()
            val description = binding.noteDescription.text.toString()

            viewModel.addNoteToDB(dateStart, dateFinish, name, description)
            startNotesFragment()
        }

    }

    private fun startNotesFragment() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, NotesFragment.newInstance())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}