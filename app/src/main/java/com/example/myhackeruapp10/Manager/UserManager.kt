package com.example.myhackeruapp10.Manager

object UserManager {

    var musername = "dvir"
    var mpassword = "123"

    fun signIn(username: String, password: String): Boolean{
        if (username == musername && password == mpassword){
            return true
        }
        return false
    }

    fun signUp(username: String,password: String){
        musername = username
        mpassword = password
    }
}