package com.marand.cleanarchitecture.presentation.main.noteslist

import android.app.Activity
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.marand.cleanarchitecture.R
import com.marand.cleanarchitecture.framework.viewmodel.NoteViewModel
import com.marand.cleanarchitecture.presentation.main.MainActivity
import com.marand.core.data.Note
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListFragmentTest {
    @Rule
    @JvmField
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    lateinit var noteViewModel: NoteViewModel
    lateinit var note: Note

    @Test
    fun testAddNoteButton_shouldBeDisplayed() {
        onView(withId(R.id.add_note_button)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationToNoteScreen() {
        onView(withId(R.id.add_note_button)).perform(click())

        //Truth has been used here.
        assertThat(navController().currentDestination?.id).isEqualTo(R.id.noteFragment)
    }

    @Test
    fun testCheckButton_shouldBeDisplayed() {
        onView(withId(R.id.add_note_button)).perform(click())

        onView(withId(R.id.check_button)).check(matches(isDisplayed()))
    }

    @Test
    fun testAddNewNote() {
        noteViewModel = NoteViewModel(ApplicationProvider.getApplicationContext())

        onView(withId(R.id.add_note_button)).perform(click())

        onView(withId(R.id.title_view)).perform(typeText(NOTE_TITLE))
        onView(withId(R.id.content_view)).perform(typeText(NOTE_CONTENT))

        createNote()

        assertNotNull(note)

        onView(withId(R.id.check_button)).perform(click())

        noteViewModel.saveNote(note)

        isToastMessageDisplayed(R.string.note_saved)
    }

    private fun navController() = Navigation.findNavController(getActivity()!!, R.id.fragment)

    private fun getActivity(): Activity? {
        var activity: Activity? = null
        activityScenarioRule.scenario.onActivity {
            activity = it
        }
        return activity
    }

    private fun isToastMessageDisplayed(stringId: Int) {
        onView(withText(stringId))
            .inRoot(withDecorView(not(getActivity()!!.window.decorView)))
            .check(matches(isDisplayed()))
    }

    private fun createNote() {
        val currentTime: Long = System.currentTimeMillis()
        note = Note(NOTE_TITLE, NOTE_CONTENT, currentTime, currentTime)
    }


    companion object {
        private const val NOTE_TITLE = "This is a test note title."
        private const val NOTE_CONTENT = "This is a test note content."
    }
}