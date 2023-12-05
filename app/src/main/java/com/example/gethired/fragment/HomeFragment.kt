package com.example.gethired.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.gethired.CommonFunction
import com.example.gethired.NotificationActivity
import com.example.gethired.R
import com.example.gethired.Token.TokenManager
import com.example.gethired.ViewModel.MeetingViewModel
import com.example.gethired.ViewModel.UserProfileViewModel
import com.example.gethired.adapter.MeetingAdapter
import com.example.gethired.adapter.SearchedMeetingCandidateAdapter
import com.example.gethired.entities.Meeting
import com.example.gethired.entities.UserDto
import com.example.gethired.entities.UserProfile
import com.example.gethired.factory.MeetingViewModelFactory
import com.example.gethired.factory.UserProfileViewModelFactory
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import java.time.LocalTime
import java.time.ZoneId

class HomeFragment : Fragment() {

    private lateinit var appBarLayout: AppBarLayout
    private lateinit var appBar: MaterialToolbar
    private lateinit var title:TextView

    private lateinit var upcomingMeetingBtn:TextView
    private lateinit var pastMeetingBtn:TextView
    private lateinit var meetingRecyclerView: RecyclerView
    private lateinit var meetingLoading: LottieAnimationView
    private lateinit var noMeetingText:TextView

    private lateinit var notificationIcon:ImageView

    private var upcomingMeetingList:MutableList<Meeting> = mutableListOf()
    private var pastMeetingList:MutableList<Meeting> = mutableListOf()
    private lateinit var meetingAdapter: MeetingAdapter

//    private lateinit var candidateName: EditText

    private lateinit var tokenManager:TokenManager
    private lateinit var meetingViewModel:MeetingViewModel
    private lateinit var userProfileViewModel: UserProfileViewModel

    private var candidateProfileList: MutableList<UserProfile> = mutableListOf()
    private lateinit var searchedMeetingCandidateAdapter: SearchedMeetingCandidateAdapter

    private var user: UserDto? = null

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        appBarLayout = rootView.findViewById(R.id.appbar_container)
        appBar=rootView.findViewById(R.id.appBar)
        title=rootView.findViewById(R.id.title)

        upcomingMeetingBtn=rootView.findViewById(R.id.upcomingTab)
        pastMeetingBtn=rootView.findViewById(R.id.pastTab)
        notificationIcon=rootView.findViewById(R.id.notification_icon)

        meetingRecyclerView=rootView.findViewById(R.id.meetingRecyclerView)
        meetingLoading=rootView.findViewById(R.id.meetingLoadingBar)
        noMeetingText=rootView.findViewById(R.id.noMeeting)

        tokenManager= TokenManager(requireContext())
        meetingViewModel=
            ViewModelProvider(this, MeetingViewModelFactory(tokenManager))[MeetingViewModel::class.java]
        userProfileViewModel=
            ViewModelProvider(this, UserProfileViewModelFactory(tokenManager))[UserProfileViewModel::class.java]

        user = CommonFunction.SharedPrefsUtil.fetchUserResponseFromSharedPreferences()

        val nestedScrollView = rootView.findViewById<NestedScrollView>(R.id.nestedScrollView)
        var isHidden = false
        nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY && !isHidden) {
                // Scrolling down, animate AppBarLayout back to its position
//                appBarLayout.visibility = View.GONE
                appBar.visibility=View.GONE
                appBarLayout.animate().translationY(0f).setDuration(300).start()
//                appBar.animate().translationY(0f).setDuration(300).start()
                isHidden = true
            }
            else if (scrollY < oldScrollY&& isHidden) {
                // Scrolling up, hide appbar
                appBarLayout.animate().translationY(-appBarLayout.height.toFloat()).setDuration(300).start()
//                appBar.animate().translationY(-appBar.height.toFloat()).setDuration(300).start()
                isHidden = false
//                appBarLayout.visibility =View.VISIBLE
                appBar.visibility=View.VISIBLE
            }
        }

        val username=user!!.name.split(" ")[0]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val time=LocalTime.now()
            if(time.hour in 4..11){
                title.text="Good morning, ${username}"
            }else if(time.hour in 12 downTo 5){
                title.text="Good afternoon, ${username}"
            }else{
                title.text="Good evening, ${username}"
            }
        }else{
            title.text="Hello, ${username}"
        }

        notificationIcon.setOnClickListener {
            val intent= Intent(requireActivity(),NotificationActivity::class.java)
            startActivity(intent)
        }

        meetingAdapter=MeetingAdapter(upcomingMeetingList,requireContext())
        meetingRecyclerView.adapter=meetingAdapter
        meetingRecyclerView.layoutManager= LinearLayoutManager(requireContext())

        meetingLoading.visibility=View.VISIBLE
        meetingRecyclerView.visibility=View.GONE
        noMeetingText.visibility=View.GONE
        upcomingMeeting()
        upcomingMeetingBtn.setOnClickListener {
            meetingLoading.visibility=View.VISIBLE
            meetingRecyclerView.visibility=View.GONE
            noMeetingText.visibility=View.GONE

            pastMeetingBtn.background = resources.getDrawable(R.drawable.home_meeting_fragment_background_inactive)
            upcomingMeetingBtn.background = resources.getDrawable(R.drawable.home_meeting_fragment_background_active)
            pastMeetingBtn.setTextColor(resources.getColor(R.color.black))
            upcomingMeetingBtn.setTextColor(resources.getColor(R.color.white))

            upcomingMeeting()



        }
        pastMeetingBtn.setOnClickListener {
            meetingLoading.visibility=View.VISIBLE
            meetingRecyclerView.visibility=View.GONE
            noMeetingText.visibility=View.GONE

            pastMeetingBtn.background = resources.getDrawable(R.drawable.home_meeting_fragment_background_active)
            upcomingMeetingBtn.background = resources.getDrawable(R.drawable.home_meeting_fragment_background_inactive)
            pastMeetingBtn.setTextColor(resources.getColor(R.color.white))
            upcomingMeetingBtn.setTextColor(resources.getColor(R.color.black))

            meetingViewModel.getAllPastMeeting(user!!.username).observe(requireActivity()){
                meetingLoading.visibility=View.GONE
                meetingRecyclerView.visibility=View.VISIBLE
                pastMeetingList.clear()
                pastMeetingList.addAll(it)
                if (it.isEmpty()){
                    noMeetingText.visibility=View.VISIBLE
                    Toast.makeText(requireContext(),"No past meetings",Toast.LENGTH_SHORT).show()
                }
                meetingAdapter.update(pastMeetingList)

            }
        }

        return rootView
    }

    private fun upcomingMeeting(){
        meetingViewModel.getAllMeeting(user!!.username).observe(requireActivity()){
            meetingLoading.visibility=View.GONE
            meetingRecyclerView.visibility=View.VISIBLE
            meetingLoading.visibility=View.GONE
            meetingRecyclerView.visibility=View.VISIBLE
            upcomingMeetingList.clear()
            upcomingMeetingList.addAll(it)
            if (it.isEmpty()){
                noMeetingText.visibility=View.VISIBLE
                Toast.makeText(requireContext(),"No upcoming meetings",Toast.LENGTH_SHORT).show()
            }
            meetingAdapter.update(upcomingMeetingList)

        }
    }


}