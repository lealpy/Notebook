package com.lealpy.notebook.ui.calendar

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lealpy.notebook.R
import com.lealpy.notebook.databinding.FragmentCalendarBinding
import com.lealpy.notebook.ui.new_note.NewNoteFragment
import com.lealpy.notebook.ui.notes.NotesFragment
import java.util.*

class CalendarFragment: DialogFragment(R.layout.fragment_calendar), DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: CalendarViewModel
    private lateinit var binding: FragmentCalendarBinding
    private val calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CalendarViewModel::class.java]

        binding = FragmentCalendarBinding.bind(view)

        initObservers()
        initViews()
    }

    private fun initObservers() {
        viewModel.events.observe(viewLifecycleOwner) {events ->
            binding.calendarView.setEvents(events)
        }

        viewModel.startNewNote.observe(viewLifecycleOwner) {date ->
            findNavController().navigate(
                R.id.action_navigation_calendar_to_newNoteFragment,
                bundleOf(NewNoteFragment.NEW_NOTE_CODE to date)
            )
        }
    }

    private fun initViews() {
        binding.calendarView.setOnDayClickListener {eventDay ->
            val date = eventDay.calendar.time.time

            findNavController().navigate(
                R.id.action_navigation_calendar_to_navigation_notes,
                bundleOf(NotesFragment.NOTES_CODE to date)
            )
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