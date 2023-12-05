package com.example.gethired.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.gethired.Callback.SearchUserProfileCallback
import com.example.gethired.Callback.UsernameAvailabilityCallback
import com.example.gethired.CommonFunction
import com.example.gethired.ProfileActivity
import com.example.gethired.R
import com.example.gethired.Token.TokenManager
import com.example.gethired.ViewModel.NotificationViewModel
import com.example.gethired.ViewModel.RecentSearchViewModel
import com.example.gethired.ViewModel.UserProfileViewModel
import com.example.gethired.adapter.RecentSearchAdapter
import com.example.gethired.adapter.UserProfileAdapter
import com.example.gethired.entities.*
import com.example.gethired.factory.NotificationViewModelFactory
import com.example.gethired.factory.RecentSearchViewModelFactory
import com.example.gethired.factory.UserProfileViewModelFactory
import com.example.gethired.factory.UserViewModelFactory
import com.google.android.material.textfield.TextInputEditText
 const val SEARCH_STATE_KEY = "searchState"
 const val CURRENT_FRAGMENT_KEY=1
class SearchFragment : Fragment() {

    private lateinit var searchView:TextInputEditText
    private lateinit var recentSearchRecyclerView: RecyclerView
    private lateinit var userProfileRecyclerView: RecyclerView
    private lateinit var recentSearchContainer:LinearLayout
    private lateinit var recentSearchClearAll:TextView
    private lateinit var searchLoadingAnimation:LottieAnimationView

    private lateinit var recentSearchAdapter: RecentSearchAdapter
    private lateinit var userProfileAdapter:UserProfileAdapter

    private lateinit var resultNotFound:LinearLayout

    private var userProfileList: MutableList<UserProfile> = mutableListOf()
    private var recentSearchList:MutableList<RecentSearch> = mutableListOf()

    private lateinit var tokenManager: TokenManager
    private lateinit var userProfileViewModel:UserProfileViewModel
    private lateinit var notificationViewModel: NotificationViewModel
    private lateinit var recentSearchViewModel:RecentSearchViewModel

    private var currentPage = 0

    private var senderUser:UserDto?=null

    private var isRecentSearchFetched:Boolean =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SearchFragment", "onCreate")
        retainInstance = true
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        // Inflate the layout for this fragment
//        savedInstanceState?.let {
//            restoreState(it)
//        }
        tokenManager = TokenManager(requireContext())

        senderUser=CommonFunction.SharedPrefsUtil.fetchUserResponseFromSharedPreferences()

        userProfileViewModel=ViewModelProvider(this, UserProfileViewModelFactory(tokenManager))[UserProfileViewModel::class.java]
        notificationViewModel=ViewModelProvider(this,NotificationViewModelFactory(tokenManager))[NotificationViewModel::class.java]
        recentSearchViewModel=ViewModelProvider(this,RecentSearchViewModelFactory(tokenManager))[RecentSearchViewModel::class.java]




        recentSearchAdapter= RecentSearchAdapter(recentSearchList)

        userProfileAdapter = UserProfileAdapter(ArrayList(), senderUser!!.isRecuriter==1)

        recentSearchRecyclerView=rootView.findViewById(R.id.search_fragment_recent_searches)
        userProfileRecyclerView=rootView.findViewById(R.id.search_fragment_recyclerView)
        searchView=rootView.findViewById(R.id.search_bar_edittext)
        recentSearchContainer=rootView.findViewById(R.id.search_fragment_recent_searches_container)
        recentSearchClearAll=rootView.findViewById(R.id.search_fragment_recent_searches_clear_all)
        searchLoadingAnimation=rootView.findViewById(R.id.search_fragment_loading_animation)


        searchView.addTextChangedListener (searchQueryTextWatcher)

        recentSearchRecyclerView.adapter=recentSearchAdapter
        recentSearchRecyclerView.layoutManager=LinearLayoutManager(requireContext())

        resultNotFound=rootView.findViewById(R.id.search_user_result_not_found)

        userProfileRecyclerView.adapter=userProfileAdapter
        userProfileRecyclerView.layoutManager=LinearLayoutManager(requireContext())




        if(!isRecentSearchFetched){
            recentSearchViewModel.getAllRecentSearches(
                senderUser!!.id,
                { recentSearches -> // onSuccess
                    // Handle list of recent searches
                    if(recentSearches.isNotEmpty()){
                        recentSearchContainer.visibility=View.VISIBLE
                    }
                    isRecentSearchFetched=true
                    recentSearchList.clear()
                    recentSearchList.addAll(recentSearches)
                    recentSearchAdapter.updateList(recentSearchList)
                },
                { throwable -> // onFailure
                    // Handle failure with throwable
                    Toast.makeText(requireContext(),throwable.toString(),Toast.LENGTH_SHORT).show()
                }
            )
        }

        recentSearchAdapter.setOnDeleteIconClickListener(object : RecentSearchAdapter.OnDeleteIconClickListener {
            override fun onDeleteIconClick(position: Int) {

                val recentSearch = recentSearchList[position]
                recentSearchList.removeAt(position)

                if(recentSearchList.size==0){
                    recentSearchContainer.visibility=View.GONE
                }
                recentSearchAdapter.notifyItemRemoved(position)
                recentSearchViewModel.deleteRecentSearch(recentSearch.id,
                    onSuccess = {

                    },
                    onFailure = { throwable ->
                    }
                )
            }
        })

        recentSearchAdapter.setOnItemClickListener(object :RecentSearchAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val item=recentSearchList[position]
                searchView.setText(item.searchedText)
                recentSearchAdapter.notifyItemRemoved(position)
                recentSearchContainer.visibility=View.GONE
            }

        })

        userProfileAdapter.setOnItemClickListener(object :UserProfileAdapter.OnItemClickListener{
            override fun onItemClick(position: Int, userProfile: UserProfile) {

                sendNotification(userProfile)
                val intent=Intent(requireContext(),ProfileActivity::class.java)
                intent.putExtra("user", userProfile.user)
                startActivity(intent)
            }


        })

        recentSearchClearAll.setOnClickListener {
            deleteAllRecentSearches()
        }

        userProfileAdapter.setOnLoadMoreListener(object :UserProfileAdapter.OnLoadMoreListener{
            override fun onLoadMore() {
                if(userProfileAdapter.getIsLoading()){
                    loadMoreData()
                }

            }

        })


        return rootView
    }


    private val searchQueryTextWatcher = object : TextWatcher {
        private val DELAY = 1000L // Delay in milliseconds
        private val handler = Handler(Looper.getMainLooper())

        private var lastQuery = ""
        private var isLoading = false // Variable to track loading state

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            handler.removeCallbacksAndMessages(null)
            lastQuery = s.toString()

            // Delay the API call
            handler.postDelayed({
                // Check if the user has not typed anything new
                if (lastQuery == s.toString()) {
                    val searchQuery = s.toString()
                    if (searchQuery.isEmpty()) {
                        recentSearchContainer.visibility = View.VISIBLE
                        userProfileRecyclerView.visibility = View.GONE
                        resultNotFound.visibility = View.GONE
                    } else {
                        recentSearchContainer.visibility = View.GONE
                        userProfileRecyclerView.visibility = View.VISIBLE

                        // Check if loading is not in progress
                        if (!isLoading) {
                            searchLoadingAnimation.visibility = View.VISIBLE
                        }

                        val recentSearch = RecentSearch(0, searchQuery, senderUser!!.id)
                        val existingSearch = recentSearchList.find { it.searchedText == recentSearch.searchedText }
                        if (existingSearch == null) {
                            recentSearchList.add(0, recentSearch) // Add to the top of the list
                            recentSearchAdapter.notifyItemInserted(0)
                            // Update the adapter here or use notifyDataSetChanged() if needed
                            recentSearchViewModel.addRecentSearch(
                                recentSearch,
                                { /* onSuccess */ },
                                { throwable ->
                                    // Handle failure with throwable
                                    Toast.makeText(
                                        requireContext(),
                                        throwable.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }


                        userProfileViewModel.getAllCandidateProfile(
                            searchQuery,"",
                            currentPage,
                            8,
                            "id",
                            "ASC"
                        ).observe(requireActivity()) {
                            // Search completed, hide loading animation
                            searchLoadingAnimation.visibility = View.GONE
                            isLoading = false

                            if (it.isEmpty()) {
                                resultNotFound.visibility = View.VISIBLE
                            } else {
                                resultNotFound.visibility = View.GONE
                                userProfileAdapter.setLoading(true)
                            }
                            userProfileList.clear()
                            userProfileList.addAll(it)
                            userProfileAdapter.updateList(userProfileList, true)

                        }
                    }
                }
            }, DELAY)
        }

        override fun afterTextChanged(s: Editable?) {}
    }


    private fun deleteAllRecentSearches() {
        recentSearchContainer.visibility=View.GONE
        recentSearchList.clear()
        recentSearchAdapter.updateList(mutableListOf())
        recentSearchViewModel.deleteAllRecentSearches(senderUser!!.id,
            onSuccess = {

            },
            onFailure = { throwable ->
//                Toast.makeText(requireContext(),"Something went wrong!",Toast.LENGTH_SHORT).show()
            }
        )

    }

    private fun sendNotification(userProfile: UserProfile) {

        val request = NotificationRequest(senderUser!!.username,"Profile view","Your Profile is viewed by someone",userProfile.user.username,"profile")

        if(senderUser?.username?.equals(userProfile.user.username) == false){

            notificationViewModel.sendNotification(request)
        }


    }

    private fun loadMoreData() {

            currentPage++;
        userProfileAdapter.setLoading(false)

            // Fetch more results with the same search query and location
            userProfileViewModel.getAllCandidateProfile(
                searchView.text.toString(),
                "",
                currentPage,
                8,
                "id",
                "ASC"
            ).observe(requireActivity()) {
                // Update recycler view
                userProfileAdapter.updateList(it, false)

                // Set loading flag to false
                if(it.size==8) {
                    userProfileAdapter.setLoading(true)
                }else{
                    userProfileAdapter.setLoading(false)
                }
            }

    }
//
//    override fun onPause() {
//        super.onPause()
//        onSaveInstanceState()
//    }
//    fun onSaveInstanceState(): Bundle {
//        val bundle = Bundle()
//        bundle.putString("searchQuery", searchView.text.toString())
//        // Add other relevant data like pagination information
//        return bundle
//    }

//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        Log.d("SearchFragment", "onActivityCreated")
//    }
//
//    private fun restoreState(savedInstanceState: Bundle) {
//        val searchQuery = savedInstanceState.getString("searchQuery")
//        if (!searchQuery.isNullOrEmpty()) {
//            searchView.setText(searchQuery) // Update UI with saved query
//            // Reload data or perform actions based on restored state
//
//
//// ...
//        }
//    }

}



