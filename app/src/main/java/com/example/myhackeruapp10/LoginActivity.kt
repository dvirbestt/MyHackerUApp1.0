package com.example.myhackeruapp10

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhackeruapp10.fragment.SignInFragment
import com.example.myhackeruapp10.fragment.SignUpFragment
import java.util.prefs.AbstractPreferences

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

    fun displaySignUp(){
        supportFragmentManager.beginTransaction().replace(R.id.Login_Fragment_Holder, SignUpFragment()).commit()
    }

    fun checkPref(){

        val lastLogin = sharedPreferences.getLong("Last_Login",-1)
        println(lastLogin)
        if (System.currentTimeMillis() - lastLogin  < 3600000 && lastLogin != -1L){
            startActivity(Intent(this,MainActivity::class.java))
        }else {
            displaySignIn()
        }
    }

}