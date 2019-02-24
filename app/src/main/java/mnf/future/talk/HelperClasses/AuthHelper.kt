package mnf.future.talk.HelperClasses

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.content_authentication.*
import mnf.future.talk.ApplicationClass

class AuthHelper {

    val TAG = "AuthHelperClass"
    val auth = ApplicationClass.auth

    // check if user logged in
    fun manageAuthState() {

    }

    fun isUsesExist(): Boolean{
        return (auth.currentUser != null)
    }

    fun createUserAccount(username: String,password: String, callback: OnCompleteListener<AuthResult>) {
        auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(callback)
    }

    fun signInUser(username: String, password: String, callback: OnCompleteListener<AuthResult>) {
        Log.d(TAG,"signIn called");
        auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(callback)
    }


}