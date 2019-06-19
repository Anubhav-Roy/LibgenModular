package com.roy.anubhav.core.data

import com.roy.anubhav.core.utils.network.NetworkUtils
import com.roy.anubhav.core.utils.network.NetworkUtils.Companion.convertStreamToString
import com.roy.anubhav.core.utils.network.NetworkUtils.Companion.createBookList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection

class NetworkCallsApi {

    suspend fun fetchResults(query:String,pageNumber:Int):ArrayList<Book> = withContext(Dispatchers.IO){

        val url = NetworkUtils.createURL(query,pageNumber)
        val urlConnection = url.openConnection() as HttpURLConnection
        var result:StringBuilder? = null
        try {
            val inputStream = urlConnection.inputStream
            result  = convertStreamToString(inputStream)
        } finally {
            urlConnection.disconnect()
        }

        createBookList(result!!)
    }

}