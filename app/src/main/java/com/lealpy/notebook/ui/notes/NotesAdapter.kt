package com.lealpy.notebook.ui.notes

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lealpy.notebook.data.models.Note
import com.lealpy.notebook.databinding.NoteItemBinding
import com.lealpy.notebook.utils.AppUtils
import java.text.SimpleDateFormat
import java.util.*

class NotesAdapter(
    private val onItemClickListener: OnItemClickListener,
): ListAdapter<Note, NotesAdapter.NoteHolder>(DiffCallback()) {

    inner class NoteHolder(
        private val binding: NoteItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                binding.root.setOnClickListener {
                    val position = layoutPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val note = getItem(position)
                        onItemClickListener.onItemClick(note)
                    }
                }
            }
        }

        fun bind(note: Note, previousNote: Note?) {
            binding.noteName.text = "${getTimeStringByTimestamp(note.dateStart)} - ${note.name}"
            binding.timeRange.text = AppUtils.getTimeRange(note.dateStart?.plus(AppUtils.getGMT()))

            val max = 200
            val min = 100
            val randomRed = Random().nextInt(max-min)+min
            val randomGreen = Random().nextInt(max-min)+min
            val randomBlue = Random().nextInt(max-min)+min
            binding.cardView.setCardBackgroundColor(Color.argb(255, randomRed, randomGreen, randomBlue))

            binding.timeRange.visibility =
                if(previousNote != null &&
                    AppUtils.getTimeRange(note.dateStart) == AppUtils.getTimeRange(previousNote.dateStart)
                ) { View.INVISIBLE }
                else { View.VISIBLE }
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
        val previousItem = if (position > 0) {
            getItem(position - 1)
        } else null
        holder.bind(item, previousItem)
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    class DiffCallback: DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem
    }

    private fun getTimeStringByTimestamp(timestamp: Long?): String {
        return if(timestamp != null) {
            SimpleDateFormat("HH:mm").format(Date(timestamp))
        }
        else ""
    }
}