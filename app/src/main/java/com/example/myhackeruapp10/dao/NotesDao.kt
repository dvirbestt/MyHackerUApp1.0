package com.example.myhackeruapp10.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myhackeruapp10.dataClasses.Note

@Dao
interface NotesDao {

    @Insert
    fun addNote(note: Note)

    @Query("SELECT * FROM notes")
    fun getNotes(): LiveData<List<Note>>

    @Delete
    fun deleteNote(note: Note)

    @Update
    fun updateNote(note: Note)
}