package com.lealpy.notebook.ui.calendar

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.lealpy.notebook.R
import com.lealpy.notebook.databinding.FragmentCalendarBinding
import com.lealpy.notebook.ui.new_note.NewNoteFragment
import com.lealpy.notebook.ui.notes.NotesFragment
import java.util.*

class CalendarFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: CalendarViewModel
    private lateinit var binding: FragmentCalendarBinding
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        viewModel = ViewModelProvider(this)[CalendarViewModel::class.java]

        binding = FragmentCalendarBinding.inflate(inflater)

        initObservers()
        initViews()

        return binding.root
    }

    private fun initObservers() {
        viewModel.events.observe(viewLifecycleOwner) {events ->
            binding.calendarView.setEvents(events)
        }

        viewModel.startNewNote.observe(viewLifecycleOwner) {date ->
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main,
                    NewNoteFragment.newInstance(date ?: 0))
                .commit()
        }
    }

    private fun initViews() {
        binding.calendarView.setOnDayClickListener {eventDay ->
            val date = eventDay.calendar.time.time
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main,
                    NotesFragment.newInstance(date))
                .commit()
        }

        binding.addNoteBtn.setOnClickListener {
            viewModel.onAddNoteBtnClicked()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    }
}