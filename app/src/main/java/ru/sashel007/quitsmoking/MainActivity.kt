package ru.sashel007.quitsmoking

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint
import ru.sashel007.quitsmoking.navigator.AppNavigator
import ru.sashel007.quitsmoking.ui.theme.QuitSmokingTheme
import ru.sashel007.quitsmoking.util.MySharedPref
import ru.sashel007.quitsmoking.viewmodel.AchievementViewModel
import ru.sashel007.quitsmoking.viewmodel.LastOpenedPageViewModel
import ru.sashel007.quitsmoking.viewmodel.SmokingStatsViewModel
import ru.sashel007.quitsmoking.viewmodel.UserViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPref: MySharedPref

//    private val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//            Log.d("Notification_check", "MainActivity_Notification permission granted")
//            createNotificationChannel()
//        } else {
//            Log.d("Notification_check", "MainActivity_Notification permission denied")
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        val userViewModel: UserViewModel by viewModels<UserViewModel>()
        val lastOpenedPageViewModel: LastOpenedPageViewModel by viewModels<LastOpenedPageViewModel>()
        val smokingStatsViewModel: SmokingStatsViewModel by viewModels<SmokingStatsViewModel>()
        val achievementViewModel: AchievementViewModel by viewModels<AchievementViewModel>()

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            when {
//                ContextCompat.checkSelfPermission(
//                    this,
//                    android.Manifest.permission.POST_NOTIFICATIONS
//                ) == PackageManager.PERMISSION_GRANTED -> {
//                    Log.d("Notification_check", "MainActivity_Notification permission already granted")
//                    createNotificationChannel()  // Создание канала уведомлений
//                }
//                shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS) -> {
//                    Log.d("Notification_check", "MainActivity_Show rationale for notification permission")
//                }
//                else -> {
//                    Log.d("Notification_check", "MainActivity_Requesting notification permission")
//                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//                }
//            }
//        }

        setContent {
            QuitSmokingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    AppNavigator(
                        userViewModel,
                        lastOpenedPageViewModel,
                        smokingStatsViewModel,
                        achievementViewModel,
                        sharedPref
                    ) {
                        createNotificationChannel()
                    }
                }
            }
        }

        achievementViewModel.newlyUnlockedAchievement.observe(this) { achievement ->
            Log.d("Notification_check", "Observer triggered")
            achievement?.let {
                Log.d("Notification_check", "New achievement unlocked: ${it.name}")
                sendNotification(
                    this,
                    "Новое достижение: ${it.name} без сигарет"
                )
            }
        }
    }

    private fun createNotificationChannel() {
        Log.d("Notification_check", "Creating notification channel")
        val name = getString(R.string.notification_channel_name)
        val descriptionText = getString(R.string.notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        Log.d("Notification_check", "Notification channel created")
    }

    private fun sendNotification(context: Context, message: String) {
        val notificationTitle = "Так держать!"
        Log.d("Notification_check", "sendNotification called")
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.d("Notification_check", "Permission granted")
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
                notify(NOTIFICATION_ID, builder.build())
                Log.d("Notification_check", "Notification sent")
            }
        } else {
            Log.d("Notification_check", "Permission for notifications not granted")
        }
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "achievements_channel"
        private const val NOTIFICATION_ID = 1
    }

}

