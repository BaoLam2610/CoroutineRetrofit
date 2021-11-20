package com.example.retrofitexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitexample.adapter.DataClientAdapter
import com.example.retrofitexample.adapter.PostItemAdapter
import com.example.retrofitexample.api.RetrofitInstance
import com.example.retrofitexample.databinding.ActivityDemoBinding
import com.example.retrofitexample.model.DataClient
import com.example.retrofitexample.model.PostItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DemoActivity : AppCompatActivity() {

    private val TAG = "DemoActivity"
    lateinit var binding: ActivityDemoBinding
    lateinit var adapter: DataClientAdapter
    lateinit var adapter2: PostItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        getDataClients()
//        getPostItem()
//        updatePost()
        deletePost()
    }

    fun setupAdapter(dataClientList: List<DataClient>) {
        adapter = DataClientAdapter(dataClientList)
        binding.rvDataClients.adapter = adapter
        binding.rvDataClients.layoutManager = LinearLayoutManager(this)
    }

    fun setupAdapter2(postList: List<PostItem>) {
        adapter2 = PostItemAdapter(postList)
        binding.rvDataClients.adapter = adapter2
        binding.rvDataClients.layoutManager = LinearLayoutManager(this)
    }

    fun getDataClients() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                var map = mapOf(
                    "userId" to "3",
                    "_sort" to "id",
                    "_order" to "desc"
                )
                val call =
                    RetrofitInstance.getInstance().getApi().getAllDataClient(map)
                withContext(Dispatchers.Main) {
                    setupAdapter(call.body()!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getPostItem(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val call =
                    RetrofitInstance.getInstance().getApi().getPostItems(3)
                withContext(Dispatchers.Main) {
                    setupAdapter2(call.body()!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePost(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val dataClient = DataClient(12,1,null,"New content")
                val call =
                    RetrofitInstance.getInstance().getApi().patchDataClient(1,dataClient)
                val dataResponse = call.body()
                Log.d(TAG, "----UPDATE-------")
                Log.d(TAG, dataResponse?.userId.toString())
                Log.d(TAG, dataResponse?.id.toString())
                Log.d(TAG, dataResponse?.title.toString())
                Log.d(TAG, dataResponse?.content.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deletePost(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val call =
                    RetrofitInstance.getInstance().getApi().deleteDataClient(1)
                if(call.isSuccessful){
                    Log.d(TAG, "Delete successful, code: ${call.code()}")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}