package com.example.myhackeruapp10.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.myhackeruapp10.dataBases.NotesDatabase
import com.example.myhackeruapp10.dataClasses.Note


class NotesRepository private constructor(context: Context) {

    val db = Room.databaseBuilder(
        context,
        NotesDatabase::class.java, "database-name"
    ).build()

    companion object {
        lateinit var instance: NotesRepository

        fun getInstance(context: Context): NotesRepository{
            if (!::instance.isInitialized){
                instance = NotesRepository(context)
            }
            return instance
        }
    }

    fun addNote(note: Note){
        db.notesDao().addNote(note)
    }

    fun getNotes(): LiveData<List<Note>>{
        return db.notesDao().getNotes()
    }

    fun deleteNote(note: Note){
        db.notesDao().deleteNote(note)
    }

    fun updateNote(note: Note){
        db.notesDao().updateNote(note)
    }

    fun deleteImage(note: Note){
        note.imageType = null
        note.imageUrl = null
        updateNote(note)
    }
}