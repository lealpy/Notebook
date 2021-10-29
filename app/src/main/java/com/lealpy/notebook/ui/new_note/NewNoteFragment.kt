package com.lealpy.notebook.ui.new_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lealpy.notebook.databinding.FragmentNewNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class NewNoteFragment : Fragment() {

    private lateinit var newNoteViewModel: NewNoteViewModel
    private var _binding: FragmentNewNoteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newNoteViewModel =
            ViewModelProvider(this).get(NewNoteViewModel::class.java)

        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initViews()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        binding.dateStart.text = "${newNoteViewModel.currentDay}.${newNoteViewModel.currentMonth}.${newNoteViewModel.currentYear}"
        binding.timeStart.text = "${newNoteViewModel.currentHour}:${newNoteViewModel.currentMinute}"

        binding.dateFinish.text = "${newNoteViewModel.currentDay}.${newNoteViewModel.currentMonth}.${newNoteViewModel.currentYear}"
        binding.timeFinish.text = "${newNoteViewModel.currentHour+1}:${newNoteViewModel.currentMinute}"
    }

}