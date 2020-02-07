package mnf.future.talk.Activity

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.content_authentication.*
import kotlinx.android.synthetic.main.new_message_content.*
import mnf.future.talk.R
import mnf.future.talk.Tools.Misc
import android.view.WindowManager
import android.os.Build
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.content_main.*
import mnf.future.talk.ApplicationClass
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class NewMessage : AppCompatActivity() {

    var TAG = "NewMessageActivity";
    var db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        auth = ApplicationClass.auth
        var currentUser = auth.currentUser

        if(currentUser !=null ){
            Log.d(TAG, "current user is not null ---- "+currentUser.displayName)
            if (!currentUser.isEmailVerified)  startActivity(Intent(this, EmailVerificationActivity::class.java)) else Log.d(TAG, "user email is  verified ")
        } else {
            Log.d(TAG, " current user is  null ")
            startActivity(Intent(this, AuthenticationActivity::class.java))
        }

        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        var selectedDay = ""
        var selectedMonth =  ""
        var selectedYear = ""

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources
                    .getColor(android.R.color.white)
        }*/
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
        }
        var ss = Misc.getFont(this.applicationContext, R.font.ss)
        var robotoLight = Misc.getFont(this.applicationContext, R.font.roboto_light)
        var maven_black = Misc.getFont(this.applicationContext, R.font.maven_black)
        var maven_medium = Misc.getFont(this.applicationContext, R.font.maven_medium)
        var roboto = Misc.getFont(this.applicationContext, R.font.roboto)

        titleMsg.typeface = maven_black
        titleTv.typeface = maven_black
        messageTv.typeface = maven_black
        edtMessage.typeface = roboto
        edtTitle.typeface = roboto
        dateTv.typeface = maven_black
        edtDate.typeface = roboto

       /* titleMessage.typeface = ss
        edtMessage.typeface = robotoLight
        edtSubject.typeface = robotoLight*/
        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/
        var date = ""
        edtDate.setOnClickListener{ view ->
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear , mMonth, mDay ->
                Log.v(TAG, "Selected days are = "+mYear+mMonth+mDay)
                edtDate.setText(""+mDay+"/"+(mMonth+1)+"/"+mYear)
                date = ""+mDay + "-"+(mMonth+1)+"-"+mYear

                selectedDay = "" + mDay
                selectedMonth = "" + (mMonth + 1)
                selectedYear = "" + mYear



            }, year, month, day)
            datePicker.show()
        }


        btnSend.setOnClickListener{view ->

            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val dateObj = dateFormat.parse(date)
            Log.v(TAG,"dateOBJ got is "+dateObj);
            if(edtTitle.text != null && edtDate.text != null && edtMessage.text != null) {
                // if every field has values save them to collection
                val message = HashMap<String, Any>()
                message["userid"] = currentUser!!.uid
                message["title"] = edtTitle.text.toString()
                message["date"] = edtDate.text.toString()
                message["message"] = edtMessage.text.toString()
                message["day"] = selectedDay
                message["month"] = selectedMonth
                message["year"] = selectedYear
                message["date_timestamp"] = dateObj

                val RootMessage = HashMap<String, HashMap<String, Any>>()
                RootMessage[date+"$"+UUID.randomUUID().toString()] = message

                val messageRoot = HashMap<String, HashMap<String, Any>>()
                messageRoot["messages"] = message
                // This data need to be written in order to skip the italic collection id issue
                val userData = HashMap<String, String?>()
                userData["userid"] = currentUser!!.uid
                userData["name"] = currentUser!!.displayName
                db.collection("messages").document().set(message)

                /**
                 * mapOf(
                "title" to edtTitle.text.toString(),
                "date" to edtDate.text.toString(),
                "message" to edtMessage.text.toString(),
                "day" to selectedDay,
                "month" to selectedMonth,
                "year" to selectedYear,
                "date_timestamp" to dateObj
                ), SetOptions.merge()
                 */

                db.collection("users").document(currentUser!!.uid).collection("messages").document(date)
                        .set(message)
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

            }
        }
    }

}
