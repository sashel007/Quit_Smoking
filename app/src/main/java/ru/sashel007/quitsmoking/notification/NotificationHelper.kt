package ru.sashel007.quitsmoking.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.sashel007.quitsmoking.data.repository.dto.AchievementDto

class NotificationHelper(private val context: Context) {
    private val channelId = "achievements_channel"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        Log.d("Notification_check", "NotificationHelper // createNotificationChannel()")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Achievements",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for achievements notifications"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d("Notification_check", "NotificationHelper // Notification channel created")
        }
    }

    fun sendAchievementUnlockedNotification(achievement: AchievementDto) {
        Log.d("Notification_check", "NotificationHelper // sendAchievementUnlockedNotification()")

        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.w("Notification_check", "Permission not granted for POST_NOTIFICATIONS")
            return
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentTitle("Achievement Unlocked: ${achievement.name}")
            .setContentText(achievement.description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(achievement.id, builder.build())
        Log.d("Notification_check", "Notification sent for ${achievement.name}")
    }
}