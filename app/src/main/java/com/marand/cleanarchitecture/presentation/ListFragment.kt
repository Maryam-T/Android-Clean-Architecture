package com.marand.cleanarchitecture.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.marand.cleanarchitecture.R
import com.marand.cleanarchitecture.framework.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val notesListAdapter = NotesListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notes_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesListAdapter
        }

        add_note_button.setOnClickListener {
            goToNoteFragment()
        }

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

// -------------------------------------------------------------------------------------------------

    private fun goToNoteFragment(id: Long = 0L) {
        val action: NavDirections = ListFragmentDirections.actionListFragmentToNoteFragment(id)
        Navigation.findNavController(notes_recycler_view).navigate(action)
    }

    private fun observeViewModel() {
       viewModel.notes.observe(viewLifecycleOwner, Observer {noteList ->
           loading_view.visibility = GONE
           notes_recycler_view.visibility = VISIBLE
           notesListAdapter.updateNotes(noteList.sortedByDescending { it.updateDate })
       })
    }
}