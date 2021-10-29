package com.lealpy.notebook.ui.note_description

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.lealpy.notebook.databinding.FragmentNoteDescriptionBinding
import com.lealpy.notebook.ui.new_note.NewNoteViewModel

class NoteDescriptionFragment : Fragment() {
    private lateinit var newNoteViewModel: NewNoteViewModel
    private var _binding: FragmentNoteDescriptionBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newNoteViewModel =
            ViewModelProvider(this).get(NewNoteViewModel::class.java)

        _binding = FragmentNoteDescriptionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initViews()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {

        /* Присвоить похраненные в БД значения события:
        binding.dateStart.text =
        binding.timeStart.text =

        binding.dateFinish.text =
        binding.timeFinish.text =

         */
    }

}