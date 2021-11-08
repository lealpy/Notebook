package com.lealpy.notebook.ui.new_note

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lealpy.notebook.R
import com.lealpy.notebook.databinding.FragmentNewNoteBinding
import com.lealpy.notebook.ui.notes.NotesFragment

class NewNoteFragment: Fragment() {

    private lateinit var viewModel: NewNoteViewModel

    private lateinit var binding: FragmentNewNoteBinding

    private val onStartDateSetClickListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        viewModel.onDateStartPicked(year, month, dayOfMonth)
    }
    private val onFinishDateSetClickListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        viewModel.onDateFinishPicked(year, month, dayOfMonth)
    }
    private val onStartTimeSetClickListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        viewModel.onTimeStartPicked(hour, minute)
    }
    private val onFinishTimeSetClickListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        viewModel.onTimeFinishPicked(hour, minute)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewNoteBinding.inflate(inflater)

        viewModel = ViewModelProvider(this)[NewNoteViewModel::class.java]

        arguments?.getLong(NEW_NOTE_CODE)?.let { date ->
            viewModel.onGotDate(date)
        }

        initObservers()
        initViews()

        return binding.root
    }

    private fun initObservers() {
        viewModel.dateStringStart.observe(viewLifecycleOwner) {
            binding.dateStart.text = it
        }

        viewModel.timeStringStart.observe(viewLifecycleOwner) {
            binding.timeStart.text = it
        }

        viewModel.dateStringFinish.observe(viewLifecycleOwner) {
            binding.dateFinish.text = it
        }

        viewModel.timeStringFinish.observe(viewLifecycleOwner) {
            binding.timeFinish.text = it
        }
        
        viewModel.dateStartPickerData.observe(viewLifecycleOwner) { datePickerData ->
            if(datePickerData != null) {
                DatePickerDialog(
                    requireContext(),
                    onStartDateSetClickListener,
                    datePickerData.year,
                    datePickerData.month,
                    datePickerData.day
                ).show()
            }
        }

        viewModel.dateFinishPickerData.observe(viewLifecycleOwner) { datePickerData ->
            if(datePickerData != null) {
                DatePickerDialog(
                    requireContext(),
                    onFinishDateSetClickListener,
                    datePickerData.year,
                    datePickerData.month,
                    datePickerData.day
                ).show()
            }
        }

        viewModel.timeStartPickerData.observe(viewLifecycleOwner) { timePickerData ->
            if(timePickerData != null) {
                TimePickerDialog(
                    requireContext(),
                    onStartTimeSetClickListener,
                    timePickerData.hour,
                    timePickerData.minute,
                    true
                ).show()
            }
        }

        viewModel.timeFinishPickerData.observe(viewLifecycleOwner) { timePickerData ->
            if(timePickerData != null) {
                TimePickerDialog(
                    requireContext(),
                    onFinishTimeSetClickListener,
                    timePickerData.hour,
                    timePickerData.minute,
                    true
                ).show()
            }
        }

        viewModel.startNotesFragment.observe(viewLifecycleOwner) { date ->
            startNotesFragment(date)
        }
    }

    private fun initViews() {
        binding.dateStart.setOnClickListener {
            viewModel.onDateStartPickerClicked()
        }

        binding.dateFinish.setOnClickListener {
            viewModel.onDateFinishPickerClicked()
        }

        binding.timeStart.setOnClickListener {
            viewModel.onTimeStartPickerClicked()
        }

        binding.timeFinish.setOnClickListener {
            viewModel.onTimeFinishPickerClicked()
        }

        binding.addNoteButton.setOnClickListener {
            val name = binding.noteName.text.toString()
            val description = binding.noteDescription.text.toString()
            viewModel.onAddNoteClicked(name, description)
        }
    }

    private fun startNotesFragment(date: Long) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main,
                NotesFragment.newInstance(date))
            .commit()
    }

    companion object {
        private const val NEW_NOTE_CODE = "NEW_NOTE_CODE"

        @JvmStatic
        fun newInstance(date: Long): NewNoteFragment {
            return NewNoteFragment().apply {
                arguments = Bundle().apply {
                    putLong(NEW_NOTE_CODE, date)
                }
            }
        }
    }
}