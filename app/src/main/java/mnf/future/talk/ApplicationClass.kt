package mnf.future.talk

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class ApplicationClass: Application() {

    val TAG = "ApplicationClass"
    // private lateinit var auth: FirebaseAuth

    companion object {
        public lateinit var auth: FirebaseAuth
    }

    override fun onCreate() {
        super.onCreate()
        auth = FirebaseAuth.getInstance()
        Log.v(TAG, "onCreate Application class")
    }


    
}