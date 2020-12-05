package com.marand.cleanarchitecture.presentation

import android.app.AlertDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.marand.cleanarchitecture.R
import com.marand.cleanarchitecture.framework.NoteViewModel
import com.marand.core.data.Note
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : Fragment() {
    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note("", "", 0L, 0L)
    private var noteId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        if (noteId != 0L) {
            viewModel.getNote(noteId)
        }

        check_button.setOnClickListener {
            if (!title_view.text.toString().isEmpty() || !content_view.text.toString().isEmpty()) {
                val time = System.currentTimeMillis()
                currentNote.title = title_view.text.toString()
                currentNote.content = content_view.text.toString()
                currentNote.updateDate = time
                if (currentNote.id == 0L) {
                    currentNote.creationData = time
                }
                viewModel.saveNote(currentNote)
            } else{
                Navigation.findNavController(it).popBackStack()
            }
        }

        observeViewModel()
    }

// -------------------------------------------------------------------------------------------------

    private fun observeViewModel() {
        viewModel.saved.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(context, getString(R.string.note_saved), Toast.LENGTH_SHORT).show()
                hideKeyboard()
                Navigation.findNavController(title_view).popBackStack()
            } else{
                Toast.makeText(context, getString(R.string.error_note_saved), Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.currentNote.observe(viewLifecycleOwner, Observer {note ->
            note?.let {
                currentNote = it
                title_view.setText(it.title, TextView.BufferType.EDITABLE)
                content_view.setText(it.content, TextView.BufferType.EDITABLE)
            }
        })
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(title_view.windowToken, 0)
    }

// -------------------------------------------------------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.delete_note -> {
                if (context != null && noteId != 0L) {
                    AlertDialog.Builder(context)
                            .setTitle(getString(R.string.delete_note))
                            .setMessage(getString(R.string.delete_message))
                            .setPositiveButton(getString(R.string.yes)) {dialogInterface, i ->
                                viewModel.deleteNote(currentNote)
                            }
                            .setNegativeButton(getString(R.string.no)) {dialogInterface, i -> }
                            .create()
                            .show()
                }
            }
        }
        return true
    }
}