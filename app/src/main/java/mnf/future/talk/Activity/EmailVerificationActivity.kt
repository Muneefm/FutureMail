package mnf.future.talk.Activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_email_verification.*
import kotlinx.android.synthetic.main.content_email_verification.*
import mnf.future.talk.MainActivity
import mnf.future.talk.R
import mnf.future.talk.Tools.Misc

class EmailVerificationActivity : AppCompatActivity() {
    var TAG = "future_login"
    private lateinit var auth: FirebaseAuth


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        if(currentUser !=null ){
            Log.d(TAG, "current user is not null")
            if (currentUser.isEmailVerified)  startActivity(Intent(this, MainActivity::class.java)) else Log.d(TAG, "user email is not verified ")
        } else {
            Log.d(TAG, " current user is  null ");
        }
        //routeManager(currentUser)

        // updateUI(currentUser)
        Log.d(TAG, "user is "+currentUser)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_verification)
        auth = FirebaseAuth.getInstance()

        var maven_black = Misc.getFont(this.applicationContext, R.font.maven_black)
        var maven_medium = Misc.getFont(this.applicationContext, R.font.maven_medium)


        title_tv.typeface = maven_black
        subtitle_tv.typeface = maven_medium
        pre_text.typeface = maven_medium
        login.typeface = maven_black

        login.setOnClickListener{view ->
            Log.d(TAG, "login pressed")
            startActivity(Intent(this, AuthenticationActivity::class.java))
        }
        btn_email.setOnClickListener{view ->

            Log.d(TAG, "btn_email pressed")
            val currentUser = auth.currentUser
            if(currentUser !=null) {
                Log.d(TAG,"btm_email press, Email sending")

                currentUser.sendEmailVerification()
            }else{
                Log.d(TAG,"btm_email press, User is null")
            }
        }


    }

}
