package com.kashiflab.scopedstorage.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kashiflab.scopedstorage.data.model.InternalStorageFiles
import com.kashiflab.scopedstorage.data.repository.PhotoRepository
import com.kashiflab.scopedstorage.data.utils.MediaUtils
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val photos : LiveData<List<InternalStorageFiles>>
    get() = repo.photos

    val saved : LiveData<Boolean>
    get() = repo.saved

    val deleted : LiveData<Boolean>
    get() = repo.deleted

    private val repo: PhotoRepository

    init {
        repo = PhotoRepository(application)
        loadPhotosFromInternalStorage()
    }

    private fun loadPhotosFromInternalStorage(){
        viewModelScope.launch {
            repo.loadPhotosFromInternalStorage()
        }
    }

    fun savePhotoInInternalStorage(fileName: String, bmp: Bitmap) {
        viewModelScope.launch {
            repo.savePhotoInInternalStorage(fileName, bmp)
        }
    }

    fun deletePhotoInInternalStorage(fileName: String) {
       viewModelScope.launch {
            repo.deletePhotoInInternalStorage(fileName)
        }
    }

}