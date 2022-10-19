package com.example.myhackeruapp10.Manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.myhackeruapp10.MainActivity
import com.example.myhackeruapp10.R
import com.example.myhackeruapp10.dataClasses.Note
import kotlinx.coroutines.channels.Channel
import java.util.Date.from

object NotificationManager {

    val channelId = "CHANNEL_ID"



    @RequiresApi(Build.VERSION_CODES.O)
    fun display(context: Context,item: String){

        createNotificationChannel(context)
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle("New Item Added")
            .setContentText("$item Been Added To Your List ")
            .setSmallIcon(R.drawable.cam)
            .build()
        val notificationCompat = NotificationManagerCompat.from(context)
        notificationCompat.notify(1,builder)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(context: Context) {
        val name = "Channel"
        val description = "Channel description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId,name, importance)
        channel.description = description
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}