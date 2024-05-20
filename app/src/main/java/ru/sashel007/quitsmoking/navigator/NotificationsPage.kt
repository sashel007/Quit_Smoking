package ru.sashel007.quitsmoking.navigator

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import ru.sashel007.quitsmoking.R

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NotificationsPage(
    moveToNextPage: () -> Unit,
    createNotificationChannel: () -> Unit
) {
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Разрешение предоставлено, создаем канал уведомлений
            createNotificationChannel()
        } else {
            // Разрешение отклонено
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun requestNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Разрешение уже предоставлено
                createNotificationChannel()
                permissionGranted = true
                moveToNextPage()
            }
            else -> {
                // Запросить разрешение
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.notification_situation),
            contentDescription = "Your Image Description",
            modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Получать уведомление о здоровье?",
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Пользователи, которые получают от нас уведомления, имеют более высокие шансы на успех",
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { requestNotificationPermission() },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Text(text = "Получать уведомления")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { moveToNextPage() },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Text(text = "Может, позже")
        }
    }
}
