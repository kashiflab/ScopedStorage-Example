package com.kashiflab.scopedstorage.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kashiflab.scopedstorage.MainViewModel
import com.kashiflab.scopedstorage.data.utils.MediaUtils
import com.kashiflab.scopedstorage.PhotoClickListener
import com.kashiflab.scopedstorage.ui.adapter.PhotosAdapter
import com.kashiflab.scopedstorage.databinding.ActivityMainBinding
import java.util.UUID

class MainActivity : AppCompatActivity(), PhotoClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PhotosAdapter

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
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

        val takePicture = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
            it?.let { it1 ->
                val isSaved = MediaUtils.savePhotoInInternalStorage(
                    this, UUID.randomUUID().toString(),
                    it1
                )

                if(isSaved){
                    Toast.makeText(this,"Saved", Toast.LENGTH_SHORT).show()
                    loadPhotos()
                }else{
                    Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.takePicture.setOnClickListener {
            takePicture.launch(null)
        }

    }

    private fun loadPhotos(){
        mainViewModel.loadPhotosFromInternalStorage()
    }


    override fun onPhotoLongClick(fileName: String) {
        MediaUtils.deletePhotoFromInternalStorage(this, fileName)
    }
}