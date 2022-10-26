package com.example.myhackeruapp10.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myhackeruapp10.LoginActivity
import com.example.myhackeruapp10.MainActivity
import com.example.myhackeruapp10.Manager.UserManager
import com.example.myhackeruapp10.R
import com.example.myhackeruapp10.ViewModel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.signup_fragment.*

class SignUpFragment() : Fragment(R.layout.signup_fragment) {

    private val loginViewModel: LoginViewModel by activityViewModels()

    var firebaseAuth=  FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        SignUp_Button.setOnClickListener {
            onSignUpClick()
        }

        to_signIn.setOnClickListener {
            goToSignIn()
        }

        SignUp_Email.setText(loginViewModel.email)
        SignUp_Email.addTextChangedListener {
            loginViewModel.email = it.toString()
        }

        SignUp_Password.setText(loginViewModel.password)
        SignUp_Password.addTextChangedListener {
            loginViewModel.password = it.toString()
        }
    }



    fun onSignUpClick(){

            firebaseAuth.fetchSignInMethodsForEmail(loginViewModel.email!!)
                .addOnSuccessListener {
                    println(it.signInMethods!!.size)
                    if (it.signInMethods!!.size <= 0){
                        firebaseAuth.createUserWithEmailAndPassword(loginViewModel.email!!,loginViewModel.password!!)
                        goToSignIn()
                    }else{
                        displayToast("Already A Member")
                    }
                }
                .addOnFailureListener {
                    displayToast("Try Again Later")
                }
    }

    fun goToSignIn(){
        val activity = requireActivity()
        activity.supportFragmentManager.beginTransaction().replace(R.id.Login_Fragment_Holder,SignInFragment()).commit()
    }









    fun displayToast(text : String){
        Toast.makeText(requireActivity(),text,Toast.LENGTH_LONG).show()
    }

}