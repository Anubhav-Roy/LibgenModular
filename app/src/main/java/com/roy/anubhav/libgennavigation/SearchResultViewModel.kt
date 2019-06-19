package com.roy.anubhav.libgennavigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.roy.anubhav.core.data.Book
import com.roy.anubhav.core.data.BookDataSourceFactory

class SearchResultViewModel: ViewModel() {

    //Book DataSourceFactory
    private lateinit var bookDataSourceFactory :BookDataSourceFactory

    //Online books data ( received from the server ) as a paginated list
    lateinit var booksList: LiveData<PagedList<Book>>

    fun performQuery(query:String){

        bookDataSourceFactory = BookDataSourceFactory(query)

        //PagedList configuration .
        val config = PagedList.Config.Builder()
            .setPageSize(25)        // The page size - We've set this to 10 as we're fetching 10 per request
            .setEnablePlaceholders(true)
            .build()


        booksList = LivePagedListBuilder<Int, Book>(bookDataSourceFactory, config).build()

        //state = Transformations.switchMap<BookDataSource,NetworkStatus>(bookDataSourceFactory.bookDataSource,
        //     BookDataSource::state)



    }
}