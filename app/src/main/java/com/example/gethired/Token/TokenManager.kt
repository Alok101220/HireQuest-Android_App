package com.example.gethired.Token

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import com.example.gethired.AddUserProfileDetailsActivity
import com.example.gethired.Callback.LoginCallback
import com.example.gethired.CommonFunction
import com.example.gethired.LoginActivity
import com.example.gethired.Repository.RegisterLoginRepository
import com.example.gethired.entities.LoginDto
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.nio.charset.Charset
import java.util.Base64

class TokenManager(private val context: Context) {
    private val registerLoginRepository = RegisterLoginRepository()

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    private val loginSharedPreferences: SharedPreferences =
        context.getSharedPreferences("login_preference", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit().putString("jwt_token", token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("jwt_token", null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove("jwt_token").apply()
    }

    fun isTokenValid(): Boolean {
        val token = getToken()
        return token != null && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        try {
            val claims: Claims = Jwts.parser()
                .setSigningKey("1fe95c7cd261a353e7ccd603144031cf85bf91691fe95c7cd261a353e7ccd603144031cf85bf916922ea24bf3b39711e5a32bc0cc84e1681") // Replace with your JWT secret key
                .parseClaimsJws(token)
                .body
            val expirationTime = claims.expiration.time
            val currentTime = System.currentTimeMillis()

            return currentTime > expirationTime
        }
        catch (e:ExpiredJwtException){
            e.printStackTrace()

            requestNewToken { newToken ->
                if (newToken != null) {
                    clearToken()
                    saveToken(newToken)
                }else{
                    CommonFunction.SharedPrefsUtil.clearUserResponseFromSharedPreferences()

                    // Redirect to the login screen
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(context,intent,null)
                }
            }

            return true
        }

    }

    private fun requestNewToken(callback: (String?) -> Unit) {
        val user = CommonFunction.SharedPrefsUtil.fetchUserResponseFromSharedPreferences()
        val pass=loginSharedPreferences.getString("password","")

        val loginDto = LoginDto(user.username, pass.toString(),"", "")

        registerLoginRepository.loginUser(loginDto, object : LoginCallback {
            override fun onResponseLogin(loginResponse: LoginResponse) {
                callback(loginResponse.accessToken)
            }

            override fun onErrorLogin() {
                callback(null)
            }
        })
    }
}
