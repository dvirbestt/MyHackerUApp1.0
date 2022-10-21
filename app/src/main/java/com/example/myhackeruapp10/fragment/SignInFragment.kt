package com.example.myhackeruapp10.fragment

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myhackeruapp10.MainActivity
import com.example.myhackeruapp10.Manager.UserManager
import com.example.myhackeruapp10.R

class SignInFragment(val func: ()-> Unit ) : Fragment(R.layout.signin_fragment) {

    override fun onResume() {
        super.onResume()
        val activity = requireActivity()

        val loginButton = activity.findViewById<Button>(R.id.Login_Fragment_Login)
        loginButton.setOnClickListener {
            onLoginClick()
        }
        val signInText = activity.findViewById<TextView>(R.id.to_signUp)
        signInText.setOnClickListener {
            onTextClick()
        }
    }


    fun onLoginClick(){
        val activity = requireActivity()

        val editUser = activity.findViewById<EditText>(R.id.Login_Username).text.toString()
        val editPass = activity.findViewById<EditText>(R.id.Login_Password).text.toString()

        if (UserManager.signIn(editUser,editPass,activity)){
            activity.getPreferences(Context.MODE_PRIVATE).edit().putLong("Last_Login",System.currentTimeMillis()).apply()
            startActivity(Intent(activity,MainActivity::class.java))
        }
    }


    fun onTextClick(){
        func()
    }

}