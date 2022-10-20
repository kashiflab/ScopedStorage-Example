package com.kashiflab.scopedstorage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kashiflab.scopedstorage.data.model.InternalStorageFiles
import com.kashiflab.scopedstorage.data.utils.MediaUtils
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _photos = MutableLiveData<List<InternalStorageFiles>>()
    val photos : LiveData<List<InternalStorageFiles>>
    get() = _photos


    init {
        loadPhotosFromInternalStorage()
    }

    fun loadPhotosFromInternalStorage(){
        viewModelScope.launch {

            _photos.postValue(MediaUtils.loadPhotosFromInternalStorage(getApplication<Application>()))
        }
    }

}