package com.example.myhackeruapp10.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myhackeruapp10.dao.NotesDao
import com.example.myhackeruapp10.dataClasses.Note
import com.example.myhackeruapp10.repository.NotesRepository

class NotesViewModel(val app:Application): AndroidViewModel(app) {

    val repository = NotesRepository.getInstance(app)

    var notesLiveData: LiveData<List<Note>> = repository.getNotes()

    fun addNote (note:Note){
        repository.addNote(note)
    }
}