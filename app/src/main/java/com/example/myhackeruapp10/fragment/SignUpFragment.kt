package com.example.myhackeruapp10.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.ViewUtils
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.myhackeruapp10.Manager.UserManager
import com.example.myhackeruapp10.R
import com.example.myhackeruapp10.ViewModel.LoginViewModel
import org.w3c.dom.Text

class SignUpFragment() : Fragment(R.layout.signup_fragment) {

    private val loginViewModel: LoginViewModel by activityViewModels()
    override fun onResume() {
        super.onResume()
        val activity = requireActivity()

        val editUser = activity.findViewById<EditText>(R.id.SignUp_Username)
        editUser.setText(loginViewModel.username)
        editUser.addTextChangedListener {
            loginViewModel.username = it.toString()
        }

        val editPass = activity.findViewById<EditText>(R.id.SignUp_Password)
        editPass.setText(loginViewModel.password)
        editPass.addTextChangedListener {
            loginViewModel.password = it.toString()
        }

        val signUpBtn = activity.findViewById<Button>(R.id.SignUp_Fragment_SignUp)
        signUpBtn.setOnClickListener {
            onSignUpClick()
        }

        val signUpText = activity.findViewById<TextView>(R.id.to_signIn)
        signUpText.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().replace(R.id.Login_Fragment_Holder,SignInFragment()).commit()
        }
    }

    fun onSignUpClick(){

        UserManager.signUp(loginViewModel.username!!,loginViewModel.password!!)

    }


}