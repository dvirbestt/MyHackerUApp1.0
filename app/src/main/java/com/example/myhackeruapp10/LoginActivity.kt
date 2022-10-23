package com.example.myhackeruapp10

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhackeruapp10.fragment.SignInFragment

class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    override fun onStart() {
        super.onStart()
        sharedPreferences = getSharedPreferences("LoginActivity", MODE_PRIVATE)
        checkPref()

    }

    fun displaySignIn(){
        supportFragmentManager.beginTransaction().replace(R.id.Login_Fragment_Holder,SignInFragment()).commit()
    }

    fun main(givenName: String?) {
        var intent = Intent(this,MainActivity::class.java)
        intent.putExtra("name",givenName)
        startActivity(intent)
    }


    fun checkPref(){

        val lastLogin = sharedPreferences.getLong("Last_Login",-1)
        println(lastLogin)
        if (System.currentTimeMillis() - lastLogin  < 3600000 && lastLogin != -1L){
            main(null)
        }else {
            displaySignIn()
        }
    }

}