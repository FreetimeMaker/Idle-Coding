package com.freetime.idlecoding.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.freetime.idlecoding.repository.BackupScheduler
import com.freetime.idlecoding.repository.BuffNotificationScheduler
import com.freetime.idlecoding.repository.PlayerRepository
import com.freetime.idlecoding.repository.QueuedSessionStarter
import com.freetime.idlecoding.repository.SessionRepository
import com.freetime.idlecoding.repository.WorkerQueuedSessionStarter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject lateinit var sessionRepository: SessionRepository
    @Inject lateinit var queuedSessionStarter: QueuedSessionStarter
    @Inject lateinit var workerStarter: WorkerQueuedSessionStarter
    @Inject lateinit var playerRepository: PlayerRepository
    @Inject lateinit var backupScheduler: BackupScheduler
    @Inject lateinit var buffNotifScheduler: BuffNotificationScheduler

    override fun onReceive(context: Context, intent: Intent) {
        // MY_PACKAGE_REPLACED included: an app update cancels every alarm this app set,
        // and only a cold MainActivity launch used to restore them, so scheduled backups
        // and session timers could stay dead for days after updating.
        if (intent.action != Intent.ACTION_BOOT_COMPLETED &&
            intent.action != Intent.ACTION_MY_PACKAGE_REPLACED) return
        val pending = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                sessionRepository.recoverActiveSession(queuedSessionStarter)
                sessionRepository.recoverActiveWorkerSession(1, workerStarter)
                sessionRepository.recoverActiveWorkerSession(2, workerStarter)
                val flags = playerRepository.getFlags()
                if (flags.backupFrequency.isNotEmpty()) backupScheduler.schedule(flags.backupFrequency)
                buffNotifScheduler.scheduleXpBoostExpiry(flags.xpBoostExpiresAt)
                buffNotifScheduler.scheduleBlessingExpiry(flags.activeBlessingExpiresAt)
            } finally {
                pending.finish()
            }
        }
    }
}
