package com.example.myhackeruapp10

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.myhackeruapp10.Manager.NotificationManager
import com.example.myhackeruapp10.repository.NotesRepository
import kotlin.concurrent.thread



class NoteService(): Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(1,NotificationManager.createServiceNotification(this))
        myServiceFunction()
        return super.onStartCommand(intent, flags, startId)
    }

    fun myServiceFunction(){
        thread(start = true) {
            while (true){
                Thread.sleep(10000)
                val list = NotesRepository.getInstance(this).getNotesForService()

                if (list.isNotEmpty()){
                    var size = 0
                    for (l in list){
                        if (System.currentTimeMillis() - l.creationTime > 86400000){
                            size++
                        }
                    }
                    if (size > 0) {
                        NotificationManager.display(this,size)
                    }
                    if (size == 0){
                        stopSelf()
                    }
                }

            }


        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}