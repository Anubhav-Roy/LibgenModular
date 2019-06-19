package com.roy.anubhav.libgennavigation

import android.app.Activity
import android.app.Application
import android.content.Context
import com.roy.anubhav.core.dagger.CoreComponent
import com.roy.anubhav.core.dagger.DaggerCoreComponent

class LibGenApplication:Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.create()
    }

    companion object {
        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as LibGenApplication).coreComponent
    }
}

fun Activity.coreComponent() = LibGenApplication.coreComponent(this)