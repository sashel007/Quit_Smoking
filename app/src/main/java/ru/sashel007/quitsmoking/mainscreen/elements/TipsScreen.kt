package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.data.repository.dto.DialogSpecificType
import ru.sashel007.quitsmoking.data.repository.dto.FaqItem
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles
import ru.sashel007.quitsmoking.util.getFactsFromJson
import ru.sashel007.quitsmoking.util.getFaqItemsFromJson
import ru.sashel007.quitsmoking.util.getMythsFromJson
import ru.sashel007.quitsmoking.util.getTipsFromJson

@Composable
fun TipsScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(/* Color(0xFFFBFAFA) */ Color.LightGray)
    ) {
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .shadow(
                        elevation = 2.dp, shape = RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp)
                    )
                    .clip(RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp))
                    .background(Color.White)
                    .padding(vertical = 12.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Полезная информация",
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(200.dp)
                    .shadow(2.dp, RoundedCornerShape(20.dp))
                    .background(Color.White, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FactsBlock(navController = navController)
                        MythsBlock(navController = navController)
                    }
                }
            }
            TipsBlock(navController = navController)
        }
    }

}

@Composable
fun FaqColumn() {
    val context = LocalContext.current
    val faqItems = remember { getFaqItemsFromJson(context) }
    var expandedItemIndex by remember { mutableStateOf<Int?>(null) }

    Column {
        faqItems.forEachIndexed { index, faqItem ->
            FaqBlock(faqItem = faqItem, expanded = expandedItemIndex == index, onClick = {
                expandedItemIndex = if (expandedItemIndex == index) null else index
            })
        }
    }

}

@Composable
fun FaqBlock(faqItem: FaqItem, expanded: Boolean, onClick: () -> Unit) {
    val rotationAngle by animateDpAsState(
        targetValue = if (expanded) 180.dp else 180.dp,
        animationSpec = tween(durationMillis = 300),
        label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(start = 16.dp, end = 10.dp, top = 6.dp, bottom = 6.dp)
        ) {
            Text(
                text = faqItem.question,
                fontFamily = MyTextStyles.mRobotoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
//                    .clickable(
//                        interactionSource = remember {
//                            MutableInteractionSource()
//                        },
//                        indication = null
//                    ) {
//                        expanded = !expanded
//                    },
                lineHeight = 16.sp
            )
            IconButton(
                onClick = onClick, modifier = Modifier.rotate(rotationAngle.value)
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Раскрыть"
                )
            }
        }
        AnimatedVisibility(visible = expanded) {
            Text(
                text = faqItem.answer,
                modifier = Modifier.padding(16.dp),
                fontSize = 16.sp,
                fontFamily = MyTextStyles.mRobotoFontFamily,
                fontWeight = FontWeight.Light,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun FactsBlock(navController: NavController) {
    val colorStops = arrayOf(
        0.0f to Color(0xFFF05757), 0.2f to Color(0xFFD081AC), 1f to Color(0xFFB1AAFF)
    )

    Box(modifier = Modifier
        .size(160.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable { navController.navigate("facts_list") }
        .background(Brush.linearGradient(colorStops = colorStops))
        .padding(8.dp)) {
        Text(
            "Факты",
            fontFamily = MyTextStyles.mRobotoFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 6.dp, top = 12.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.facts_icon),
            contentDescription = "Факты",
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.BottomEnd)
                .graphicsLayer {
                    translationY = 16.dp.toPx()
                    translationX = 16.dp.toPx()
                },
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MythsBlock(navController: NavController) {
    val colorStops = arrayOf(
        0.0f to Color(0xFFB4B4B4), /* 0.2f to Color(0xFFDAB092), */ 1f to Color(0xFFFFAB6F)
    )

    Box(modifier = Modifier
        .size(160.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable { navController.navigate("myths_list") }
        .background(Brush.linearGradient(colorStops = colorStops))
        .padding(8.dp)) {
        Text(
            "Мифы\nо курении",
            fontFamily = MyTextStyles.mRobotoFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 6.dp, top = 12.dp),
            color = Color.White
        )
        Image(painter = painterResource(id = R.drawable.myths_icon),
            contentDescription = "Мифы\n" + "о курении",
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.BottomEnd)
                .graphicsLayer {
                    translationY = 10.dp.toPx()
                    translationX = 12.dp.toPx()
                })
    }
}

@Composable
fun TipsBlock(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .height(800.dp)
            .shadow(2.dp, RoundedCornerShape(20.dp))
            .background(Color.White, RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Pin()
            TipsTitle()
            TipsInfo()
            MoveToTipsListButton(navController = navController)
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 12.dp)
            )
            FaqColumn()
        }
    }
}

@Composable
fun MoveToTipsListButton(navController: NavController) {
    Box {
        OutlinedButton(onClick = { navController.navigate("tips_list") }) {
            Text(
                text = "Перейти",
                fontFamily = MyTextStyles.mRobotoFontFamily,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.9.sp
            )
        }
    }
}


@Composable
fun TipsInfo() {
    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = "Следуйте приведённым здесь советам, чтобы отказ от курения прошёл максимально эффективно. В острых случаях проконсультируйтесь со специалистом.",
            fontFamily = MyTextStyles.mRobotoFontFamily,
            fontWeight = FontWeight.Light,
            color = Color.Gray,
            fontSize = 14.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
fun TipsTitle() {
    Box(
        modifier = Modifier
            .padding(start = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            "Советы",
            fontFamily = MyTextStyles.mRobotoFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Pin() {
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .size(width = 40.dp, height = 5.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(50))
    )
}

@Composable
fun TipsList(navController: NavController) {
    val context = LocalContext.current
    val tipsItems = remember { getTipsFromJson(context) }

    val scrollState = rememberScrollState()
    val isScrolled = remember {
        derivedStateOf { scrollState.value > 0 }
    }
    val backgroundColor = if (isScrolled.value) Color.White else Color.Transparent
    val shape =
        if (isScrolled.value) RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp) else RectangleShape
    val shadowElevation = if (isScrolled.value) 8.dp else 0.dp
    val onShowTipDialog = remember { mutableStateOf(false) }
    val onTipTransit = remember { mutableStateOf<DialogSpecificType?>(null) }

    Box {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = shadowElevation, shape = shape)
                    .clip(shape)
                    .background(backgroundColor)
            ) {
                Box(modifier = Modifier.align(Alignment.TopStart)) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
                Box(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        "Советы",
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(12.dp)
            ) {
                tipsItems.forEach { tip ->
                    val tipId = tip.id
                    val tipTitle = tip.name
                    val tipDescription = tip.text
                    val imgSize = 60.dp

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onShowTipDialog.value = true
                            onTipTransit.value = tip
                        }
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        TipImage(tipId, imgSize)
                        Spacer(Modifier.width(20.dp))
                        Column(modifier = Modifier.height(IntrinsicSize.Min)) {
                            Text(
                                text = tipTitle,
                                fontFamily = MyTextStyles.mRobotoFontFamily,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = true
                            )
                            Text(
                                text = tipDescription,
                                fontFamily = MyTextStyles.mRobotoFontFamily,
                                fontWeight = FontWeight.Light,
                                textAlign = TextAlign.Start,
                                maxLines = 2,
                                lineHeight = 14.sp,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = true,
                                fontSize = 12.sp
                            )
                        }
                    }
                    HorizontalDivider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
    if (onShowTipDialog.value) {
        MyDialog(
            onDismissRequest = { onShowTipDialog.value = false },
            inputType = onTipTransit.value
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDialog(
    onDismissRequest: () -> Unit,
    inputType: DialogSpecificType?
) {
    val tipTitle = inputType?.name
    val tipDescription = inputType?.text

    BasicAlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .width(100.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
        ) {
            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                IconButton(onClick = { onDismissRequest() }) {
                    Icon(Icons.Default.Clear, contentDescription = "Назад")
                }
            }
            Column(modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(horizontal = 16.dp, vertical = 22.dp)) {
                Text(
                    text = tipTitle!!,
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = tipDescription!!,
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Start,
                    lineHeight = 18.sp,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun TipImage(id: Int, imgSize: Dp) {
    val gradientColors = Brush.linearGradient(
        colors = listOf(Color(0xFFFFF6A1), Color(0xFFFFCBA3), Color(0xFFFFA4A4)),
        start = Offset(0f, 0f),
        end = Offset.Infinite
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(imgSize)
            .shadow(4.dp, CircleShape, clip = false)
            .background(gradientColors, CircleShape)
            .aspectRatio(1f)
    ) {
        Text(
            text = "${id + 1}",
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun FactList(navController: NavController) {
    val context = LocalContext.current
    val factItems = remember { getFactsFromJson(context) }

    val scrollState = rememberScrollState()
    val isScrolled = remember {
        derivedStateOf { scrollState.value > 0 }
    }
    val backgroundColor = if (isScrolled.value) Color.White else Color.Transparent
    val shape =
        if (isScrolled.value) RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp) else RectangleShape
    val shadowElevation = if (isScrolled.value) 8.dp else 0.dp
    val onShowTipDialog = remember { mutableStateOf(false) }
    val onFactTransit = remember { mutableStateOf<DialogSpecificType?>(null) }

    Box {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = shadowElevation, shape = shape)
                    .clip(shape)
                    .background(backgroundColor)
            ) {
                Box(modifier = Modifier.align(Alignment.TopStart)) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
                Box(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        "Факты",
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(12.dp)
            ) {
                factItems.forEach { fact ->
                    val factId = fact.id
                    val factTitle = fact.name
                    val factDescription = fact.text
                    val imgSize = 60.dp

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onShowTipDialog.value = true
                            onFactTransit.value = fact
                        }
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        TipImage(factId, imgSize)
                        Spacer(Modifier.width(20.dp))
                        Column(modifier = Modifier.height(IntrinsicSize.Min)) {
                            Text(
                                text = factTitle,
                                fontFamily = MyTextStyles.mRobotoFontFamily,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = true
                            )
                            Text(
                                text = factDescription,
                                fontFamily = MyTextStyles.mRobotoFontFamily,
                                fontWeight = FontWeight.Light,
                                textAlign = TextAlign.Start,
                                maxLines = 2,
                                lineHeight = 14.sp,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = true,
                                fontSize = 12.sp
                            )
                        }
                    }
                    HorizontalDivider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }

    if (onShowTipDialog.value) {
        MyDialog(
            onDismissRequest = { onShowTipDialog.value = false },
            inputType = onFactTransit.value
        )
    }
}

@Composable
fun MythList(navController: NavController) {
    val context = LocalContext.current
    val mythItems = remember { getMythsFromJson(context) }

    val scrollState = rememberScrollState()
    val isScrolled = remember {
        derivedStateOf { scrollState.value > 0 }
    }
    val backgroundColor = if (isScrolled.value) Color.White else Color.Transparent
    val shape =
        if (isScrolled.value) RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp) else RectangleShape
    val shadowElevation = if (isScrolled.value) 8.dp else 0.dp
    val onShowTipDialog = remember { mutableStateOf(false) }
    val onFactTransit = remember { mutableStateOf<DialogSpecificType?>(null) }

    Box {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = shadowElevation, shape = shape)
                    .clip(shape)
                    .background(backgroundColor)
            ) {
                Box(modifier = Modifier.align(Alignment.TopStart)) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
                Box(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        "Мифы о курении",
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(12.dp)
            ) {
                mythItems.forEach { myth ->
                    val mythId = myth.id
                    val mythTitle = myth.name
                    val mythDescription = myth.text
                    val imgSize = 60.dp

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onShowTipDialog.value = true
                            onFactTransit.value = myth
                        }
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        TipImage(mythId, imgSize)
                        Spacer(Modifier.width(20.dp))
                        Column(modifier = Modifier.height(IntrinsicSize.Min)) {
                            Text(
                                text = mythTitle,
                                fontFamily = MyTextStyles.mRobotoFontFamily,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = true
                            )
                            Text(
                                text = mythDescription,
                                fontFamily = MyTextStyles.mRobotoFontFamily,
                                fontWeight = FontWeight.Light,
                                textAlign = TextAlign.Start,
                                maxLines = 2,
                                lineHeight = 14.sp,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = true,
                                fontSize = 12.sp
                            )
                        }
                    }
                    HorizontalDivider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }

    if (onShowTipDialog.value) {
        MyDialog(
            onDismissRequest = { onShowTipDialog.value = false },
            inputType = onFactTransit.value
        )
    }
}


