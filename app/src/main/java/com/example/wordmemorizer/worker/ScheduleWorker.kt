package com.example.wordmemorizer.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.WorkerParameters
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class ScheduleWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        createNotificationChannel()
        sendNotification(
            context = applicationContext,
            title = "Үгээ цээжлээрэй",
            message = "Цаг хугацаа өнгөрөхөөс өмнө үгээ давтаарай!"
        )
        return Result.success()
    }

    private fun createNotificationChannel() {
        val name = "Word Reminder"
        val descriptionText = "Notifications for word memorization reminders"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("word_reminder_channel", name, importance).apply {
            description = descriptionText
        }

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun sendNotification(context: Context, title: String, message: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        val notification = NotificationCompat.Builder(context, "word_reminder_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with your own icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification)
    }

    companion object {
        const val WORK_NAME = "WordReminderWork"
    }
}

fun scheduleNotification(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<ScheduleWorker>(
        30,
        TimeUnit.DAYS
    )
        .addTag("word_reminder")
        .setInitialDelay(1, TimeUnit.MINUTES)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        ScheduleWorker.WORK_NAME,
        ExistingPeriodicWorkPolicy.UPDATE,
        workRequest
    )
}