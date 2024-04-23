package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.data.repository.dto.AchievementDto

@Composable
fun Achievements(achievementsState: State<List<AchievementDto>?>) {
    val achievements by remember(achievementsState.value) {
        derivedStateOf {
            achievementsState.value
        }
    }

    Box(
        modifier = Modifier
            .width(360.dp)
            .height(244.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 14.dp,
                    bottomEnd = 14.dp
                )
            )
            .background(Color(red = 0.7372549f, green = 0.7372549f, blue = 0.9137255f, alpha = 1f))
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 2.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, start = 12.dp, end = 12.dp, top = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.achievement_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.show_all),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }
            LazyRow(
                contentPadding = PaddingValues(start = 6.dp, end = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                achievements?.let {
                    items(it) { achievement ->
                        AchievementBlock(achievement)
                    }
                }
            }
        }
    }
}

@Composable
fun AchievementBlock(achievement: AchievementDto) {
    val context = LocalContext.current
    val imageName = achievement.imageUri
    val imageResId = context.resources.getIdentifier(
        imageName,
        "drawable",
        context.packageName
    )

    val progress = achievement.progressLine
    val isUnlocked = achievement.isUnlocked
    val description = achievement.name
    val backgroundColor = if (isUnlocked) Color.White else Color.Gray.copy(alpha = 0.5f)

    Box(
        modifier = Modifier
            .width(138.dp)
            .height(172.dp)
            .background(Color.Transparent)
            .padding(start = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .width(138.dp)
                .height(172.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = backgroundColor)
                .padding(10.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            if (!isUnlocked) {
                LinearProgressIndicator(
                    progress = { progress / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .align(Alignment.BottomCenter),
                    color = Color.Green
                )
            }
            Column(
                modifier = Modifier
                    .border(2.dp, Color.Yellow)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.border(2.dp, Color.Black)) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = "Достижение_1",
                        modifier = Modifier
                            .size(80.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = description,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    modifier = Modifier
                        .border(2.dp, Color.Red)
                        .width(110.dp)
                        .weight(1f)
                )
            }
        }
    }
}



