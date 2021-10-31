package com.lealpy.notebook.ui.note_description

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.lealpy.notebook.R
import com.lealpy.notebook.databinding.FragmentNoteDescriptionBinding
import com.lealpy.notebook.ui.notes.NotesFragment

class NoteDescriptionFragment : Fragment() {
    private lateinit var viewModel: NoteDescriptionViewModel
    private var _binding: FragmentNoteDescriptionBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NoteDescriptionViewModel::class.java)

        _binding = FragmentNoteDescriptionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        arguments?.getLong(NOTE_DESCRIPTION_ID)?.let { id ->

        } ?: Log.d("MyLog", "Something went wrong")

        initViews()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {

        fillFieldsOnCreate()

        var yearStart = viewModel.yearStart
        var monthStart = viewModel.monthStart - 1 // В календаре индекс января = 0
        var dayStart = viewModel.dayStart
        var hourStart = viewModel.hourStart
        var minuteStart = viewModel.minuteStart

        var yearFinish = viewModel.yearFinish
        var monthFinish = viewModel.monthFinish -1
        var dayFinish = viewModel.dayFinish
        var hourFinish = viewModel.hourFinish 
        var minuteFinish = viewModel.minuteFinish

        //-------------------------Date and time pickers-----------------------------------------(start)

        binding.dateStart.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener{ _, year, month, day ->
                    val dateStart = viewModel.getTimestamp(year, month, day, 0, 0)
                    val dateFinish = viewModel.getTimestamp(yearFinish, monthFinish, dayFinish, 0, 0)

                    if(dateFinish < dateStart) {
                        yearFinish = year
                        monthFinish = month
                        dayFinish = day
                        binding.dateFinish.text = viewModel.getDateString(yearFinish, monthFinish, dayFinish)
                    }

                    yearStart = year
                    monthStart = month
                    dayStart = day

                    binding.dateStart.text = viewModel.getDateString(yearStart, monthStart, dayStart)
                },
                yearStart,
                monthStart,
                dayStart)
                .show()
        }

        binding.dateFinish.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener{ _, year, month, day ->

                    val dateStart = viewModel.getTimestamp(yearStart, monthStart, dayStart, 0, 0)
                    val dateFinish = viewModel.getTimestamp(year, month, day, 0, 0)

                    if(dateFinish < dateStart) {
                        yearStart = year
                        monthStart = month
                        dayStart = day
                        binding.dateStart.text = viewModel.getDateString(yearStart, monthStart, dayStart)
                    }

                    yearFinish = year
                    monthFinish = month
                    dayFinish = day
                    binding.dateFinish.text = viewModel.getDateString(yearFinish, monthFinish, dayFinish)
                },
                yearFinish,
                monthFinish,
                dayFinish)
                .show()
        }

        binding.timeStart.setOnClickListener {
            TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener{ _, hour, minute ->

                    val dateStart = viewModel.getTimestamp(1970, 0, 1, hour, minute)
                    val dateFinish = viewModel.getTimestamp(1970, 0, 1, hourFinish, minuteFinish)

                    if(dateFinish < dateStart) {
                        hourFinish = hour
                        minuteFinish = minute
                        binding.timeFinish.text = viewModel.getTimeString(hourFinish, minuteFinish)
                    }

                    hourStart = hour
                    minuteStart = minute
                    binding.timeStart.text = viewModel.getTimeString(hourStart, minuteStart)

                },
                hourStart,
                minuteStart,
                true)
                .show()
        }

        binding.timeFinish.setOnClickListener {
            TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener{ _, hour, minute ->

                    val dateStart = viewModel.getTimestamp(1970, 0, 1, hourStart, minuteStart)
                    val dateFinish = viewModel.getTimestamp(1970, 0, 1, hour, minute)

                    if(dateFinish < dateStart) {
                        hourStart = hour
                        minuteStart = minute
                        binding.timeStart.text = viewModel.getTimeString(hourStart, minuteStart)
                    }

                    hourFinish = hour
                    minuteFinish = minute
                    binding.timeFinish.text = viewModel.getTimeString(hourFinish, minuteFinish)

                },
                hourFinish,
                minuteFinish,
                true)
                .show()
        }

//-------------------------Date and time pickers-----------------------------------------(finish)


        binding.changeTaskButton.setOnClickListener {
            val id = viewModel.note?.id
            val dateStart = viewModel.getTimestamp(yearStart, monthStart, dayStart, hourStart, minuteStart)
            val dateFinish = viewModel.getTimestamp(yearFinish, monthFinish, dayFinish, hourFinish, minuteFinish)
            val name = binding.noteName.text.toString()
            val description = binding.noteDescription.text.toString()

            if (name != "") {
                viewModel.changeNoteInDB(id, dateStart, dateFinish, name, description)
                startNotesFragment()
            }
            else Toast.makeText(context, "Введите название события", Toast.LENGTH_SHORT)
        }

        binding.deleteTaskButton.setOnClickListener {
            viewModel.deleteNoteFromDB()
            startNotesFragment()
        }
    }

    private fun fillFieldsOnCreate() {
        val dateStart = viewModel.getDateStringByTimestamp(viewModel.note?.dateStart)
        val timeStart = viewModel.getTimeStringByTimestamp(viewModel.note?.dateStart)
        val dateFinish = viewModel.getDateStringByTimestamp(viewModel.note?.dateFinish)
        val timeFinish = viewModel.getTimeStringByTimestamp(viewModel.note?.dateFinish)

        binding.dateStart.text = dateStart
        binding.timeStart.text = timeStart
        binding.dateFinish.text = dateFinish
        binding.timeFinish.text = timeFinish

        binding.noteName.setText(viewModel.note?.name)
        binding.noteDescription.setText(viewModel.note?.description)
    }

    private fun startNotesFragment() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, NotesFragment.newInstance())
            .commit()
    }

    companion object {
        private const val NOTE_DESCRIPTION_ID = "NOTE_DESCRIPTION_ID"

        @JvmStatic
        fun newInstance(id: Long): NoteDescriptionFragment {
            return NoteDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putLong(NOTE_DESCRIPTION_ID, id)
                }
            }
        }
    }

}



