package com.example.gethired.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gethired.R
import com.example.gethired.entities.Pdf

class ResumeAdapter(private var data: List<Pdf>, private val isSameUser: Boolean):RecyclerView.Adapter<ResumeAdapter.ViewHolder>() {

    interface OnEditIconClickListener {
        fun onEditIconClick(position: Int)
    }

    private var editIconClickListener: OnEditIconClickListener? = null

    fun setOnEditIconClickListener(listener: OnEditIconClickListener) {
        editIconClickListener = listener
    }
    interface OnDownloadIconClickListener {
        fun onDownloadIconClick(position: Int)
    }

    private var downloadIconClickListener: OnDownloadIconClickListener? = null

    fun setOnDownloadIconClickListener(listener: OnDownloadIconClickListener) {
        downloadIconClickListener = listener
    }

    class ViewHolder(itemView: View, private val editIconClickListener: OnEditIconClickListener?,private val downloadIconClickListener: OnDownloadIconClickListener?):RecyclerView.ViewHolder(itemView) {

        val deleteIcon :ImageView=itemView.findViewById(R.id.resume_recyclerview_list_item_delete_icon)
        val fileName:TextView = itemView.findViewById(R.id.resume_recyclerview_list_item_file_name)
        val fileSize:TextView = itemView.findViewById(R.id.resume_recyclerview_list_item_file_size)
        val timeStamp:TextView = itemView.findViewById(R.id.resume_recyclerview_list_item_timeStamp)
        val download_icon:ImageView=itemView.findViewById(R.id.resume_recyclerview_list_item_download_icon)

        init {

            deleteIcon.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    editIconClickListener?.onEditIconClick(position)
                }
            }
            download_icon.setOnClickListener {
                val position=adapterPosition
                if(position!=RecyclerView.NO_POSITION){
                    downloadIconClickListener?.onDownloadIconClick(position)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.resume_recyclerview_list_item, parent, false)
        return ViewHolder(itemView, editIconClickListener,downloadIconClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.fileName.text=item.fileName
        holder.fileSize.text=item.fileSize
        holder.timeStamp.text=item.timeStamp
        if(!isSameUser){
            holder.deleteIcon.visibility=View.GONE
            holder.download_icon.visibility=View.VISIBLE
        }else{
            holder.deleteIcon.visibility=View.VISIBLE
            holder.download_icon.visibility=View.GONE
        }
    }

    fun updateData( newData:List<Pdf>){

        data=newData
        notifyDataSetChanged()
        Log.d("notify-resume","${data.size}")

    }
}