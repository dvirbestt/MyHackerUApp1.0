package com.example.myhackeruapp10.fragment

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.ViewUtils
import androidx.fragment.app.Fragment
import com.example.myhackeruapp10.Manager.UserManager
import com.example.myhackeruapp10.R
import org.w3c.dom.Text

class SignUpFragment(val function: () -> Unit) : Fragment(R.layout.signup_fragment) {

    override fun onResume() {
        super.onResume()
        val activity = requireActivity()

        val signUpBtn = activity.findViewById<Button>(R.id.SignUp_Fragment_SignUp)
        signUpBtn.setOnClickListener {
            onSignUpClick()
        }

        val signUpText = activity.findViewById<TextView>(R.id.to_signIn)
        signUpText.setOnClickListener {
            onTextClick()
        }
    }

    fun onSignUpClick(){
        val activity = requireActivity()
        val username = activity.findViewById<EditText>(R.id.SignUp_Username)
        val password = activity.findViewById<EditText>(R.id.SignUp_Password)

        UserManager.signUp(username.text.toString(),password.text.toString())

    }

 fun onTextClick(){
     function()
 }
}