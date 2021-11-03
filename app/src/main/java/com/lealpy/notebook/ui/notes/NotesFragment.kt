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
import com.lealpy.notebook.ui.note_description.NoteDescriptionFragment
import com.vivekkaushik.datepicker.OnDateSelectedListener

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

        viewModel.setNotes() //--------------------------- Мне не нравится

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
    }

    private fun initViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = notesAdapter

        binding.datePickerTimeline.setInitialDate(2021, 10, 1)
        binding.datePickerTimeline.setOnDateSelectedListener(object : OnDateSelectedListener {
            override fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int) {
                //binding.date.text = "$day.$month.$year"
                binding.datePickerTimeline.setInitialDate(year, month, day)
            }
            override fun onDisabledDateSelected(
                year: Int,
                month: Int,
                day: Int,
                dayOfWeek: Int,
                isDisabled: Boolean,
            ) {
                // Do Something
            }
        })
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