package com.example.gethired

import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.gethired.Callback.LoginCallback
import com.example.gethired.Repository.RegisterLoginRepository
import com.example.gethired.Token.LoginResponse
import com.example.gethired.Token.TokenCheckService
import com.example.gethired.Token.TokenManager
import com.example.gethired.entities.LoginDto
import com.example.gethired.utils.FontInterceptor
import com.google.firebase.FirebaseApp
import io.github.inflationx.viewpump.ViewPump

class MyApplication : Application() {
//   private lateinit var loginSharedPreferences: SharedPreferences
//   private lateinit var tokenManager: TokenManager
//   private lateinit var registerLoginRepository:RegisterLoginRepository
//   private lateinit var sharedPref:SharedPreferences

    override fun onCreate() {
        super.onCreate()

        CommonFunction.SharedPrefsUtil.init(this)

//        registerLoginRepository = RegisterLoginRepository()
//
//        loginSharedPreferences =
//            applicationContext.getSharedPreferences("login_preference", Context.MODE_PRIVATE)

//        sharedPref=applicationContext.getSharedPreferences("login_preference", Context.MODE_PRIVATE)
//       tokenManager= TokenManager(applicationContext)

        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(FontInterceptor())
                .build()
        )
        FirebaseApp.initializeApp(this)
//        scheduleTokenCheckJob()
//        requestNewToken { newToken ->
//            if (newToken != null) {
//                tokenManager.clearToken()
//                tokenManager.saveToken(newToken)
//            }else{
//                CommonFunction.SharedPrefsUtil.clearUserResponseFromSharedPreferences()
//
//                // Redirect to the login screen
//                val intent = Intent(applicationContext, LoginActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                ContextCompat.startActivity(applicationContext, intent, null)
//            }
//        }


//        val isDarkMode = sharedPref.getString("dark_mode","")?:""
//            if (isDarkMode=="dark") {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            }
    }

//    private fun scheduleTokenCheckJob() {
//        val componentName = ComponentName(this, TokenCheckService::class.java)
//        val jobInfo = JobInfo.Builder(JOB_ID, componentName)
//            .setPeriodic(CHECK_INTERVAL) // Set the interval for the job (e.g., every hour)
//            .build()
//
//        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
//        jobScheduler.schedule(jobInfo)
//    }
//
//    private fun requestNewToken(callback: (String?) -> Unit) {
//        val user = CommonFunction.SharedPrefsUtil.fetchUserResponseFromSharedPreferences()
//        val pass=loginSharedPreferences.getString("password","")
//
//        val loginDto = LoginDto(user.username, pass.toString(), user.fcmToken, "")
//
//        registerLoginRepository.loginUser(loginDto, object : LoginCallback {
//            override fun onResponseLogin(loginResponse: LoginResponse) {
//                callback(loginResponse.accessToken)
//            }
//
//            override fun onErrorLogin() {
//                callback(null)
//            }
//        })
//    }

    companion object {
        private const val JOB_ID = 123
        private const val CHECK_INTERVAL = 30L
    }
}
