package com.kashiflab.scopedstorage.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.kashiflab.scopedstorage.PhotoClickListener
import com.kashiflab.scopedstorage.R
import com.kashiflab.scopedstorage.data.model.InternalStorageFiles

class PhotosAdapter(private val photoClickListener: PhotoClickListener)
    : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    private var internalStorageFiles: List<InternalStorageFiles> = ArrayList()

    fun setInternalStorageFiles(internalStorageFiles: List<InternalStorageFiles>){
        this.internalStorageFiles = internalStorageFiles
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.photo_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj = internalStorageFiles[position]

        holder.imageView.setImageBitmap(obj.bmp)

        holder.imageView.setOnLongClickListener{
            photoClickListener.onPhotoLongClick(obj.name)
            true
        }
    }

    override fun getItemCount(): Int {
        return internalStorageFiles.size
    }
}