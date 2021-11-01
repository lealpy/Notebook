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

class NotesFragment : Fragment() {

    private lateinit var viewModel: NotesViewModel
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val notesAdapter = NotesAdapter(
        object : NotesAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                parentFragmentManager
                    .beginTransaction()
                    .add(R.id.nav_host_fragment_activity_main,
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

        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        arguments?.getLong(NOTES_ID)?.let { date ->
            viewModel.onGotDate(date)
        }

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
    }

    companion object {
        private const val NOTES_ID = "NOTES_ID"

        @JvmStatic
        fun newInstance(date: Long): NotesFragment {
            return NotesFragment().apply {
                arguments = Bundle().apply {
                    putLong(NOTES_ID, date)
                }
            }
        }
    }
}