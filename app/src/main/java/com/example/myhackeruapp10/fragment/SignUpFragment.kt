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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myhackeruapp10.LoginActivity
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
    lateinit var googleGetContent: ActivityResultLauncher<Intent>
    var firebaseAuth=  FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        googleGetContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            onGoogleResult(result)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity()

        /*val editUser = activity.findViewById<EditText>(R.id.SignUp_Username)
        editUser.setText(loginViewModel.username)
        editUser.addTextChangedListener {
            loginViewModel.username = it.toString()
        }*/



        SignUp_Fragment_SignUp.setOnClickListener{
            onGoogleClick()
        }

        to_signIn.setOnClickListener {
            goToSignIn()
        }
    }

    fun onSignUpClick(){
        UserManager.signUp(loginViewModel.username!!,loginViewModel.password!!)
        goToSignIn()
    }

    fun goToSignIn(){
        val activity = requireActivity()
        activity.supportFragmentManager.beginTransaction().replace(R.id.Login_Fragment_Holder,SignInFragment()).commit()
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
        Toast.makeText(requireActivity(),text,Toast.LENGTH_LONG).show()
    }

}