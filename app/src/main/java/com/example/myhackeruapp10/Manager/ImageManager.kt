package com.example.myhackeruapp10.Manager

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myhackeruapp10.dataClasses.Note
import com.example.myhackeruapp10.repository.NotesRepository
import com.example.myhackeruapp10.retrofit.PhotoApi
import com.example.myhackeruapp10.retrofit.RetrofitHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

object ImageManager {

    fun getPhotoFromGallery(note: Note,getPhoto: ActivityResultLauncher<Intent>){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        getPhoto.launch(intent)
    }

    fun imageDialog(context: Context,note: Note ,getPhoto: ActivityResultLauncher<Intent>){
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Choose Photo From")
        dialog.setNeutralButton("Close"){ dialogInterface: DialogInterface, i: Int -> }
        dialog.setPositiveButton("Gallery"){ dialogInterface: DialogInterface, i: Int ->
            getPhotoFromGallery(note,getPhoto)
        }
        dialog.setNegativeButton("Api"){ dialogInterface: DialogInterface, i: Int ->
            getPhotoFromApi(note,context)
        }
        dialog.show()

    }

    fun onResultPhoto(note: Note,result: ActivityResult,context: Context){
        GlobalScope.launch {
        if (result.resultCode == AppCompatActivity.RESULT_OK){
            val uri = result.data?.data
            if (uri!=null){
                context.contentResolver.takePersistableUriPermission(uri,Intent.FLAG_GRANT_READ_URI_PERMISSION)
                note.imageType = Note.ImageType.URI
                note.imageUrl = uri.toString()
                NotesRepository.getInstance(context).updateNote(note)

            }

        }
        }

    }

    fun getPhotoFromApi(note: Note,context: Context){
        val photoApi = RetrofitHelper.getInstance().create(PhotoApi::class.java)
        GlobalScope.launch {
            val result = photoApi.getPhotos(note.item)
            println()
            if (result.body()?.hits?.size != 0){

                note.imageType = Note.ImageType.URL
                note.imageUrl = result.body()?.hits!![0].webformatURL
                GlobalScope.launch {
                   NotesRepository.getInstance(context).updateNote(note)
                }

            }
        }
    }

    fun inFragmentAlertDialog(context: Context,note: Note){
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Delete Photo?")
            .setNeutralButton("close"){ dialogInterface: DialogInterface, i: Int -> }
            .setPositiveButton("Delete"){ dialogInterface: DialogInterface, i: Int ->
                ImageManager.deleteImage(note,context)
            }
            .show()
    }

    fun deleteImage(note: Note,context: Context){
        GlobalScope.launch() {
            NotesRepository.getInstance(context).deleteImage(note)
        }
    }
}