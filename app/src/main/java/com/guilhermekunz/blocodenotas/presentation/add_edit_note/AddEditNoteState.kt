package com.guilhermekunz.blocodenotas.presentation.add_edit_note

import com.guilhermekunz.blocodenotas.domain.model.ChecklistItem

data class AddEditNoteState(
    val title: String = "",
    val content: String = "",
    val checklistItems: List<ChecklistItem> = emptyList(),
    val noteType: String = "TEXT",
    val isSaving: Boolean = false
)

sealed class AddEditNoteEvent {
    data class EnteredTitle(val value: String): AddEditNoteEvent()
    data class EnteredContent(val value: String): AddEditNoteEvent()
    data class ChangeNoteType(val type: String): AddEditNoteEvent()
    object AddChecklistItem : AddEditNoteEvent()
    data class ToggleChecklistItem(val index: Int) : AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}