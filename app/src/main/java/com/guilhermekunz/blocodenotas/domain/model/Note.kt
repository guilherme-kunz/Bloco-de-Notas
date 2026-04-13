package com.guilhermekunz.blocodenotas.domain.model

sealed class Note(
    open val id: Int = 0,
    open val title: String,
    open val timestamp: Long
) {
    data class TextNote(
        override val id: Int = 0,
        override val title: String,
        val content: String,
        override val timestamp: Long = System.currentTimeMillis()
    ) : Note(id, title, timestamp)

    data class ChecklistNote(
        override val id: Int = 0,
        override val title: String,
        val items: List<ChecklistItem>,
        override val timestamp: Long = System.currentTimeMillis()
    ) : Note(id, title, timestamp)
}

data class ChecklistItem(
    val text: String,
    val isChecked: Boolean = false
)