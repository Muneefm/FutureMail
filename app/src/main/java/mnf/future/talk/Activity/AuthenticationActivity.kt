package mnf.future.talk.Activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import mnf.future.talk.R

import kotlinx.android.synthetic.main.activity_authentication.*
import kotlinx.android.synthetic.main.content_authentication.*
import mnf.future.talk.MainActivity
import mnf.future.talk.Tools.Misc

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    var TAG = "future_login"
    // This key determines weather the action button calls sign in method or sign up method.
    // true: sign in Profile
    // false: sign up profile
    var authState = false;

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        routeManager(currentUser)

        // updateUI(currentUser)
        Log.d(TAG, "user is "+currentUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        auth = FirebaseAuth.getInstance()

        var roboto_bold = Misc.getFont(this.applicationContext, R.font.roboto_bold)
        var roboto = Misc.getFont(this.applicationContext, R.font.roboto)
        titleOne.typeface = roboto_bold
        titleTwo.typeface =  roboto
        forgetPass.typeface = roboto_bold
        or_tv.typeface = roboto_bold
        signup_pre.typeface = roboto
        signup_tv.typeface = roboto_bold

        signin_btn.setOnClickListener{ view ->
            Log.d(TAG, " button pressed "+usernameEdt.text+passwordEdt.text)
            if (authState) signIn() else createAccount()

        }
        signup_tv.setOnClickListener{ view ->
            Log.d(TAG, "singup text clicked")
            changeProfille(!authState)
           // changeToSignUp()
        }

    }
    fun createAccount() {
        Log.d(TAG,"createAccount called")
        auth.createUserWithEmailAndPassword(usernameEdt.text.toString(), passwordEdt.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        routeManager(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }

                    // ...
                }
    }
    fun signIn(){
        Log.d(TAG,"signIn called");
        auth.signInWithEmailAndPassword(usernameEdt.text.toString(), passwordEdt.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        // updateUI(user)

                        routeManager(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }

                    // ...
                }
    }
    fun routeManager(user : FirebaseUser?){
        if(user != null) {
            user.reload()

            if (user.isEmailVerified) {
                Log.d(TAG, "User email is verified")
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // Email is not verified
                user.sendEmailVerification()
                startActivity(Intent(this, EmailVerificationActivity::class.java))
                Log.d(TAG, "Email Verifications send request")
            }
        }
    }

    // Switch all text to sign up profile.
    fun changeProfille(key: Boolean) {
        authState = key
        if(!key) {
            // authState = "sign_up"
            signup_pre.text = "Already Have an account?  "
            signup_tv.text = "SIGN IN"
            titleTwo.text = "Sign up to Continue"
            signin_btn.text = "Sign up"
        } else {
            // authState = "sign_in"
            signup_pre.text = "Don't have an account?  "
            signup_tv.text = "SIGN UP"
            titleTwo.text = "Sign in to continue"
            signin_btn.text = "Sign In"
        }
    }

}
