package com.lealpy.notebook.ui.note_description

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.lealpy.notebook.R
import com.lealpy.notebook.databinding.FragmentNoteDescriptionBinding
import com.lealpy.notebook.ui.notes.NotesFragment

class NoteDescriptionFragment: Fragment() {

    private lateinit var viewModel: NoteDescriptionViewModel

    private lateinit var binding: FragmentNoteDescriptionBinding

    private val onStartDateSetClickListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.onDateStartPicked(year, month, dayOfMonth)
        }
    private val onFinishDateSetClickListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.onDateFinishPicked(year, month, dayOfMonth)
        }
    private val onStartTimeSetClickListener =
        TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            viewModel.onTimeStartPicked(hour, minute)
        }
    private val onFinishTimeSetClickListener =
        TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            viewModel.onTimeFinishPicked(hour, minute)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[NoteDescriptionViewModel::class.java]

        binding = FragmentNoteDescriptionBinding.inflate(inflater)

        arguments?.getLong(NOTE_DESCRIPTION_CODE)?.let { id ->
            viewModel.onGotId(id)
        }

        initObservers()
        initViews()

        return binding.root
    }

    private fun initObservers() {
        viewModel.noteName.observe(viewLifecycleOwner) {
            binding.noteName.setText(it)
        }

        viewModel.noteDescription.observe(viewLifecycleOwner) {
            binding.noteDescription.setText(it)
        }

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

        binding.changeNoteButton.setOnClickListener {
            val name = binding.noteName.text.toString()
            val description = binding.noteDescription.text.toString()
            viewModel.onChangeNoteClicked(name, description)
        }

        binding.deleteNoteButton.setOnClickListener {
            viewModel.onDeleteNoteClicked()
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
        private const val NOTE_DESCRIPTION_CODE = "NOTE_DESCRIPTION_CODE"

        @JvmStatic
        fun newInstance(id: Long): NoteDescriptionFragment {
            return NoteDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putLong(NOTE_DESCRIPTION_CODE, id)
                }
            }
        }
    }
}