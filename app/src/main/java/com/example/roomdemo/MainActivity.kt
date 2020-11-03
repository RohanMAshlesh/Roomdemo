package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.db.SubscriberDao
import com.example.roomdemo.db.SubscriberDatabase
import com.example.roomdemo.db.SubscriberRepository
import java.security.acl.Owner

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao:SubscriberDao = SubscriberDatabase.getInstance(application).subscriberDao
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        displaySubcribersList()

    }

    private fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displaySubcribersList()
    }

    private fun displaySubcribersList(){
        subscriberViewModel.subscribers.observe(this, Observer {
            Log.i("MyTag",it.toString())
            binding.recyclerView.adapter = MyRecyclerViewAdapter(it)
        })
    }
}