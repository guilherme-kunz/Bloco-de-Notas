package com.guilhermekunz.blocodenotas.presentation.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.guilhermekunz.blocodenotas.presentation.add_edit_note.AddEditNoteEvent
import com.guilhermekunz.blocodenotas.presentation.add_edit_note.AddEditNoteViewModel
import com.guilhermekunz.blocodenotas.presentation.notes.components.NoteItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    onAddNoteClick: (type: String) -> Unit,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddNoteClick("TEXT") }, // Simplificado: abre criação de texto
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Nota")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.notes) { note ->
                NoteItem(
                    note = note,
                    onDeleteClick = {
                        viewModel.onEvent(NotesEvent.DeleteNote(note))
                    }
                )
            }
        }
    }
}

@Composable
fun AddEditNoteScreen(
    onSaveSuccess: () -> Unit,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = state.title,
            onValueChange = { viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it)) },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Seletor de Tipo (Exemplo simples com Chips ou Tab)
        Row {
            FilterChip(
                selected = state.noteType == "TEXT",
                onClick = { viewModel.onEvent(AddEditNoteEvent.ChangeNoteType("TEXT")) },
                label = { Text("Texto") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterChip(
                selected = state.noteType == "CHECKLIST",
                onClick = { viewModel.onEvent(AddEditNoteEvent.ChangeNoteType("CHECKLIST")) },
                label = { Text("Checklist") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.noteType == "TEXT") {
            OutlinedTextField(
                value = state.content,
                onValueChange = { viewModel.onEvent(AddEditNoteEvent.EnteredContent(it)) },
                label = { Text("Conteúdo da nota") },
                modifier = Modifier.fillWeight(1f).fillMaxWidth()
            )
        } else {
            // Aqui entraria um LazyColumn para os itens do Checklist
            Text("Lógica de lista de tarefas aqui...")
        }

        Button(
            onClick = {
                viewModel.onEvent(AddEditNoteEvent.SaveNote)
                onSaveSuccess()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Salvar Nota")
        }
    }
}