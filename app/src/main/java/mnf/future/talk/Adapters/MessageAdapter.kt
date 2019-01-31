package mnf.future.talk.Adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.message_item.view.*
import mnf.future.talk.Models.Message
import mnf.future.talk.R

class MessageAdapter(val mDataset: List<Message>): RecyclerView.Adapter<MessageAdapter.ViewHolder>(){

    val TAG = "MessageAdapterLog"

    inner class ViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {

       val timeline = itemView.timeline

        init {
          timeline.initLine(viewType)
        }
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        Log.v(TAG,"onBingViewHolder --- "+mDataset[p1])
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val  layoutInflater = LayoutInflater.from(viewGroup.context)
        return ViewHolder(layoutInflater.inflate(R.layout.message_item, viewGroup, false), viewType)
    }


}