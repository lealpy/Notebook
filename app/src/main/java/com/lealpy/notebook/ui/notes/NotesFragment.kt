package com.lealpy.notebook.ui.notes

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lealpy.notebook.R
import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.databinding.FragmentNotesBinding
import com.lealpy.notebook.ui.new_note.NewNoteFragment
import com.lealpy.notebook.ui.note_description.NoteDescriptionFragment
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import java.util.*

class NotesFragment: Fragment(R.layout.fragment_notes) {

    private lateinit var viewModel: NotesViewModel

    private lateinit var binding: FragmentNotesBinding

    private val notesAdapter = NotesAdapter(
        object: NotesAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                findNavController().navigate(
                    R.id.action_navigation_notes_to_noteDescriptionFragment,
                    bundleOf(NoteDescriptionFragment.NOTE_DESCRIPTION_CODE to note.id)
                )
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotesBinding.bind(view)

        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        arguments?.getLong(NOTES_CODE)?.let { date ->
            viewModel.onGotDate(date)
        }

        viewModel.setNotes()

        initObservers()
        initViews()
    }

    private fun initObservers() {
        viewModel.notesLD.observe(viewLifecycleOwner) { notes ->
            notesAdapter.submitList(notes)
        }

        viewModel.dateString.observe(viewLifecycleOwner) { dateString ->
            binding.date.text = dateString
        }

        viewModel.startNewNote.observe(viewLifecycleOwner) { date ->
            findNavController().navigate(
                R.id.action_navigation_notes_to_newNoteFragment,
                bundleOf(NewNoteFragment.NEW_NOTE_CODE to date)
            )
        }
    }

    private fun initViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = notesAdapter

        binding.addNoteBtn.setOnClickListener {
            viewModel.onAddNoteBntClicked()
        }

        initCalendar()
    }

    private fun initCalendar() {
        val startDate: Calendar = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -1)

        val endDate: Calendar = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)

        val horizontalCalendar = HorizontalCalendar.Builder(binding.root, R.id.horizontalCalendar)
            .range(startDate, endDate)
            .datesNumberOnScreen(5)
            .build()

        horizontalCalendar.calendarListener = object: HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                viewModel.onDateSelected(date)
            }
        }
    }

    companion object {
        const val NOTES_CODE = "NOTES_CODE"
    }
}