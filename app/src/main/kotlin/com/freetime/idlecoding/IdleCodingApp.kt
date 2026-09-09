package com.freetime.idlecoding

import android.app.Application
import com.freetime.idlecoding.notification.SessionNotificationManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class IdleCodingApp : Application() {

    @Inject lateinit var notificationManager: SessionNotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager.createChannels()
    }
}
