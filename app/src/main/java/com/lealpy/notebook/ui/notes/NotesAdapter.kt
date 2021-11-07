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
import com.lealpy.notebook.utils.Const
import java.text.SimpleDateFormat
import java.util.*

class NotesAdapter(
    private val onItemClickListener: OnItemClickListener,
) : ListAdapter<Note, NotesAdapter.NoteHolder>(DiffCallback()) {

    inner class NoteHolder(
        private val binding : NoteItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

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

        fun bind (note : Note, previousNote : Note?) {
            binding.noteName.text = "${getTimeStringByTimestamp(note.dateStart)} - ${note.name}"
            binding.timeRange.text = getTimeRange(note.dateStart?.plus(getGMT()))

            val max = 200
            val min = 100
            val randomRed = Random().nextInt(max-min)+min
            val randomGreen = Random().nextInt(max-min)+min
            val randomBlue = Random().nextInt(max-min)+min
            binding.cardView.setCardBackgroundColor(Color.argb(255, randomRed, randomGreen, randomBlue))

            binding.timeRange.visibility =
                if(previousNote != null &&
                    getTimeRange(note.dateStart) == getTimeRange(previousNote.dateStart)
                ) { View.INVISIBLE }
                else { View.VISIBLE }
        }

        private fun getGMT() : Long {
            return GregorianCalendar().timeZone.rawOffset.toLong()
        }

        private fun getTimeRange(dateStart : Long?) : String {
            return if (dateStart != null) {
                when (dateStart % Const.MILLIS_IN_DAY) {
                    in  0 * Const.MILLIS_IN_HOUR until  1 * Const.MILLIS_IN_HOUR - 1 -> "00:00-01:00"
                    in  1 * Const.MILLIS_IN_HOUR until  2 * Const.MILLIS_IN_HOUR - 1 -> "01:00-02:00"
                    in  2 * Const.MILLIS_IN_HOUR until  3 * Const.MILLIS_IN_HOUR - 1 -> "02:00-03:00"
                    in  3 * Const.MILLIS_IN_HOUR until  4 * Const.MILLIS_IN_HOUR - 1 -> "03:00-04:00"
                    in  4 * Const.MILLIS_IN_HOUR until  5 * Const.MILLIS_IN_HOUR - 1 -> "04:00-05:00"
                    in  5 * Const.MILLIS_IN_HOUR until  6 * Const.MILLIS_IN_HOUR - 1 -> "05:00-06:00"
                    in  6 * Const.MILLIS_IN_HOUR until  7 * Const.MILLIS_IN_HOUR - 1 -> "06:00-07:00"
                    in  7 * Const.MILLIS_IN_HOUR until  8 * Const.MILLIS_IN_HOUR - 1 -> "07:00-08:00"
                    in  8 * Const.MILLIS_IN_HOUR until  9 * Const.MILLIS_IN_HOUR - 1 -> "08:00-09:00"
                    in  9 * Const.MILLIS_IN_HOUR until 10 * Const.MILLIS_IN_HOUR - 1 -> "09:00-10:00"
                    in 10 * Const.MILLIS_IN_HOUR until 11 * Const.MILLIS_IN_HOUR - 1 -> "10:00-11:00"
                    in 11 * Const.MILLIS_IN_HOUR until 12 * Const.MILLIS_IN_HOUR - 1 -> "11:00-12:00"
                    in 12 * Const.MILLIS_IN_HOUR until 13 * Const.MILLIS_IN_HOUR - 1 -> "12:00-13:00"
                    in 13 * Const.MILLIS_IN_HOUR until 14 * Const.MILLIS_IN_HOUR - 1 -> "13:00-14:00"
                    in 14 * Const.MILLIS_IN_HOUR until 15 * Const.MILLIS_IN_HOUR - 1 -> "14:00-15:00"
                    in 15 * Const.MILLIS_IN_HOUR until 16 * Const.MILLIS_IN_HOUR - 1 -> "15:00-16:00"
                    in 16 * Const.MILLIS_IN_HOUR until 17 * Const.MILLIS_IN_HOUR - 1 -> "16:00-17:00"
                    in 17 * Const.MILLIS_IN_HOUR until 18 * Const.MILLIS_IN_HOUR - 1 -> "17:00-18:00"
                    in 18 * Const.MILLIS_IN_HOUR until 19 * Const.MILLIS_IN_HOUR - 1 -> "18:00-19:00"
                    in 19 * Const.MILLIS_IN_HOUR until 20 * Const.MILLIS_IN_HOUR - 1 -> "19:00-20:00"
                    in 20 * Const.MILLIS_IN_HOUR until 21 * Const.MILLIS_IN_HOUR - 1 -> "20:00-21:00"
                    in 21 * Const.MILLIS_IN_HOUR until 22 * Const.MILLIS_IN_HOUR - 1 -> "21:00-22:00"
                    in 22 * Const.MILLIS_IN_HOUR until 23 * Const.MILLIS_IN_HOUR - 1 -> "22:00-23:00"
                    in 23 * Const.MILLIS_IN_HOUR until 24 * Const.MILLIS_IN_HOUR - 1 -> "23:00-24:00"
                    else -> ""
                }
            }
            else ""
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

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem
    }

    private fun getTimeStringByTimestamp(timestamp : Long?) : String {
        return if(timestamp != null) {
            SimpleDateFormat("HH:mm").format(Date(timestamp))
        }
        else ""
    }
}