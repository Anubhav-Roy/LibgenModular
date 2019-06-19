package com.roy.anubhav.core.dagger

import com.roy.anubhav.core.data.NetworkCallsApi
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideNetworkCallsApi():NetworkCallsApi = NetworkCallsApi()

}