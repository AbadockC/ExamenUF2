package com.abadock.examenuf2

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth


    fun register(
        mail: String,
        pass: String,
        ctx: Context,
        navController: NavController,
        button: Button
    ) {

        button.setEnabled(false)

        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(mail, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    navController.navigate(R.id.action_registerFragment_to_loginFragment)

                } else {

                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(ctx, "El registre es incorrecte", Toast.LENGTH_SHORT).show()
                    button.setEnabled(true)

                }
            }
    }
}