package com.lealpy.notebook.ui.notes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.databinding.NoteItemBinding
import java.text.SimpleDateFormat
import java.util.*


class NotesAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<Note, NotesAdapter.NoteHolder>(DiffCallback()) {

    inner class NoteHolder(
        private val binding : NoteItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                binding.root.setOnClickListener {
                    val position = layoutPosition
                    if (position != RecyclerView.NO_POSITION) {
                        var note = getItem(position)
                        onItemClickListener.onItemClick(note)
                    }
                }
            }

        }

        fun bind (note : Note) {
            binding.noteName.text = "Событие : ${note.name}"
            binding.noteDescription.text = "Описание: ${note.description}"
            binding.noteDateStart.text = "Дата начала: ${getDateStringByTimestamp(note.dateStart)}"
            binding.noteTimeStart.text = "Время начала: ${getTimeStringByTimestamp(note.dateStart)}"
            binding.noteDateFinish.text = "Дата окончания: ${getDateStringByTimestamp(note.dateFinish)}"
            binding.noteTimeFinish.text = "Время окончания: ${getTimeStringByTimestamp(note.dateFinish)}"
            binding.noteID.text = "id: ${note.id}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = NoteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem
    }

    fun getNoteItem(position: Int): Note {
        return getItem(position)
    }

    fun getTimeStringByTimestamp(timestamp : Long?) : String {
        return SimpleDateFormat("HH:mm").format(timestamp?.let { Date(it) })
    }

    fun getDateStringByTimestamp(timestamp : Long?) : String {
        return SimpleDateFormat("dd.MM.yyyy").format(timestamp?.let { Date(it) })
    }

}