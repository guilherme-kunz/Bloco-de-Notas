package com.guilhermekunz.blocodenotas.presentation.notes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilhermekunz.blocodenotas.domain.repository.NoteRepository
import com.guilhermekunz.blocodenotas.domain.use_case.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val repository: NoteRepository
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var getNotesJob: Job? = null

    init {
        getNotes()
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    repository.deleteNote(event.note)
                }
            }
            is NotesEvent.RestoreNote -> {
                // Lógica opcional de desfazer
            }
            is NotesEvent.ToggleOrderSection -> {
                // Lógica de UI
            }
        }
    }

    private fun getNotes() {
        getNotesJob?.cancel()
        getNotesJob = getNotesUseCase()
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes
                )
            }
            .launchIn(viewModelScope)
    }
}