package com.example.myhackeruapp10.Manager

import android.content.Context
import android.widget.Toast

object UserManager {

    var musername = "dvir"
    var mpassword = "123"

    fun signIn(username: String, password: String, context: Context): Boolean{
            if (username == musername && password == mpassword) {
                return true
            }

        val toast =  Toast.makeText(context,"Wrong User Name / Password",500)
        toast.show()
        return false

    }

    fun signUp(username: String,password: String){
        musername = username
        mpassword = password
    }
}