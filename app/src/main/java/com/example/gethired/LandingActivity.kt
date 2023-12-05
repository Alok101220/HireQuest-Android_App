package com.example.gethired

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.gethired.Callback.LoginCallback
import com.example.gethired.Callback.UpdateUserCallback
import com.example.gethired.Token.LoginResponse
import com.example.gethired.Token.TokenManager
import com.example.gethired.ViewModel.RegisterLoginViewModel
import com.example.gethired.ViewModel.UserViewModel
import com.example.gethired.adapter.ImagePagerAdapter
import com.example.gethired.entities.LoginDto
import com.example.gethired.entities.UserDto
import com.example.gethired.factory.UserViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import java.util.Random

class LandingActivity : AppCompatActivity() {

    private lateinit var logoImageView: ImageView
    private lateinit var viewPager: ViewPager
    private lateinit var loginButton: Button
    private lateinit var continueWithGoogleButton: Button
    private lateinit var signUpButton: Button
    private  lateinit var cardView:MaterialCardView
    private lateinit var tabLayout: TabLayout

    private lateinit  var sharedPref: SharedPreferences

    private lateinit var userViewModel: UserViewModel
    private lateinit var registerLoginViewModel: RegisterLoginViewModel
    private lateinit var tokenManager: TokenManager
    private var fcmToken: String?=""

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = R.string.web_client_id

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        tokenManager= TokenManager(this@LandingActivity)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        userViewModel = ViewModelProvider(this,UserViewModelFactory(tokenManager))[UserViewModel::class.java]
        registerLoginViewModel= ViewModelProvider(this)[RegisterLoginViewModel::class.java]
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                // Token retrieval succeeded
                fcmToken = token ?: ""
                // Now you can proceed with your registration logic here
            }
            .addOnFailureListener { exception ->
                // Token retrieval failed
                Log.e("FCM", "Fetching FCM registration token failed", exception)
                // Handle the error if token retrieval fails
            }



        tabLayout = findViewById(R.id.tabLayout)
        logoImageView = findViewById(R.id.logoImageView)
        viewPager = findViewById(R.id.viewPager)
        loginButton = findViewById(R.id.loginButton)
        continueWithGoogleButton = findViewById(R.id.continueWithGoogleButton)
        signUpButton = findViewById(R.id.signUpButton)
        cardView=findViewById(R.id.container)

        sharedPref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)

        logoTransition()


        loginButton.setOnClickListener {
            val intent= Intent(this@LandingActivity,LoginActivity::class.java)
            startActivity(intent)
        }

        signUpButton.setOnClickListener {
            val intent= Intent(this@LandingActivity,RegisterActivity::class.java)
            startActivity(intent)
        }

        continueWithGoogleButton.setOnClickListener {
//            signInWithGoogle()
        }




        val savedUsername = sharedPref.getString("username", "")
        val savedPassword = sharedPref.getString("password", "")


        if (!savedUsername.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
            // Perform automatic login
            if (checkLoginCredentials(savedUsername, savedPassword)) {
                // Login successful
                val intent= Intent(this@LandingActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Invalid saved credentials, prompt for manual login
//                Toast.makeText(this, "Please log in", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun logoTransition(){
        val logoAnimator = ObjectAnimator.ofFloat(logoImageView, "translationY", 600f)
        val headingAnimationSet = AnimatorSet()
        headingAnimationSet.playTogether(logoAnimator)
        headingAnimationSet.duration = 0
        headingAnimationSet.start()
        Handler().postDelayed({ animateLayoutTransition() }, Random().nextInt(1001) + 2000.toLong())

    }
    private fun animateLayoutTransition() {
        // Move the heading (app name and logo) to the top
        val logoAnimator = ObjectAnimator.ofFloat(logoImageView, "translationY", 600f, 0f)
        val cardFadeInAnimator = ObjectAnimator.ofFloat(cardView, "alpha", 1f)
        cardFadeInAnimator.duration = 1000

        val headingAnimationSet = AnimatorSet()
        headingAnimationSet.playTogether(logoAnimator, cardFadeInAnimator)
        headingAnimationSet.duration = 500
        headingAnimationSet.start()

        // Create translation animations for the elements inside the card view
        val viewPagerAnimator = ObjectAnimator.ofFloat(viewPager, "translationY", 300f, 0f)
        val loginButtonAnimator = ObjectAnimator.ofFloat(loginButton, "translationY", 200f, 0f)
        val continueWithGoogleButtonAnimator = ObjectAnimator.ofFloat(continueWithGoogleButton, "translationY", 200f, 0f)
        val signUpButtonAnimator = ObjectAnimator.ofFloat(signUpButton, "translationY", 200f, 0f)

        // Create alpha animations for the elements inside the card view
        val viewPagerAlphaAnimator = ObjectAnimator.ofFloat(viewPager, "alpha", 0f, 1f)
        val loginButtonAlphaAnimator = ObjectAnimator.ofFloat(loginButton, "alpha", 0f, 1f)
        val continueWithGoogleButtonAlphaAnimator = ObjectAnimator.ofFloat(continueWithGoogleButton, "alpha", 0f, 1f)
        val signUpButtonAlphaAnimator = ObjectAnimator.ofFloat(signUpButton, "alpha", 0f, 1f)

        // Combine all animations into one AnimatorSet
        val combinedAnimatorSet = AnimatorSet()
        combinedAnimatorSet.playTogether(
            viewPagerAnimator, loginButtonAnimator,
            continueWithGoogleButtonAnimator, signUpButtonAnimator,
            viewPagerAlphaAnimator, loginButtonAlphaAnimator,
            continueWithGoogleButtonAlphaAnimator, signUpButtonAlphaAnimator
        )


        // Start the combined animation
        combinedAnimatorSet.start()
        viewPager.adapter=ImagePagerAdapter(this)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun checkLoginCredentials(username: String, password: String): Boolean {
        val saveUsername = sharedPref.getString("username", "") ?: ""
        val savePassword = sharedPref.getString("password", "") ?: ""

        return username == saveUsername && password == savePassword
    }
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val loginDto=LoginDto("","",fcmToken.toString(),account.email.toString())
                registerLoginViewModel.loginUser(loginDto,object :LoginCallback{
                    override fun onResponseLogin(loginResponse: LoginResponse) {
                        tokenManager.saveToken(loginResponse.accessToken)
                        val token=tokenManager.getToken()
                        userViewModel.getUser(token!!, object : UpdateUserCallback {
                            @RequiresApi(Build.VERSION_CODES.O)
                            override fun onUserUpdated(updatedUserDto: UserDto) {
                                saveLoginCredentials(updatedUserDto)
                                startActivity(Intent(this@LandingActivity,MainActivity::class.java))
                                finish()
                            }

                            override fun onUpdateUserError() {
                                tokenManager.clearToken()
                                Toast.makeText(this@LandingActivity,"Something Went Wrong",Toast.LENGTH_SHORT).show()

                            }

                        })
                    }

                    override fun onErrorLogin() {
                        Toast.makeText(this@LandingActivity,"Please create account first", Toast.LENGTH_SHORT).show()
                    }

                })
                // Signed in successfully, handle the account
                // You can use account.email, account.displayName, etc.
            } catch (e: ApiException) {
                // Sign in failed, handle the error
            }
        }
    }


    private fun saveLoginCredentials(userDto:UserDto) {
        val gson = Gson()
        val userJson = gson.toJson(userDto)
        val editor = sharedPref.edit()
        editor.putString("user_details", userJson)
        editor.putString("username", userDto.username)
        editor.putString("password", "")
        editor.apply()
    }

}
