package com.tpov.schoolquiz.presentation.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tpov.schoolquiz.data.model.Buy
import com.tpov.schoolquiz.data.model.Points
import com.tpov.schoolquiz.data.model.Profile
import com.tpov.schoolquiz.data.model.TimeInGames


class AutorisationViewModel : ViewModel() {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    val someData = MutableLiveData<Boolean>()
    private lateinit var databaseReference: DatabaseReference

    fun loginAcc(email: String, pass: String, context: Context) {
        val user = auth.currentUser
        someData.value = false
        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener {
            Log.d("v3.0", "someData.value = true")
            someData.value = true
        }.addOnFailureListener {
            Toast.makeText(context, "error login, try again", Toast.LENGTH_LONG).show()
        }
    }

    fun createAcc(
        email: String,
        pass: String,
        context: Context,
        name: String,
        date: String,
        city: String
    ) {
        auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
            val databaseReference = FirebaseDatabase.getInstance().reference

            val user = auth.currentUser
            user?.sendEmailVerification()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Verification email sent to ${user.email}",
                        Toast.LENGTH_LONG
                    ).show()

                        database.getReference("Profiles/${user.uid}").setValue(
                        Profile(
                            -1,
                            email,
                            name,
                            date,
                            Points(0, 0, 0, 0),
                            "0",
                            Buy(1, 0, 1, "0", "0", "0"),
                            "0",
                            "",
                            city,
                            0,
                            TimeInGames("", "", "0", 0)
                        )
                    )

                } else {
                    Toast.makeText(context, "Failed to send verification email.", Toast.LENGTH_LONG)
                        .show()
                }
            }
            //todo start Activity
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


}