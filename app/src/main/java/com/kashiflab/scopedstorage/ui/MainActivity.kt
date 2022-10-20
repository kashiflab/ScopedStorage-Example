package com.kashiflab.scopedstorage.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kashiflab.scopedstorage.ui.viewmodel.MainViewModel
import com.kashiflab.scopedstorage.data.utils.MediaUtils
import com.kashiflab.scopedstorage.data.listeners.PhotoClickListener
import com.kashiflab.scopedstorage.ui.adapter.PhotosAdapter
import com.kashiflab.scopedstorage.databinding.ActivityMainBinding
import java.util.UUID

class MainActivity : AppCompatActivity(), PhotoClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PhotosAdapter

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
        it?.let { it1 ->
            mainViewModel.savePhotoInInternalStorage(UUID.randomUUID().toString(), it1)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)

        adapter = PhotosAdapter(this)
        binding.recyclerView.adapter = adapter

        mainViewModel.photos.observe(this, Observer {
            adapter.setInternalStorageFiles(it)
        })

        mainViewModel.saved.observe(this, Observer {
            if(it){
                Toast.makeText(this,"Saved", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            }
        })

        mainViewModel.deleted.observe(this, Observer {
            if(it){
                Toast.makeText(this,"Deleted", Toast.LENGTH_SHORT).show()
            }
        })

        binding.takePicture.setOnClickListener {
            takePicture.launch(null)
        }

    }

    override fun onPhotoLongClick(fileName: String) {
        mainViewModel.deletePhotoInInternalStorage(fileName)
    }
}