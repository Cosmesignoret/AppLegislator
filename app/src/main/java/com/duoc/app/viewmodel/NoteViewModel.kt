package com.duoc.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.app.data.model.NoteEntity
import com.duoc.app.data.remote.client.RetrofitClient
import com.duoc.app.data.repository.AppDatabase
import com.duoc.app.data.repository.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    val notesState: StateFlow<List<NoteEntity>>

    init {
        val dao = AppDatabase.getDatabase(application).noteDao()
        val api = RetrofitClient(application).noteApi
        repository = NoteRepository(dao, api)
        notesState = repository.allNotes.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun addNote(title: String, content: String, reference: String, date: String, imageBase64: String?) {
        viewModelScope.launch {
            val note = NoteEntity(
                title = title,
                content = content,
                legalReference = reference,
                dateText = date,
                imageBase64 = imageBase64
            )
            repository.insert(note)
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun syncNotes() {
        viewModelScope.launch {
            repository.syncWithServer()
        }
    }
}