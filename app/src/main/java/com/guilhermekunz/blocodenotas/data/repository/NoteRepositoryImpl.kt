package com.guilhermekunz.blocodenotas.data.repository

import com.guilhermekunz.blocodenotas.data.local.NoteDao
import com.guilhermekunz.blocodenotas.domain.model.Note
import com.guilhermekunz.blocodenotas.domain.repository.NoteRepository
import com.guilhermekunz.blocodenotas.domain.use_case.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes().map { entities ->
            entities.map { it.toNote() }
        }
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toEntity())
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)?.toNote()
    }
}

fun NoteEntity.toNote(): Note {
    return if (type == "TEXT") {
        Note.TextNote(id, title, content ?: "", timestamp)
    } else {
        Note.ChecklistNote(id, title, emptyList(), timestamp)
    }
}

fun Note.toEntity(): NoteEntity {
    return when (this) {
        is Note.TextNote -> NoteEntity(id, title, content, null, "TEXT", timestamp)
        is Note.ChecklistNote -> NoteEntity(id, title, null, null, "CHECKLIST", timestamp)
    }
}