package ru.sashel007.quitsmoking.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.sashel007.quitsmoking.MainActivity
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.data.repository.MyRepositoryImpl
import ru.sashel007.quitsmoking.data.repository.dto.mappers.AchievementMapper.toEntity
import java.time.Duration
import java.time.Instant

@HiltWorker
class AchievementWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: MyRepositoryImpl
) : CoroutineWorker(context, workerParams) {

    private val notificationTitle = "Так держать!"

    override suspend fun doWork(): Result {
        Log.d("Notification_check", "doWork()")
        // Логика проверки достижений и отправки уведомлений
        try {
            val user = repository.getUserData()
            val quitTime = Instant.ofEpochMilli(user.quitTimeInMillisec)
            val currentTime = Instant.now()
            val timeSinceQuit = Duration.between(quitTime, currentTime).toMinutes()

            val currentAchievements = repository.getAllAchievements()

            for (achievement in currentAchievements) {
                val achievementDuration = achievement.duration
                val newProgressLine = ((timeSinceQuit.toFloat() / achievementDuration) * 100).coerceIn(0f, 100f)
                val isNewlyUnlocked = newProgressLine >= 100 && !achievement.isUnlocked

                if (isNewlyUnlocked) {
                    val updatedAchievement = achievement.copy(
                        progressLine = newProgressLine.toInt(),
                        isUnlocked = isNewlyUnlocked
                    )
                    val achievementData = updatedAchievement.toEntity()
                    repository.updateAchievement(achievementData)

                    sendNotification("Новое достижение: ${achievement.name} без сигарет")
                }
            }

            return Result.success()
        } catch (e: Exception) {
            Log.e("AchievementWorker", "Error updating achievements", e)
            return Result.failure()
        }
    }

    private fun sendNotification(message: String) {
        val name = context.getString(R.string.notification_channel_name)
        val descriptionText = context.getString(R.string.notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.quit_smoking_logo)
            .setContentTitle(notificationTitle)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "achievements_channel"
        private const val NOTIFICATION_ID = 1
    }
}
