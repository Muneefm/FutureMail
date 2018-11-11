package mnf.future.talk.Activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import mnf.future.talk.R

import kotlinx.android.synthetic.main.activity_authentication.*
import kotlinx.android.synthetic.main.content_authentication.*
import mnf.future.talk.Tools.Misc

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        var roboto_bold = Misc.getFont(this.applicationContext, R.font.roboto_bold)
        var roboto = Misc.getFont(this.applicationContext, R.font.roboto)
        titleOne.typeface = roboto_bold
        titleTwo.typeface =  roboto
        forgetPass.typeface = roboto_bold
        or_tv.typeface = roboto_bold
        signup_pre.typeface = roboto
        signup_tv.typeface = roboto_bold

        fab.setOnClickListener { view ->
            val message = Intent(this, NewMessage::class.java)
            startActivity(message)
        }
    }

}
