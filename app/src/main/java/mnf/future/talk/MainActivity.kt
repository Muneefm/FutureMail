package mnf.future.talk

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import mnf.future.talk.Activity.AuthenticationActivity
import mnf.future.talk.Activity.EmailVerificationActivity
import mnf.future.talk.Activity.MessageList
import mnf.future.talk.Activity.NewMessage
import mnf.future.talk.Tools.Misc

class MainActivity : AppCompatActivity() {
    var TAG = "future_home"

    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart ")


        //routeManager(currentUser)

        // updateUI(currentUser)
        // Log.d(TAG, "user is "+currentUser)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        auth = FirebaseAuth.getInstance()
        Log.d(TAG, "onCreate ")
        // Check if user is signed in (non-null) and update UI accordingly.
        var currentUser = auth.currentUser

        if(currentUser !=null ){
            Log.d(TAG, "current user is not null ---- "+currentUser.displayName)
            if (!currentUser.isEmailVerified)  startActivity(Intent(this, EmailVerificationActivity::class.java)) else Log.d(TAG, "user email is  verified ")
            nameTv.text = "Hey Ashwathi Achu" //currentUser.displayName
            mailTv.text = currentUser.email
        } else {
            Log.d(TAG, " current user is  null ")
            startActivity(Intent(this, AuthenticationActivity::class.java))
        }

        nameTv.typeface = Misc.getFont(this.applicationContext, R.font.bai)
        mailTv.typeface = Misc.getFont(this.applicationContext, R.font.ss)


        logoutBtn.setOnClickListener{ view ->
            Log.d(TAG, "logout button pressed")
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, AuthenticationActivity::class.java))
        }

        messageViewCard.setOnClickListener{ view ->
            startActivity(Intent(this, MessageList::class.java))
        }
        addNewCard.setOnClickListener{ view->
            startActivity(Intent(this, NewMessage::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
