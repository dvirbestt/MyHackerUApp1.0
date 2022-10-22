package com.example.myhackeruapp10

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myhackeruapp10.Manager.ImageManager
import com.example.myhackeruapp10.Manager.NotificationManager
import com.example.myhackeruapp10.ViewModel.NotesViewModel
import com.example.myhackeruapp10.adpaters.ItemRecyclerAdapter
import com.example.myhackeruapp10.dataClasses.Note
import com.example.myhackeruapp10.fragment.NoteFragment
import com.example.myhackeruapp10.repository.NotesRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    var placeHolder: Note? = null
    private val notesViewModel:NotesViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onSubmitClick()
        val serviceIntent = Intent(this,NoteService::class.java)
        ContextCompat.startForegroundService(this,serviceIntent)
        notesViewModel.notesLiveData.observe(this){
            createRecycler(it)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun onSubmitClick(){

        Submit_Item.setOnClickListener {
            val item = findViewById<EditText>(R.id.Item_Text).text.toString()
            val amount = findViewById<EditText>(R.id.Item_Amount).text.toString()
        if (amount != ""){
            thread (start = true){

                notesViewModel.addNote(Note(item,amount.toInt()))
            }
        }else{
            Toast.makeText(this,"Please Fill Both Name And Amount",Toast.LENGTH_SHORT).show()
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

        val adapter = ItemRecyclerAdapter(list,createFragment(),this,getPhoto,savePlaceholder())
        Notes_Recycler.adapter = adapter
    }

    val getPhoto =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        ImageManager.onResultPhoto(placeHolder!!, it, this)
    }

}