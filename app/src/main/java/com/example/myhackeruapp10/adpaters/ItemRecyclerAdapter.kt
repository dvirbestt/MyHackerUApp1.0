package com.example.myhackeruapp10.adpaters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myhackeruapp10.Manager.ImageManager
import com.example.myhackeruapp10.R
import com.example.myhackeruapp10.dataClasses.Note
import com.example.myhackeruapp10.repository.NotesRepository
import kotlin.concurrent.thread



class ItemRecyclerAdapter(
    val list: List<Note>,
    val createFragment: (Note) -> Unit,
    val context: Context,
    val getPhoto: ActivityResultLauncher<Intent>,
    val savePlaceholder: (Note) -> Unit
): RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textView: TextView
        val deleteImage: ImageView
        val itemPhoto: ImageView
        init {
            textView = view.findViewById(R.id.Item_Recycler_Text)
            deleteImage = view.findViewById(R.id.Item_Delete_Image)
            itemPhoto = view.findViewById(R.id.Item_Image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_recycler_items,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text= list[position].item
        holder.textView.setOnClickListener{
            createFragment(list[position])
        }

        holder.deleteImage.setOnClickListener {
            thread (start = true){  NotesRepository.getInstance(context).deleteNote(list[position])}

        }


        holder.itemPhoto.setOnClickListener {
            savePlaceholder(list[position])
            ImageManager.imageDialog(context,list[position], getPhoto)
        }

        if (list[position].imageType != null){
            if (list[position].imageType == Note.ImageType.URI){
                holder.itemPhoto.setImageURI(Uri.parse(list[position].imageUrl))
            }
            else{
                Glide.with(context).load(list[position].imageUrl).into(holder.itemPhoto)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}