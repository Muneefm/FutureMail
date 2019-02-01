package mnf.future.talk.Activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_message_list.*
import mnf.future.talk.Adapters.MessageAdapter
import mnf.future.talk.Models.Message
import mnf.future.talk.R

class MessageList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var mDataset: List<Message>
    var db = FirebaseFirestore.getInstance()
    val TAG = "MessageListLog"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)
        setSupportActionBar(toolbar)
        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

        if(currentUser !=null ){
            Log.d(TAG, "current user is not null ---- "+currentUser.displayName)
            if (!currentUser.isEmailVerified)  startActivity(Intent(this, EmailVerificationActivity::class.java)) else Log.d(TAG, "user email is  verified ")
        } else {
            Log.d(TAG, " current user is  null ")
            startActivity(Intent(this, AuthenticationActivity::class.java))
        }
        mDataset = listOf(Message("",1,1,1,1), Message("",2,2,2,2), Message("", 23,234,234,234))

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
        docRef.get()
                .addOnSuccessListener { collection ->
                    if (collection != null) {
                        Log.d(TAG, "DocumentSnapshot data: " + collection.documents[0].id)
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }

    }

}
