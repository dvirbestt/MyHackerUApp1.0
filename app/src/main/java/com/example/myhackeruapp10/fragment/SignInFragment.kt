package com.example.myhackeruapp10.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.myhackeruapp10.LoginActivity
import com.example.myhackeruapp10.MainActivity
import com.example.myhackeruapp10.Manager.UserManager
import com.example.myhackeruapp10.R
import com.example.myhackeruapp10.ViewModel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.signin_fragment.*

class SignInFragment() : Fragment(R.layout.signin_fragment) {

    lateinit var googleGetContent: ActivityResultLauncher<Intent>
    private val loginViewModel: LoginViewModel by activityViewModels()
    var firebaseAuth=  FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        googleGetContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            onGoogleResult(result)
        }
    }
    override fun onResume() {
        super.onResume()
        val activity = requireActivity()


        Login_Username.setText(loginViewModel.email)
        Login_Username.addTextChangedListener {
            loginViewModel.email = it.toString()
        }

        Login_Password.setText(loginViewModel.password)
        Login_Password.addTextChangedListener {
            loginViewModel.password = it.toString()
        }

        Google_login_Button.setOnClickListener {
            onGoogleClick()
        }

        Login_Fragment_Login.setOnClickListener {
            onLoginClick()
        }

        to_signUp.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().replace(R.id.Login_Fragment_Holder,SignUpFragment()).commit()
        }
    }


    fun onLoginClick(){
        firebaseAuth.signInWithEmailAndPassword(loginViewModel.email!!,loginViewModel.password!!)
            .addOnSuccessListener {
                var intent = Intent(requireActivity(),MainActivity::class.java)
                intent.putExtra("name",it.user?.email)
                startActivity(intent)
            }
            .addOnFailureListener {
                displayToast("Wrong Email/Password")
            }
    }


    fun onGoogleClick(){
        val googleSignInOptions = GoogleSignInOptions.Builder()
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestProfile()
            .build()
        val googleIntent=  GoogleSignIn.getClient(requireActivity(),googleSignInOptions).signInIntent

        googleGetContent.launch(googleIntent)
    }

    private fun onGoogleResult(result: ActivityResult) {
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        task.addOnSuccessListener {
            loginOrSignupFirebase(it)
        }
            .addOnFailureListener {
                displayToast("cant Log You In With Google")
            }
    }

    private fun loginOrSignupFirebase(googleSignInAccount: GoogleSignInAccount){
        firebaseAuth.fetchSignInMethodsForEmail(googleSignInAccount.email!!)
            .addOnFailureListener{displayToast("Failed")}
            .addOnSuccessListener {
                if (it.signInMethods.isNullOrEmpty()){
                    registerToAppFromGoogle(googleSignInAccount)
                    println("Register ")
                }else{
                    (requireActivity() as LoginActivity).main(googleSignInAccount.givenName)
                }
            }
    }

    private fun registerToAppFromGoogle(googleSignInAccount: GoogleSignInAccount) {
        val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken,null)
        firebaseAuth.signInWithCredential(authCredential)
            .addOnSuccessListener { (requireActivity() as LoginActivity).main(googleSignInAccount.givenName) }
            .addOnFailureListener { displayToast("Try Later") }

    }

    fun displayToast(text : String){
        Toast.makeText(requireActivity(),text, Toast.LENGTH_LONG).show()
    }

}