package com.kashiflab.scopedstorage.data.repository

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kashiflab.scopedstorage.data.model.InternalStorageFiles
import com.kashiflab.scopedstorage.data.utils.MediaUtils

class PhotoRepository(private val context: Context) {
    private val _photos = MutableLiveData<List<InternalStorageFiles>>()
    val photos : LiveData<List<InternalStorageFiles>>
        get() = _photos

    private val _isSaved = MutableLiveData<Boolean>()
    val saved : LiveData<Boolean>
    get() = _isSaved

    private val _isDeleted = MutableLiveData<Boolean>()
    val deleted : LiveData<Boolean>
        get() = _isDeleted

    suspend fun loadPhotosFromInternalStorage(){
        _photos.postValue(MediaUtils.loadPhotosFromInternalStorage(context))
    }

    suspend fun savePhotoInInternalStorage(fileName: String, bmp: Bitmap) {
        _isSaved.postValue(MediaUtils.savePhotoInInternalStorage(context, fileName, bmp))
        loadPhotosFromInternalStorage()
    }

    suspend fun deletePhotoInInternalStorage(fileName: String) {
        _isDeleted.postValue(MediaUtils.deletePhotoFromInternalStorage(context, fileName))
        loadPhotosFromInternalStorage()
    }

}