package com.guilhermekunz.blocodenotas.presentation.add_edit_note

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilhermekunz.blocodenotas.domain.model.Note
import com.guilhermekunz.blocodenotas.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _state = mutableStateOf(AddEditNoteState())
    val state: State<AddEditNoteState> = _state

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _state.value = state.value.copy(title = event.value)
            }
            is AddEditNoteEvent.EnteredContent -> {
                _state.value = state.value.copy(content = event.value)
            }
            is AddEditNoteEvent.ChangeNoteType -> {
                _state.value = state.value.copy(noteType = event.type)
            }
            is AddEditNoteEvent.SaveNote -> {
                saveNote()
            }
            // Outros eventos de checklist seriam manipulados aqui
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            val note = if (state.value.noteType == "TEXT") {
                Note.TextNote(
                    title = state.value.title,
                    content = state.value.content
                )
            } else {
                Note.ChecklistNote(
                    title = state.value.title,
                    items = state.value.checklistItems
                )
            }
            repository.insertNote(note)
        }
    }
}