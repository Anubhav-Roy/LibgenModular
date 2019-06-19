package com.roy.anubhav.core.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class BookDataSourceFactory (val query:String) : DataSource.Factory<Int,Book>() {

    var bookDataSource: MutableLiveData<BookDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Book> {
        val bookDataSourcetemp = BookDataSource(query)
        bookDataSource.postValue(bookDataSourcetemp)
        return bookDataSourcetemp
    }
}