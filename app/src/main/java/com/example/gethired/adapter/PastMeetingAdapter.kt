package com.example.gethired.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gethired.R
import com.example.gethired.entities.Meeting
import com.google.android.material.button.MaterialButton

class PastMeetingAdapter(private var data: List<Meeting>, private val context: Context) :
    RecyclerView.Adapter<PastMeetingAdapter.ViewHolder>() {


    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }

    private var longClickListener: OnItemLongClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        longClickListener = listener
    }


    class ViewHolder(
        itemView: View,
        private val longClickListener: PastMeetingAdapter.OnItemLongClickListener?
    ) :
        RecyclerView.ViewHolder(itemView) {
        val date = itemView.findViewById<TextView>(R.id.meetingDate)
        val joinBtn = itemView.findViewById<MaterialButton>(R.id.joinMeetingBtn)
        val title=itemView.findViewById<TextView>(R.id.joining_textview)

        var url = ""

        init {
//
//            if (url.isNotEmpty()) {
//                joinBtn.setOnClickListener {
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    itemView.context.startActivity(intent)
//                }
//            }
            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    longClickListener?.onItemLongClick(itemView, position)
                    return@setOnLongClickListener true
                }
                return@setOnLongClickListener false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meeting_list_layout, parent, false)

        return ViewHolder(view, longClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.title.text=""
        holder.date.text = item.date
        holder.url = item.link

        holder.joinBtn.alpha=0.8f
        holder.joinBtn.icon = null
        if(item.isAttended){
            holder.joinBtn.text="Joined"
            holder.joinBtn.setTextColor(context.resources.getColor(R.color.completed))
            holder.joinBtn.setBackgroundColor(context.resources.getColor(R.color.completed_background))

        }else{
            holder.joinBtn.text="Missed"
            holder.joinBtn.setTextColor(context.resources.getColor(R.color.not_joined))
            holder.joinBtn.setBackgroundColor(context.resources.getColor(R.color.not_joined_background))
        }

    }
    fun update(meetingList: List<Meeting>) {
        data=meetingList
        notifyDataSetChanged()
    }
}
