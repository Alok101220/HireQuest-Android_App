package com.example.gethired.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gethired.R
import com.example.gethired.entities.Notification

class NotificationAdapter(private var data: List<Notification>, private val context: Context) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){


    interface OnEditIconClickListener {
        fun onEditIconClick(position: Int)
    }

    private var editIconClickListener: OnEditIconClickListener? = null

    fun setOnEditIconClickListener(listener: OnEditIconClickListener) {
        editIconClickListener = listener
    }

    class ViewHolder(itemView: View, private val editIconClickListener: OnEditIconClickListener?):RecyclerView.ViewHolder(itemView) {

        val editIcon: ImageView = itemView.findViewById<ImageView>(R.id.notification_setting_icon)
        val isOpen = itemView.findViewById<ImageView>(R.id.notification_isOpen_indicator_circle)
        val notificationHeader = itemView.findViewById<TextView>(R.id.notification_heading)

        init {
            editIcon.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    editIconClickListener?.onEditIconClick(position)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item_list, parent, false)

        // Pass the longClickListener instance to the ViewHolder
        return ViewHolder(view, editIconClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item=data[position]
        holder.notificationHeader.text="${item.senderUsername} just visited your profile"
        if(item.readStatus){
            holder.isOpen.visibility=View.GONE
        }else{
            holder.isOpen.visibility=View.VISIBLE
        }
    }

    fun updateData(notifications:List<Notification>){
        data=notifications
        notifyDataSetChanged()
    }
}