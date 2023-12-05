package com.example.gethired.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.gethired.R
import com.example.gethired.entities.Meeting
import com.google.android.material.button.MaterialButton
import java.time.LocalDate
import java.time.LocalTime

class MeetingAdapter(private var data: List<Meeting>, private val context: Context) :
    RecyclerView.Adapter<MeetingAdapter.ViewHolder>()  {


    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }

    private var longClickListener: OnItemLongClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        longClickListener = listener
    }


    class ViewHolder (itemView: View,private val longClickListener: MeetingAdapter.OnItemLongClickListener?):RecyclerView.ViewHolder(itemView){
        val date=itemView.findViewById<TextView>(R.id.meetingDate)
        val joinBtn=itemView.findViewById<MaterialButton>(R.id.joinMeetingBtn)
        var url=""

        init {

            if(url.isNotEmpty()){
                joinBtn.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    itemView.context.startActivity(intent)
                }
            }
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

        return ViewHolder(view,longClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=data[position]

        holder.date.text=item.date
        holder.url=item.link


        if(item.isAttended){
            holder.joinBtn.text="Joined"
            holder.joinBtn.icon=null
            holder.joinBtn.setTextColor(context.resources.getColor(R.color.completed))
            holder.joinBtn.setBackgroundColor(context.resources.getColor(R.color.completed_background))

        }
        else{
            val date=LocalDate.now()
            val arr=item.date.split("-")
            val year = arr[2].toInt()
            val month = arr[1].toInt()
            val day = arr[0].toInt()
            val itemDate=LocalDate.of(year,month,day)

            val currentTime = LocalTime.now()
            val time = item.time.split(":")
            val hour = time[0].toInt()
            val min = time[1].toInt()
            val itemTime=LocalTime.of(hour,min)

            if(date>itemDate){
                holder.joinBtn.rippleColor=null
                holder.joinBtn.text="Missed"
                holder.joinBtn.icon=null
                holder.joinBtn.setTextColor(context.resources.getColor(R.color.not_joined))
                holder.joinBtn.setBackgroundColor(context.resources.getColor(R.color.not_joined_background))
            }
            else if(date==itemDate&&currentTime>itemTime){
                holder.joinBtn.rippleColor=null
                holder.joinBtn.text="Missed"
                holder.joinBtn.icon=null
                holder.joinBtn.setTextColor(context.resources.getColor(R.color.not_joined))
                holder.joinBtn.setBackgroundColor(context.resources.getColor(R.color.not_joined_background))

            }
                else
            {
                holder.joinBtn.text="Click Here"
                holder.joinBtn.setIconResource(R.drawable.icon_url)
                holder.joinBtn.setIconTintResource(R.color.base_color)
                holder.joinBtn.setTextColor(context.resources.getColor(R.color.base_color))
                holder.joinBtn.setBackgroundColor(context.resources.getColor(R.color.join_background_color))
            }
        }

    }

    fun update(meetingList: List<Meeting>) {
        data=meetingList
        notifyDataSetChanged()
    }
}