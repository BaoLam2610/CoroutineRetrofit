package com.example.retrofitexample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitexample.api.ApiResponse
import com.example.retrofitexample.api.RetrofitInstance
import com.example.retrofitexample.databinding.ActivityLoginBinding
import kotlinx.coroutines.*
import retrofit2.*

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    lateinit var binding: ActivityLoginBinding
    var checkLogin = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvSwap.setOnClickListener {
            if (checkLogin) {
                binding.btnLoginRegister.text = "Register"
                checkLogin = false
                binding.tvSwap.text = "Do you have account? Login now"
            } else {
                binding.btnLoginRegister.text = "Login"
                checkLogin = true
                binding.tvSwap.text = "Don't have account? Register now"
            }
        }

        binding.btnLoginRegister.setOnClickListener {
            if (checkLogin) {
                loginAccount()
            } else {
                registerAccount()
            }
        }
    }

    fun loginAccount() {
        val username = binding.etUserName.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val map = mapOf(
            "username" to username,
            "password" to password
        )
        // solution 1
        GlobalScope.launch(Dispatchers.IO) {
            try {
                var call = RetrofitInstance.getInstance().getApi().loginAccount(map)

                withContext(Dispatchers.Main) {
                    if(call.body()?.username!="") {
                        Toast.makeText(this@LoginActivity,
                            "Dang nhap thanh cong",
                            Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "${call.body()?.username}")
                        Intent(this@LoginActivity, MainActivity::class.java).also{
                            it.putExtra("user",call.body())
                            startActivity(it)
                        }
                    } else{
                        Toast.makeText(this@LoginActivity,
                            "Dang nhap that bai",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e:Exception){
            Log.e(TAG, e.stackTraceToString())
            } finally {

            }
        }



    }

    fun registerAccount() {
//        val requestBody = pathAvt.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val username = binding.etUserName.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        // solution 1
        val call = RetrofitInstance.getInstance().getApi().registerAccount(username, password)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>,
            ) {
                if(response.body()?.resultCode == 2)
                    Toast.makeText(this@LoginActivity, "Tai khoan da ton tai", Toast.LENGTH_SHORT)
                    .show()
                else
                    Toast.makeText(this@LoginActivity, "Dang ki thanh cong", Toast.LENGTH_SHORT)
                        .show()
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e(TAG, t.stackTraceToString())
                Toast.makeText(this@LoginActivity, t.stackTraceToString(), Toast.LENGTH_SHORT)
                    .show()

            }

        })
        // solution 2
        /* GlobalScope.launch(Dispatchers.IO) {
             try {
                 val call = RetrofitInstance.getInstance().getApi()
                     .registerAccount(username, password)
                     .awaitResponse()
                 if (call.isSuccessful) {
                     val response = call.body()
                     withContext(Dispatchers.Main) {
                         Toast.makeText(this@LoginActivity, "${response?.status} - ${response?.resultCode}", Toast.LENGTH_SHORT)
                             .show()
                     }
                 }
             } catch (e: Exception){
                 Log.e(TAG, e.stackTraceToString())
             }
         }*/
    }
}