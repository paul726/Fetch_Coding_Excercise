package com.pjiang.fetch_coding_exercise

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pjiang.fetch_coding_exercise.adapter.HiringGroupAdapter
import com.pjiang.fetch_coding_exercise.databinding.ActivityMainBinding
import com.pjiang.fetch_coding_exercise.viewmodel.HiringViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val hiringViewModel: HiringViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: HiringGroupAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        adapter = HiringGroupAdapter()
        binding.recyclerView.adapter = adapter

        hiringViewModel.hiringMutableLiveData.observe(this, {
            adapter.setData(it)
        })

        hiringViewModel.errorMsgLiveData.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        hiringViewModel.fetchHiringData()
    }
}