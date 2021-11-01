package com.lealpy.notebook.ui.calendar

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.lealpy.notebook.R
import com.lealpy.notebook.databinding.FragmentCalendarBinding
import com.lealpy.notebook.ui.note_description.NoteDescriptionFragment
import com.lealpy.notebook.ui.notes.NotesFragment
import java.sql.Timestamp
import java.util.*


class CalendarFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: CalendarViewModel

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initObservers()
        initViews()

        return root
    }

    private fun initObservers() {
        viewModel.events.observe(viewLifecycleOwner) {events ->
            binding.calendarView.setEvents(events)
        }
    }

    private fun initViews() {
        binding.calendarView.setOnDayClickListener {eventDay ->
            val date = eventDay.calendar.time.time
            Log.d("MyLog", "$date")
            parentFragmentManager
                .beginTransaction()
                .add(R.id.nav_host_fragment_activity_main,
                    NotesFragment.newInstance(date ?: 0))
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    }

/*
    //Удалить?
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireActivity(),this, year, month, day)
    }
    */
}




