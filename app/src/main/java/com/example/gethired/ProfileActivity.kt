package com.example.gethired

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.gethired.entities.User
import com.example.gethired.entities.UserDto
import com.example.gethired.fragment.UserProfileFragment


class ProfileActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val searchedUser = intent.getSerializableExtra("user") as? User
        if (searchedUser != null) {
            // Now 'user' contains the User object sent from the first activity

            val bundle = Bundle()
            bundle.putSerializable("userInfo", convertUserToUserDto(searchedUser))
            val fragment= UserProfileFragment()
            fragment.arguments=bundle
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.user_profile_fragment, fragment)
            fragmentTransaction.commit()
        } else {
            // Handle case where 'user' is null (e.g., if there was an issue with the intent)
            val bundle = Bundle()
            bundle.putSerializable("userInfo", CommonFunction.SharedPrefsUtil.fetchUserResponseFromSharedPreferences())
            val fragment= UserProfileFragment()
            fragment.arguments=bundle
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.user_profile_fragment, fragment)
            fragmentTransaction.commit()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()

        // Navigate back to the previous activity
        finish()
    }

    fun convertUserToUserDto(user: User): UserDto {
        return UserDto(
            id = user.id,
            birthdate = user.birthdate,
            currentOccupation = user.currentOccupation,
            email = user.email,
            headline = user.headline,
            name = user.name,
            phone = user.phone,
            status = user.status,
            username = user.username,
            isRecuriter = null, // Set this value based on your logic
            gender = null // Set this value based on your logic
        )
    }

}