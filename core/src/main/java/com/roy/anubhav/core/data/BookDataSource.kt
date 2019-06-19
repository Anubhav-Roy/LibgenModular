package com.roy.anubhav.core.data

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.*

class BookDataSource(val  query:String): PageKeyedDataSource<Int, Book>() {


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Book>) {

        CoroutineScope(Dispatchers.IO).launch {
            val  job = async { NetworkRepository.getInstance().fetchResults(query,1) }

            val result = job.await()

            withContext(Dispatchers.Main){

                if(result!=null) {

                    callback.onResult(result, null, 2)
                }else{

                }
            }

        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {


        CoroutineScope(Dispatchers.IO).launch {
            val  job = async { NetworkRepository.getInstance().fetchResults(query,params.key) }

            val result = job.await()

            withContext(Dispatchers.Main){

                if(result!=null) {

                    callback.onResult(result, params.key+1)
                }else{

                }
            }

        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {

    }
}