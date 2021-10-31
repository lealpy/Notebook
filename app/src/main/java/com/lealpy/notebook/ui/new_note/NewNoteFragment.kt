package com.lealpy.notebook.ui.new_note

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lealpy.notebook.R
import com.lealpy.notebook.databinding.FragmentNewNoteBinding
import com.lealpy.notebook.ui.notes.NotesFragment

class NewNoteFragment : Fragment() {

    private lateinit var viewModel: NewNoteViewModel
    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(this).get(NewNoteViewModel::class.java)

        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initObservers()
        initViews()

        return root
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
    }

    private fun initViews() {

//<-------------------------Date and time pickers-----------------------------------------...

        binding.dateStart.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    viewModel.onDateStartClicked(year, month, day)
                },
                viewModel.yearStart.value!!, /////////////////////////////////////////////////////////////// null-safety !!  shot in my leg
                viewModel.monthStart.value!!,
                viewModel.dayStart.value!!)
                .show()
        }

        binding.dateFinish.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    viewModel.onDateFinishClicked(year, month, day)
                },
                viewModel.yearFinish.value!!,
                viewModel.monthFinish.value!!,
                viewModel.dayFinish.value!!)
                .show()
        }

        binding.timeStart.setOnClickListener {
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    viewModel.onTimeStartClicked(hour, minute)
                },
                viewModel.hourStart.value!!,
                viewModel.minuteStart.value!!,
                true)
                .show()
        }

        binding.timeFinish.setOnClickListener {
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    viewModel.onTimeFinishClicked(hour, minute)
                },
                viewModel.hourFinish.value!!,
                viewModel.minuteFinish.value!!,
                true)
                .show()
        }

//...-------------------------Date and time pickers----------------------------------------->

        binding.addNoteButton.setOnClickListener {
            val name = binding.noteName.text.toString()
            val description = binding.noteDescription.text.toString()
            viewModel.onAddNoteClicked(name, description)

            if (!viewModel.noteName.value.equals("")) {
                viewModel.addNoteToDB()
                startNotesFragment()
            }
            else Toast.makeText(activity, "Введите название события", Toast.LENGTH_SHORT).show() ////////////////////////// Не работает тост
        }

    }

    private fun startNotesFragment() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, NotesFragment.newInstance())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}