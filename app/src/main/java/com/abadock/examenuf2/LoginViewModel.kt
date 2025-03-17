package com.abadock.examenuf2

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth
    fun login(mail: String, pass: String, ctx: Context, button: Button, nav: NavController) {

        button.setEnabled(false)

        auth = Firebase.auth

        auth.signInWithEmailAndPassword(mail, pass)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {

                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser

                    nav.navigate(R.id.action_loginFragment_to_listFragment)

                } else {

                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(ctx, "Register incorrecte failed.", Toast.LENGTH_SHORT).show()
                    button.setEnabled(true)
                }
            }
    }
}
