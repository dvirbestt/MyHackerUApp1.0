package com.example.myhackeruapp10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CloseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stopService(Intent(this,NoteService::class.java))
        finish()
    }
}