package com.lealpy.notebook.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lealpy.notebook.R
import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.databinding.FragmentNotesBinding
import com.lealpy.notebook.ui.new_note.NewNoteFragment
import com.lealpy.notebook.ui.note_description.NoteDescriptionFragment
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import java.util.*

class NotesFragment : Fragment() {

    private lateinit var viewModel: NotesViewModel
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val notesAdapter = NotesAdapter(
        object : NotesAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main,
                        NoteDescriptionFragment.newInstance(note.id ?: -1))
                    .commit()
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        arguments?.getLong(NOTES_CODE)?.let { date ->
            viewModel.onGotDate(date)
        }

        viewModel.setNotes()

        initObservers()
        initViews()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        viewModel.notesLD.observe(viewLifecycleOwner) { notes ->
            notesAdapter.submitList(notes)
        }

        viewModel.dateString.observe(viewLifecycleOwner) { dateString ->
            binding.date.text = dateString
        }

        viewModel.startNewNote.observe(viewLifecycleOwner) { date ->
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main,
                    NewNoteFragment.newInstance(date ?: 0))
                .commit()
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

        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                viewModel.onDateSelected(date)
            }
        }
    }

    companion object {
        private const val NOTES_CODE = "NOTES_CODE"

        @JvmStatic
        fun newInstance(date: Long): NotesFragment {
            return NotesFragment().apply {
                arguments = Bundle().apply {
                    putLong(NOTES_CODE, date)
                }
            }
        }
    }
}