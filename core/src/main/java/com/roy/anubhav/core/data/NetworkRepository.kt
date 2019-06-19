package com.roy.anubhav.core.data

class NetworkRepository {

    companion object {

        //Singleton instance
        @Volatile
        private var INSTANCE: NetworkCallsApi? = null

        fun getInstance(): NetworkCallsApi {

            val tempInstance = INSTANCE
            if (tempInstance != null) {     //Already have made the Network Repo Instance
                return tempInstance
            }
            //We haven't made the Network Repo Instance
            //We lock other threads and create the instance in this thread
            //and set the instance
            synchronized(this) {
                val instance = NetworkCallsApi()
                INSTANCE = instance
                return instance
            }
        }
    }

}