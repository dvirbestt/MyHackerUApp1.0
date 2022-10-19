package com.example.myhackeruapp10

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myhackeruapp10.Manager.ImageManager
import com.example.myhackeruapp10.Manager.NotificationManager
import com.example.myhackeruapp10.adpaters.ItemRecyclerAdapter
import com.example.myhackeruapp10.dataClasses.Note
import com.example.myhackeruapp10.fragment.NoteFragment
import com.example.myhackeruapp10.repository.NotesRepository
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    var placeHolder: Note? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onSubmitClick()
        NotesRepository.getInstance(this).getNotes().observe(this){
            createRecycler(it)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun onSubmitClick(){
        val button = findViewById<Button>(R.id.Submit_Item)
        button.setOnClickListener {
            val item = findViewById<EditText>(R.id.Item_Text).text.toString()
            val amount = findViewById<EditText>(R.id.Item_Amount).text.toString().toInt()

            thread (start = true){
                NotesRepository.getInstance(this).addNote(Note(item,amount))
                NotificationManager.display(this,item)
            }

        }
    }

    fun savePlaceholder(): (note: Note) -> Unit = {
        placeHolder = it
    }

    fun createFragment(): (note: Note) -> Unit = {
        supportFragmentManager.beginTransaction().replace(R.id.Main_Fragment_Holder,NoteFragment(it)).commit()
    }

    fun createRecycler(list: List<Note>){
        val recycler = findViewById<RecyclerView>(R.id.Notes_Recycler)
        val adapter = ItemRecyclerAdapter(list,createFragment(),this,getPhoto,savePlaceholder())
        recycler.adapter = adapter
    }

    val getPhoto =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        ImageManager.onResultPhoto(placeHolder!!, it, this)
    }

}