package com.kashiflab.scopedstorage.data.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.kashiflab.scopedstorage.data.model.InternalStorageFiles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class MediaUtils {

    companion object{
        fun savePhotoInInternalStorage(context: Context, fileName: String, bmp: Bitmap) : Boolean {
            return try{
                context.openFileOutput("$fileName.jpeg", MODE_PRIVATE).use { stream ->
                    if(!bmp.compress(Bitmap.CompressFormat.JPEG,95,stream)){
                        throw Exception("Couldn't open file")
                    }
                }

                true
            }catch (e: Exception){
                false
            }
        }

        fun deletePhotoFromInternalStorage(context: Context, fileName: String) : Boolean {
            return try {
                context.deleteFile(fileName)
            }catch (e: Exception){
                false
            }
        }

        suspend fun loadPhotosFromInternalStorage(context: Context) : List<InternalStorageFiles> {
            return withContext(Dispatchers.IO){
                val files = context.filesDir.listFiles()
                files?.filter {
                    it.canRead() && it.isFile && it.name.endsWith("jpeg")
                }?.map {
                    val bytes = it.readBytes()
                    val bmp = BitmapFactory.decodeByteArray(bytes,0, bytes.size)
                    InternalStorageFiles(it.name, bmp)
                }?: emptyList()
            }
        }
    }


}