package com.example.retrofitexample

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.retrofitexample.api.RetrofitInstance
import com.example.retrofitexample.databinding.ActivityMainBinding
import com.example.retrofitexample.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.awaitResponse
import java.io.File

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    var pathAvt = ""
    var file: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermission()
        val user = intent.getSerializableExtra("user") as User
        binding.etUserName.setText(user.username)
        binding.etPassword.setText(user.password)

        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                    intent.let {
                        binding.ivAvatar.visibility = View.VISIBLE
                        binding.ivAvatar.setImageURI(intent?.data)
                        pathAvt = getRealPathFromURI(intent?.data!!)
                        file = File(pathAvt)
                        var filePath = file!!.absolutePath
//                        Log.d(TAG, filePath)
//                        Toast.makeText(this, filePath,Toast.LENGTH_LONG).show()
                        var i1 = filePath.lastIndexOf("/")
                        var i2 = filePath.lastIndexOf(".")
                        pathAvt = filePath.substring(i1+1, i2) + System.currentTimeMillis() + filePath.substring(i2, filePath.length)
//                        Log.d(TAG,pathAvt )
                    }
                }
            }
        binding.tvChooseImage.setOnClickListener {
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                startForResult.launch(it)
            }
        }

        binding.btnUpdate.setOnClickListener {
            updateAccount()
        }
        binding.btnDelete.setOnClickListener {
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                123)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    123)
            }
        }
    }

    private fun getRealPathFromURI(contentUri: Uri): String {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = contentResolver.query(contentUri, proj, null, null, null)
            val columnIndex: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(columnIndex)
        } finally {
            cursor?.close()
        }
    }

    fun updateAccount() {
//        val requestBody = pathAvt.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val requestBody =
            file?.let { it.asRequestBody("multipart/form-data".toMediaTypeOrNull()) }
        val multipartBodyAvt =
            requestBody?.let { MultipartBody.Part.createFormData("upload_avt",pathAvt, it) }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val call = multipartBodyAvt?.let {
                    RetrofitInstance.getInstance().getApi().postAvatar(it).awaitResponse()
                }
                if (call!!.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        if (call.body()?.resultCode != 0)
                            Toast.makeText(this@MainActivity,
                                "dang anh thanh cong",
                                Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception){

            }
        }

    }

}