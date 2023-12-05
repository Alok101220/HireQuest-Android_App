package com.example.gethired.adapter

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.gethired.R
import com.example.gethired.entities.Chat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.abs
@RequiresApi(Build.VERSION_CODES.O)
class MessageAdapter(
    private val messages: List<Chat>,
    private val currentUser: String,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var handler: Handler? = null

    fun startUpdatingTimestamps() {
        handler = Handler(Looper.getMainLooper())
        handler?.post(updateTimestamps)
    }

    fun stopUpdatingTimestamps() {
        handler?.removeCallbacksAndMessages(null)
    }

    private val updateTimestamps = object : Runnable {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun run() {
            val currentTimeStamp = LocalDateTime.now()
            val currHour = currentTimeStamp.hour
            val currMin = currentTimeStamp.minute

            for (i in messages.indices) {
                val message = messages[i]
                val chatTimeStamp = LocalDateTime.parse(
                    message.timestamp.subSequence(0,23),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
                )
                val messageHour = chatTimeStamp.hour
                val messageMin = chatTimeStamp.minute

                val timeAgo = if (abs(currHour - messageHour) < 1) {
                    if (abs(currMin - messageMin) <= 1) {
                        "just now"
                    } else if (abs(messageMin - currMin) < 10) {
                        "0${abs(messageMin - currMin)}"
                    } else {
                        "${abs(messageMin - currMin)} min ago"
                    }
                } else {
                    if (messageHour < 12) {
                        "${messageHour}:${messageMin} am"
                    } else {
                        "${messageHour}:${messageMin} pm"
                    }
                }

                val holder =
                    recyclerView.findViewHolderForAdapterPosition(i) as? RecyclerView.ViewHolder
                holder?.let {
                    if (it is MessageViewHolder) {
                        it.messageTime.text = timeAgo
                    }
                }
            }

            handler?.postDelayed(this, 60 * 1000)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == MessageType.SENDER) {
                val view=inflater.inflate(R.layout.sender_message_item, parent, false)
            return MessageViewHolder(view)
            }
        else if (viewType == MessageType.RECEIVER){
            val view=inflater.inflate(R.layout.receiver_message_item, parent, false)
            return MessageViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.chat_date_header, parent, false)
            return DateHeaderViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        val chatTimeStamp = LocalDateTime.parse(message.timestamp.subSequence(0,23), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))

        when (getItemViewType(position)) {
            MessageType.SENDER, MessageType.RECEIVER -> {
                val messageHolder = holder as MessageViewHolder
                messageHolder.messageContent.text = message.content
                messageHolder.messageTime.text = getTimeAgo(chatTimeStamp)
            }
            MessageType.VIEW_TYPE_DATE_HEADER -> {
                val dateHeaderHolder = holder as DateHeaderViewHolder
                dateHeaderHolder.dateHeader.text = formatDateHeader(chatTimeStamp)

                // Show the message after date header
                val nextPosition = position + 1
                if (nextPosition < messages.size &&
                    getItemViewType(nextPosition) != MessageType.VIEW_TYPE_DATE_HEADER
                ) {
                    val nextMessage = messages[nextPosition]
                    val nextTimeStamp = LocalDateTime.parse(nextMessage.timestamp.subSequence(0,23), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))

                    val nextMessageHolder = recyclerView.findViewHolderForAdapterPosition(nextPosition) as? MessageViewHolder
                    nextMessageHolder?.let {
                        it.messageContent.text = nextMessage.content
                        it.messageTime.text = getTimeAgo(nextTimeStamp)
                    }
                }
            }
        }

        // Check if this is the last item in the list and it is a message
        if (position == messages.size - 1 && getItemViewType(position) != MessageType.VIEW_TYPE_DATE_HEADER) {
            val lastMessageTimeStamp = LocalDateTime.parse(messages.last().timestamp.subSequence(0,23), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))
            if (areDatesEqual(chatTimeStamp, lastMessageTimeStamp)) {
                // This message is from the same day as the last message, so show it below the date header
                val dateHeaderHolder = recyclerView.findViewHolderForAdapterPosition(position - 1) as? DateHeaderViewHolder
                dateHeaderHolder?.let {
                    it.dateHeader.visibility = View.GONE
                }
            }
        }
    }



    override fun getItemCount() = messages.size

    override fun getItemViewType(position: Int): Int {
        if (isDateHeaderNeeded(position))
            return MessageType.VIEW_TYPE_DATE_HEADER
        else {
            val message = messages[position]
            return if (message.senderId.toString() == currentUser) {
                MessageType.SENDER
            } else {
                MessageType.RECEIVER
            }
        }
    }


    private fun isDateHeaderNeeded(position: Int): Boolean {
        val currentMessage = messages[position]
        val currentTimeStamp = LocalDateTime.parse(currentMessage.timestamp.subSequence(0,23), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))

        val previousMessage: Chat = (if (position > 0) messages[position - 1] else null) ?: return true
        val previousMessageTimeStamp = LocalDateTime.parse(previousMessage.timestamp.subSequence(0,23), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))

        return !areDatesEqual(currentTimeStamp, previousMessageTimeStamp)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun areDatesEqual(date1: LocalDateTime, date2: LocalDateTime): Boolean {
        return date1.dayOfYear == date2.dayOfYear && date1.year == date2.year
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDateHeader(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
        return date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTimeAgo(date: LocalDateTime): String {
        val currentTime = LocalDateTime.now()
        val minutesAgo = currentTime.minute - date.minute
        val hoursAgo = currentTime.hour - date.hour

        return when {
            hoursAgo == 0 && minutesAgo < 2 -> "just now"
            hoursAgo == 0 -> "$minutesAgo min ago"
            else -> date.format(DateTimeFormatter.ofPattern("hh:mm a"))
        }
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageContent: TextView = itemView.findViewById(R.id.message_text)
        val messageTime: TextView = itemView.findViewById(R.id.message_timing)
    }

    inner class DateHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateHeader: TextView = itemView.findViewById(R.id.date_header_text)
    }

    object MessageType {
        const val SENDER = 0
        const val RECEIVER = 1
        const val VIEW_TYPE_DATE_HEADER=2
    }
}

