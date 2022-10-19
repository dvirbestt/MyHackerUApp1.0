package com.example.myhackeruapp10.dataBases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myhackeruapp10.dao.NotesDao
import com.example.myhackeruapp10.dataClasses.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase: RoomDatabase (){
    abstract fun notesDao(): NotesDao
}