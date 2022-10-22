package com.example.myhackeruapp10.fragment

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.myhackeruapp10.MainActivity
import com.example.myhackeruapp10.Manager.UserManager
import com.example.myhackeruapp10.R
import com.example.myhackeruapp10.ViewModel.LoginViewModel

class SignInFragment() : Fragment(R.layout.signin_fragment) {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        val activity = requireActivity()

        val editUser = activity.findViewById<EditText>(R.id.Login_Username)
        editUser.setText(loginViewModel.username)
        editUser.addTextChangedListener {
            loginViewModel.username = it.toString()
        }
        val editPass = activity.findViewById<EditText>(R.id.Login_Password)
        editPass.setText(loginViewModel.password)
        editPass.addTextChangedListener {
            loginViewModel.password = it.toString()
        }

        val loginButton = activity.findViewById<Button>(R.id.Login_Fragment_Login)
        loginButton.setOnClickListener {
            onLoginClick()
        }
        val signInText = activity.findViewById<TextView>(R.id.to_signUp)
        signInText.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().replace(R.id.Login_Fragment_Holder,SignUpFragment()).commit()
        }
    }


    fun onLoginClick(){
        val activity = requireActivity()



        if (UserManager.signIn(loginViewModel.username!!,loginViewModel.password!!,activity)){
            activity.getPreferences(Context.MODE_PRIVATE).edit().putLong("Last_Login",System.currentTimeMillis()).apply()
            startActivity(Intent(activity,MainActivity::class.java))
        }
    }



}