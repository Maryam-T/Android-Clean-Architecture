package com.marand.cleanarchitecture.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marand.cleanarchitecture.R
import com.marand.core.data.Note
import kotlinx.android.synthetic.main.list_row_note.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotesListAdapter(var notes: ArrayList<Note>, val action: ListAction): RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_row_note, parent, false)
    )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size

    inner class NoteViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val noteTitle = view.title
        private val noteContent = view.content
        private val noteDate = view.date
        private val noteWordCount = view.word_count

        fun bind(note: Note) {
            noteTitle.text = note.title
            noteContent.text = note.content
            noteWordCount.text = "Words: ${note.wordCount}"

            val sdf = SimpleDateFormat("MMM dd, HH:mm:ss")
            val resultDate = Date(note.updateDate)
            noteDate.text = "Last updated: ${sdf.format(resultDate)}"

            itemView.setOnClickListener {
                action.onClick(note.id)
            }
        }
    }

// -------------------------------------------------------------------------------------------------

    fun updateNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }
}