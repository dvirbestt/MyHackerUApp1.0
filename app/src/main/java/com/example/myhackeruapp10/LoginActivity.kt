package com.example.myhackeruapp10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhackeruapp10.fragment.SignInFragment
import com.example.myhackeruapp10.fragment.SignUpFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    override fun onStart() {
        super.onStart()
        displaySignIn()
    }

    fun displaySignIn(){
        supportFragmentManager.beginTransaction().replace(R.id.Login_Fragment_Holder,SignInFragment(){
            displaySignUp()
        }).commit()
    }

    fun displaySignUp(){
        supportFragmentManager.beginTransaction().replace(R.id.Login_Fragment_Holder,
            SignUpFragment(){
                displaySignIn()
            }
        ).commit()
    }

}