package com.example.gethired

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.gethired.Callback.MeetingCallback
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
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class HrDashBoardFragment : Fragment() {
    private lateinit var title:TextView

    private lateinit var notificationIcon: ImageView
    private lateinit var scheduleMeeting:LinearLayout

    private lateinit var upcomingMeetings:TextView
    private lateinit var pastMeetings:TextView
    private lateinit var meetingRecyclerView:RecyclerView
    private lateinit var meetingLoading: LottieAnimationView
    private lateinit var noMeetingText:TextView

    private lateinit var candidateName:EditText

    private lateinit var tokenManager:TokenManager
    private lateinit var meetingViewModel:MeetingViewModel
    private lateinit var userProfileViewModel: UserProfileViewModel

    private var upcomingMeetingList:MutableList<Meeting> = mutableListOf()
    private var pastMeetingList:MutableList<Meeting> = mutableListOf()
    private lateinit var meetingAdapter: MeetingAdapter



    private var candidateProfileList: MutableList<UserProfile> = mutableListOf()
    private lateinit var searchedMeetingCandidateAdapter:SearchedMeetingCandidateAdapter

    private var user: UserDto? = null

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_hr_dash_board, container, false)

        user = CommonFunction.SharedPrefsUtil.fetchUserResponseFromSharedPreferences()
        tokenManager = TokenManager(requireContext())
        meetingViewModel = ViewModelProvider(
            this,
            MeetingViewModelFactory(tokenManager)
        )[MeetingViewModel::class.java]
        userProfileViewModel = ViewModelProvider(
            this,
            UserProfileViewModelFactory(tokenManager)
        )[UserProfileViewModel::class.java]



        title = rootView.findViewById(R.id.title)
        notificationIcon = rootView.findViewById(R.id.notification_icon)
        scheduleMeeting = rootView.findViewById(R.id.create_new_meeting_layout)

        upcomingMeetings = rootView.findViewById(R.id.upcomingMeetingTab)
        pastMeetings = rootView.findViewById(R.id.pastMeetingTab)
        meetingRecyclerView = rootView.findViewById(R.id.meetingRecyclerView)
        meetingLoading = rootView.findViewById(R.id.meetingLoadingBar)
        noMeetingText = rootView.findViewById(R.id.noMeeting)


        meetingAdapter = MeetingAdapter(upcomingMeetingList, requireContext())
        meetingRecyclerView.adapter = meetingAdapter
        meetingRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        if (user!!.name.isNotEmpty()) {
            title.text = "Hello, ${user!!.name.split(" ")[0]}"
        }
        notificationIcon.setOnClickListener {
            val intent = Intent(requireActivity(), NotificationActivity::class.java)
            startActivity(intent)
        }

        meetingLoading.visibility = View.VISIBLE
        meetingRecyclerView.visibility = View.GONE
        noMeetingText.visibility = View.GONE
        meetingViewModel.getAllMeeting(user!!.username).observe(requireActivity()) {
            meetingLoading.visibility = View.GONE
            meetingRecyclerView.visibility = View.VISIBLE
            upcomingMeetingList.clear()
            upcomingMeetingList.addAll(it)
            if (it.isEmpty()) {
                noMeetingText.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "No upcoming meetings", Toast.LENGTH_SHORT).show()
            }
            meetingAdapter.update(upcomingMeetingList)

        }

        upcomingMeetings.setOnClickListener {

            meetingLoading.visibility = View.VISIBLE
            meetingRecyclerView.visibility = View.GONE
            noMeetingText.visibility = View.GONE
            pastMeetings.background =
                resources.getDrawable(R.drawable.home_meeting_fragment_background_inactive)
            upcomingMeetings.background =
                resources.getDrawable(R.drawable.home_meeting_fragment_background_active)
            pastMeetings.setTextColor(resources.getColor(R.color.black))
            upcomingMeetings.setTextColor(resources.getColor(R.color.white))

            meetingViewModel.getAllMeeting(user!!.username).observe(requireActivity()) {
                meetingLoading.visibility = View.GONE
                meetingRecyclerView.visibility = View.VISIBLE
                upcomingMeetingList.clear()
                upcomingMeetingList.addAll(it)

                if (it.isEmpty()) {
                    noMeetingText.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "No upcoming meetings", Toast.LENGTH_SHORT)
                        .show()
                }
                meetingAdapter.update(upcomingMeetingList)

            }

        }
        pastMeetings.setOnClickListener {
            meetingLoading.visibility = View.VISIBLE
            meetingRecyclerView.visibility = View.GONE
            noMeetingText.visibility = View.GONE
            pastMeetings.background =
                resources.getDrawable(R.drawable.home_meeting_fragment_background_active)
            upcomingMeetings.background =
                resources.getDrawable(R.drawable.home_meeting_fragment_background_inactive)
            pastMeetings.setTextColor(resources.getColor(R.color.white))
            upcomingMeetings.setTextColor(resources.getColor(R.color.black))

            meetingViewModel.getAllPastMeeting(user!!.username).observe(requireActivity()) {
                meetingLoading.visibility = View.GONE
                meetingRecyclerView.visibility = View.VISIBLE
                pastMeetingList.clear()
                pastMeetingList.addAll(it)
                if (it.isEmpty()) {
                    noMeetingText.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "No past meetings", Toast.LENGTH_SHORT).show()
                }
                meetingAdapter.update(pastMeetingList)

            }
        }

        scheduleMeeting.setOnClickListener {

            val inflater = LayoutInflater.from(requireContext())
            val popupView = inflater.inflate(R.layout.schedule_meeting_popup_layout, null)
            val popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            // Set background drawable
            popupWindow.animationStyle = R.style.PopupAnimation
// Set outside touch-ability
            popupWindow.isOutsideTouchable = true
// Set focusability
            popupWindow.isFocusable = true
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)
            var isSearched = false;
            var isCandidateValid = false;
            var selectedPosition = -1

            val backButton: ImageView = popupView.findViewById(R.id.back_button)
            candidateName =
                popupView.findViewById(R.id.schedule_meeting_popup_candidate_name_editText)
            val meetingLink: TextInputEditText =
                popupView.findViewById(R.id.schedule_meeting_popup_meeting_link_editText)
            val candidateSearchLoadingAnimation =
                popupView.findViewById<LottieAnimationView>(R.id.meeting_candidate_search_loading_animation)
            val candidateSearchNoResult: TextView =
                popupView.findViewById(R.id.meeting_candidate_search_noResult)
            val date: TextInputEditText =
                popupView.findViewById(R.id.schedule_meeting_popup_meeting_date)
            val time: TextInputEditText =
                popupView.findViewById(R.id.schedule_meeting_popup_meeting_time)
            val submitBtn: MaterialButton =
                popupView.findViewById(R.id.schedule_meeting_popup_save_button)
            val submitLoadingBar: LottieAnimationView =
                popupView.findViewById(R.id.submit_loadingBar)

            val bodyContainer: LinearLayout =
                popupView.findViewById(R.id.schedule_meeting_popup_body_container)
            val searchedCandidateRecyclerView =
                popupView.findViewById<RecyclerView>(R.id.schedule_meeting_popup_candidate_search_recyclerview)

            searchedMeetingCandidateAdapter =
                SearchedMeetingCandidateAdapter(candidateProfileList)
            searchedCandidateRecyclerView.adapter = searchedMeetingCandidateAdapter
            searchedCandidateRecyclerView.layoutManager = LinearLayoutManager(requireContext())

            backButton.setOnClickListener {
                popupWindow.dismiss()
            }
            if (!isSearched) {
                // Modify the TextWatcher to update RecyclerView visibility
                candidateName.addTextChangedListener(object : TextWatcher {
                    private val DELAY = 1000L // Delay in milliseconds
                    private val handler = Handler(Looper.getMainLooper())
                    private var lastQuery = ""

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        candidateSearchLoadingAnimation.visibility = View.VISIBLE
                        isSearched = false
                        lastQuery = s.toString().trim()

                        if (lastQuery == s.toString().trim()) {
                            if (s.toString().isEmpty() || candidateName.text.isEmpty()) {
                                searchedCandidateRecyclerView.visibility = View.GONE
                                bodyContainer.visibility = View.VISIBLE
                                candidateSearchNoResult.visibility = View.GONE
                            } else {
                                if (selectedPosition == -1) {
                                    // No item selected, show the RecyclerView
                                    searchedCandidateRecyclerView.visibility = View.VISIBLE
                                    bodyContainer.visibility = View.GONE
                                    candidateSearchNoResult.visibility = View.GONE

                                    userProfileViewModel.getAllCandidateProfile(
                                        s.toString().trim(),"",
                                        0,
                                        100,
                                        "id",
                                        "ASC"
                                    )
                                        .observe(requireActivity()) {
                                            candidateProfileList.clear()
                                            candidateSearchLoadingAnimation.visibility = View.GONE
                                            if (it.isEmpty()) {
                                                candidateSearchNoResult.visibility = View.VISIBLE
                                                searchedCandidateRecyclerView.visibility = View.GONE
                                                isCandidateValid = false
                                            } else {
                                                candidateSearchNoResult.visibility = View.GONE
                                                searchedCandidateRecyclerView.visibility = View.VISIBLE
                                                bodyContainer.visibility = View.GONE
                                            }
                                            candidateProfileList.clear()
                                            candidateProfileList.addAll(it)
                                            searchedMeetingCandidateAdapter.notifyDataSetChanged()
                                        }
                                } else {
                                    // An item is selected, hide the RecyclerView
                                    searchedCandidateRecyclerView.visibility = View.GONE
                                    bodyContainer.visibility = View.VISIBLE
                                    candidateSearchNoResult.visibility = View.GONE
                                }
                            }
                        } else {
                            candidateSearchLoadingAnimation.visibility = View.GONE
                            searchedCandidateRecyclerView.visibility = View.GONE
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })

            }
            if(candidateName.text.isEmpty()){
                candidateProfileList.clear()
                searchedMeetingCandidateAdapter.notifyDataSetChanged()
                searchedCandidateRecyclerView.visibility=View.GONE
                bodyContainer.visibility = View.VISIBLE
                candidateSearchNoResult.visibility=View.GONE
            }



// ...

            searchedMeetingCandidateAdapter.setOnItemClickListener(object :
                SearchedMeetingCandidateAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    selectedPosition = position

                    // Set the candidateName EditText with the selected profile's username
                    candidateName.setText(candidateProfileList[position].user.username)

                    // Hide the RecyclerView
                    searchedCandidateRecyclerView.visibility = View.GONE
                    candidateSearchNoResult.visibility = View.GONE
                    bodyContainer.visibility = View.VISIBLE

                    // Clear focus from the candidateName EditText
                    candidateName.clearFocus()

                    // Update the flag
                    isSearched = true
                    isCandidateValid = true
                }
            })


            submitBtn.setOnClickListener {
                if (isCandidateValid) {
                    submitLoadingBar.visibility = View.VISIBLE
                    submitBtn.visibility = View.GONE
                    if (!isValidUrl(meetingLink.text.toString())) {
                        submitLoadingBar.visibility = View.GONE
                        meetingLink.setTextColor(resources.getColor(R.color.red))
                    } else if (!isValidUrl(meetingLink.text.toString()) || candidateName.text.toString()
                            .isEmpty() || date.text.toString().isEmpty() || time.text.toString()
                            .isEmpty()
                    ) {
                        Toast.makeText(
                            requireContext(),
                            "Please fill required fields",
                            Toast.LENGTH_SHORT
                        ).show()
                        submitLoadingBar.visibility = View.GONE
                        submitBtn.visibility = View.VISIBLE
                    } else {
                        val meeting = Meeting(
                            0,
                            candidateName.text.toString(),
                            user!!.username,
                            date.text.toString(),
                            time.text.toString(),
                            meetingLink.text.toString(),
                            false
                        )
                        meetingViewModel.createMeeting(meeting, object : MeetingCallback {
                            override fun onMeetingResponse(meeting: Meeting) {
                                submitLoadingBar.visibility = View.GONE
                                submitBtn.visibility = View.VISIBLE
                                noMeetingText.visibility = View.GONE

                                upcomingMeetingList.add(upcomingMeetingList.size, meeting)
                                meetingAdapter.notifyItemInserted(upcomingMeetingList.size);
                                popupWindow.dismiss()
                            }

                            override fun onMeetingError(message: String) {
                                submitLoadingBar.visibility = View.GONE
                                submitBtn.visibility = View.VISIBLE
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                                popupWindow.dismiss()
                            }

                        })
                    }
                }
                else{
                    Toast.makeText(requireContext(), "Enter valid user",Toast.LENGTH_SHORT).show()
                }

            }
        }


        return rootView
    }
    private fun isValidUrl(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }


}