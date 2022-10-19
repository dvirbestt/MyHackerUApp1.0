package com.example.myhackeruapp10.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class Note(val item: String,val amount: Int) {

    @PrimaryKey(autoGenerate = true)
    var id = 0

    var imageType: ImageType?= null
    var imageUrl: String?= null


    enum class ImageType{
        URL,URI
    }

    override fun toString(): String {
        return "$item , $amount"
    }
}