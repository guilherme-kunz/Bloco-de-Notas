package com.guilhermekunz.blocodenotas.domain.use_case

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String?,
    val checklistJson: String?,
    val type: String,
    val timestamp: Long
)