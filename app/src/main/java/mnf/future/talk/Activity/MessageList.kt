package mnf.future.talk.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_message_list.*
import mnf.future.talk.Adapters.MessageAdapter
import mnf.future.talk.Models.Message
import mnf.future.talk.R
import mnf.future.talk.Tools.Misc
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime

import java.time.format.DateTimeFormatter

class MessageList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MessageAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    //private lateinit var mDataset: List<Message>()
    var db = FirebaseFirestore.getInstance()
    val TAG = "MessageListLog"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)
        setSupportActionBar(toolbar)
        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        var ss = Misc.getFont(this.applicationContext, R.font.ss)
        var robotoLight = Misc.getFont(this.applicationContext, R.font.roboto_light)
        var maven_black = Misc.getFont(this.applicationContext, R.font.maven_black)
        var maven_medium = Misc.getFont(this.applicationContext, R.font.maven_medium)
        var roboto = Misc.getFont(this.applicationContext, R.font.roboto)
        title_timeline.typeface = ss

        if(currentUser !=null ){
            Log.d(TAG, "current user is not null ---- "+currentUser.displayName)
            if (!currentUser.isEmailVerified)  startActivity(Intent(this, EmailVerificationActivity::class.java)) else Log.d(TAG, "user email is  verified ")
        } else {
            Log.d(TAG, " current user is  null ")
            startActivity(Intent(this, AuthenticationActivity::class.java))
        }
       /* mDataset = listOf(Message("","","21-21-2008",1,1, 2001),
                Message("","","21-21-2008",1,1, 2001),
                Message("","","21-21-2008",1,1, 2001))*/

        var mDataset = ArrayList<Message>()

        viewManager = LinearLayoutManager(this)
        viewAdapter = MessageAdapter(mDataset)

        recyclerView = findViewById<RecyclerView>(R.id.rv_message).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        val docRef = db.collection("users").document(currentUser!!.uid).collection("messages")
        docRef.orderBy("date_timestamp").get()
                .addOnSuccessListener { collection ->
                    if (collection != null) {
                        Log.d(TAG, "DocumentSnapshot data: " + collection.documents[0])
                        // var messageList = ArrayList<Message>()
                        for(document in collection) {
                            Log.d(TAG, "foreach loop  " + document.data)
                            val item = document.data
                            mDataset.add(Message(item["title"] as String, item["message"] as String,
                                    item["date"] as String, 1,1,1))
                            // var date = LocalDate.parse(item["date"])
                           // var dateFormat = SimpleDateFormat("dd-MM-yyyy")
                           // var date = dateFormat.parse("1-2-2019" )
                           // Log.d(TAG, "date  " + date)

                        }
                        Log.d(TAG, "after loop  " + mDataset)
                        viewAdapter.updateDataset(mDataset)

                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }

    }





}
