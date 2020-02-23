package mnf.future.talk.Activity

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.android.synthetic.main.content_authentication.*
import mnf.future.talk.ApplicationClass
import mnf.future.talk.HelperClasses.AuthHelper
import mnf.future.talk.MainActivity
import mnf.future.talk.R
import mnf.future.talk.Tools.Misc
import mnf.future.talk.Utils.isEmail


class AuthenticationActivity : AppCompatActivity() {
    // private lateinit var auth: FirebaseAuth

    var TAG = "future_login"
    // This key determines weather the action button calls sign in method or sign up method.
    // true: sign in Profile
    // false: sign up profile
    var authState = false;

    public override fun onStart() {
        super.onStart()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        val usernameEdtDrawable = usernameEdt.getBackground() as GradientDrawable
        val passwordEdtDrawable = passwordEdt.getBackground() as GradientDrawable

        // auth = FirebaseAuth.getInstance()

        var roboto_bold = Misc.getFont(this.applicationContext, R.font.roboto_bold)
        var roboto = Misc.getFont(this.applicationContext, R.font.roboto)
        titleOne.typeface = roboto_bold
        titleTwo.typeface =  roboto
        forgetPass.typeface = roboto_bold
        or_tv.typeface = roboto_bold
        signup_pre.typeface = roboto
        signup_tv.typeface = roboto_bold
        var auth = FirebaseAuth.getInstance()


        /**
         * Callback when auth state is changed singin /signup
         */
        val authCallback = OnCompleteListener<AuthResult> {task ->
            progressSpinner.visibility = View.GONE
            Log.d(TAG,"Auth callback called ")
            if(task.isSuccessful){
                Log.d(TAG,"authentication success from callback")
                routeManager()
            } else {

                Log.e(TAG,"authentication error from callback ${task.exception?.message} \n code - ${(task.exception as FirebaseAuthException?)!!.errorCode}"+task.exception)
                setErrorMessage( "Invalid Credentials please try again !", View.VISIBLE, R.color.red, usernameEdtDrawable, passwordEdtDrawable)
            }

        }

        // Setting click listener for sign-in button
        signin_btn.setOnClickListener{ view ->
            progressSpinner.visibility = View.VISIBLE
            /**
             * IF previously any invalid email is entered clear them
             */
            setEmailWarning(false, usernameEdtDrawable)
            setErrorMessage( "", View.GONE, android.R.color.white, usernameEdtDrawable, passwordEdtDrawable)

            Log.d(TAG, "username is email or not - ${usernameEdt.toString().isEmail()}")
            /**
             * Check if the user entered a valid email address
             */
            if(usernameEdt.text.toString().isEmail()) {
                Log.d(TAG, " button pressed " + usernameEdt.text + passwordEdt.text)
                // if (authState) signIn() else createAccount()
                if (authState) {
                    Log.d(TAG, " authState true " + usernameEdt.text + passwordEdt.text)

                    AuthHelper().signInUser(usernameEdt.text.toString(), passwordEdt.text.toString(), authCallback)
                } else {
                    Log.d(TAG, " authState false " + usernameEdt.text + passwordEdt.text)

//                auth.signInWithEmailAndPassword(usernameEdt.text.toString(), passwordEdt.text.toString())
//                        .addOnCompleteListener(authCallback)
                    AuthHelper().createUserAccount(usernameEdt.text.toString(), passwordEdt.text.toString(), authCallback)
                }
                // AuthHelper().createUserAccount(usernameEdt.text.toString(), passwordEdt.text.toString(), authCallback)
            } else {
                /**
                 * User entered a invalid email
                 */
                Log.e(TAG, "user entered a invalid email - ${usernameEdt.text}")
                progressSpinner.visibility = View.GONE
                setEmailWarning(true, usernameEdtDrawable)

            }
        }
        signup_tv.setOnClickListener{ view ->
            Log.d(TAG, "singup text clicked")
            changeProfille(!authState)
           // changeToSignUp()
        }

    }

    private fun setEmailWarning(status: Boolean, edtDrawable: GradientDrawable) {
        if(status){
            edtDrawable.setStroke(3, resources.getColor(R.color.red))
            errorTv.visibility = View.VISIBLE
            errorTv.text = "Please enter a Valid Email !"
        } else {
            edtDrawable.setStroke(3, resources.getColor(android.R.color.white))
            errorTv.visibility = View.GONE
            errorTv.text = ""
        }
    }
    private fun setErrorMessage(text: String, visibility: Int, color: Int, usrEdtDrawable: GradientDrawable, pwdEdtDrawable: GradientDrawable) {
            errorTv.text = text
            errorTv.visibility = visibility
            usrEdtDrawable.setStroke(3, resources.getColor(color))
            pwdEdtDrawable.setStroke(3, resources.getColor(color))
    }

    /*fun createAccount() {
        Log.d(TAG,"createAccount called")
        auth.createUserWithEmailAndPassword(usernameEdt.text.toString(), passwordEdt.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                       // routeManager(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }

                    // ...
                }
    }*/
   /* fun signIn(){
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
    }*/
    fun routeManager(){
        val user = ApplicationClass.auth.currentUser
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
        } else {
            Log.d(TAG, "routeManager user returned null")
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
