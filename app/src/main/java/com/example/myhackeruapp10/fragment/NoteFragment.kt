package com.example.myhackeruapp10.fragment

import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.myhackeruapp10.Manager.ImageManager
import com.example.myhackeruapp10.R
import com.example.myhackeruapp10.dataClasses.Note
import java.net.URI

class NoteFragment(val note: Note): Fragment(R.layout.note_fragment_layout) {

    override fun onResume() {
        super.onResume()
        val activity = requireActivity()

        val itemName = activity.findViewById<TextView>(R.id.fragment_Item_Name)
        itemName.text = note.item

        val background = activity.findViewById<ConstraintLayout>(R.id.fragment_Background)
        background.setOnClickListener{
            activity.supportFragmentManager.beginTransaction().remove(this).commit()
        }

        val itemAmount = activity.findViewById<TextView>(R.id.fragment_Item_Amount)
        itemAmount.text = note.amount.toString()

        val close = activity.findViewById<Button>(R.id.fragment_Close)
        close.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().remove(this).commit()
        }

        val itemImage = activity.findViewById<ImageView>(R.id.fragment_Item_Image)
        itemImage.setOnClickListener {
            if (note.imageType != null){
                ImageManager.inFragmentAlertDialog(this.requireContext(),note)
                activity.supportFragmentManager.beginTransaction().remove(this).commit()
            }
        }
        if (note.imageType != null){
            if (note.imageType == Note.ImageType.URI){
                itemImage.setImageURI(Uri.parse(note.imageUrl))
            }else{
                Glide.with(this).load(note.imageUrl).into(itemImage)
            }
        }
    }
}
