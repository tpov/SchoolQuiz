package com.tpov.schoolquiz.presentation.network

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AutorisationViewModel : ViewModel() {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun loginAcc(email: String, pass: String, context: Context) {
        val user = auth.currentUser
        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener {

        }.addOnFailureListener {
            Toast.makeText(context, "error login, try again", Toast.LENGTH_LONG).show()
        }
    }

    fun createAcc(email: String, pass: String, context: Context) {
        auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
            val user = auth.currentUser
            user?.sendEmailVerification()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Verification email sent to ${user.email}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Failed to send verification email.", Toast.LENGTH_LONG).show()
                }
            }
            Toast.makeText(context, "create account successful", Toast.LENGTH_LONG).show()
            //todo start ActivityLogin
        }.addOnFailureListener {
            Toast.makeText(context, "error create account, try again", Toast.LENGTH_LONG).show()
        }
    }

    fun logOut() {
        val user = auth.currentUser
        auth.signOut()
    }

    fun loginStatus() {
        val user = auth.currentUser
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                loadData()
            } else {

            }
        }
    }

    fun reLoadAcc() {
        var user = auth.currentUser
        auth.sendPasswordResetEmail("email").addOnSuccessListener {

        }.addOnFailureListener {

        }
    }

    private fun loadData() {

    }

}