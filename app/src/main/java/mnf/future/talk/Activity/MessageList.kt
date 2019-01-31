package mnf.future.talk.Activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_message_list.*
import mnf.future.talk.Adapters.MessageAdapter
import mnf.future.talk.Models.Message
import mnf.future.talk.R

class MessageList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var mDataset: List<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)
        setSupportActionBar(toolbar)
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
    }

}
