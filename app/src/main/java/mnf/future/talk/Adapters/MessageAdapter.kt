package mnf.future.talk.Adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.message_item.view.*
import mnf.future.talk.Models.Message
import mnf.future.talk.R

class MessageAdapter(var mDataset: ArrayList<Message>): RecyclerView.Adapter<MessageAdapter.ViewHolder>(){

    val TAG = "MessageAdapterLog"

    inner class ViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {

       val timeline = itemView.timeline
        val msgDateTv = itemView.date_tv
        val msgTitleTv = itemView.message_title

        init {
          timeline.initLine(viewType)
        }
    }
    fun updateDataset(  mData: ArrayList<Message>) {
        this.mDataset = mData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.v(TAG,"onBingViewHolder --- "+mDataset[position].title)
        viewHolder.msgDateTv.text = mDataset[position].date
        viewHolder.msgTitleTv.text = mDataset[position].title

    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val  layoutInflater = LayoutInflater.from(viewGroup.context)
        return ViewHolder(layoutInflater.inflate(R.layout.message_item, viewGroup, false), viewType)
    }


}