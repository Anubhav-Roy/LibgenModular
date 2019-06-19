package com.roy.anubhav.libgennavigation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.roy.anubhav.core.data.Book
import com.roy.anubhav.core.utils.network.NetworkUtils
import com.roy.anubhav.libgennavigation.R

class BookAdapter (var context: Context, var callback: (String, String)-> Unit) : PagedListAdapter<Book, BookAdapter.BookViewHolder>(
    bookDiffBack) {

    companion object{

        val bookDiffBack = object : DiffUtil.ItemCallback<Book>(){
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.book_item,parent,false))
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)

        holder.title.text = book!!.name
        holder.authors.text = book.author
        holder.size.text = book.size
        holder.url.setOnClickListener{
            if(NetworkUtils.isDownloadManagerAvailable(context)) {
                //
                callback(book.downloadURL, book.name)
            }
        }

    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder((itemView) ){
        val title: TextView = itemView.findViewById(R.id.title)
        val size: TextView = itemView.findViewById(R.id.size)
        val url: TextView = itemView.findViewById(R.id.url)
        val authors: TextView = itemView.findViewById(R.id.authors)

    }
}