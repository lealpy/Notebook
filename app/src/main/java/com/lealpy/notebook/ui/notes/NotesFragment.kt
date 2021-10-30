package com.lealpy.notebook.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lealpy.notebook.R
import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.databinding.FragmentNotesBinding
import com.lealpy.notebook.ui.note_description.NoteDescriptionFragment
import io.realm.Realm
import io.realm.RealmResults
import java.lang.Exception
import java.lang.reflect.Array.newInstance

class NotesFragment : Fragment() {

    private lateinit var notesViewModel: NotesViewModel
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    // private lateinit var realm : Realm
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
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initObservers()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = notesAdapter

        binding.date.text =
            "${notesViewModel.currentDay}.${notesViewModel.currentMonth}.${notesViewModel.currentYear}"

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        notesViewModel.notesLD.observe(viewLifecycleOwner) { notes ->
            notesAdapter.submitList(notes)
        }
    }

    companion object {
        private const val NOTES_ID = "NOTES_ID"

        @JvmStatic
        fun newInstance() = NotesFragment()
    }
}