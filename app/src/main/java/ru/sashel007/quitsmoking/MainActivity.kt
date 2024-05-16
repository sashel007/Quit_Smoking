package ru.sashel007.quitsmoking

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Observer
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint
import ru.sashel007.quitsmoking.mainscreen.elements.AppNavigator
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        val userViewModel: UserViewModel by viewModels<UserViewModel>()
        val lastOpenedPageViewModel: LastOpenedPageViewModel by viewModels<LastOpenedPageViewModel>()
        val smokingStatsViewModel: SmokingStatsViewModel by viewModels<SmokingStatsViewModel>()
        val achievementViewModel: AchievementViewModel by viewModels<AchievementViewModel>()

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
                    )
                }
            }
        }
    }
}

