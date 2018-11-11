package mnf.future.talk.Activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.content_authentication.*
import kotlinx.android.synthetic.main.content_new_message.*
import mnf.future.talk.R
import mnf.future.talk.Tools.Misc

class NewMessage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        var ss = Misc.getFont(this.applicationContext, R.font.ss)
        var robotoLight = Misc.getFont(this.applicationContext, R.font.roboto_light)

        titleMessage.typeface = ss
        edtMessage.typeface = robotoLight
        edtSubject.typeface = robotoLight
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
