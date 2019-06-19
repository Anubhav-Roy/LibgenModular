package com.roy.anubhav.core.dagger

import com.roy.anubhav.core.data.NetworkCallsApi
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface CoreComponent {

    @Component.Builder interface Builder {
        fun build(): CoreComponent
    }

    fun provideNetworkCallsApi(): NetworkCallsApi
}