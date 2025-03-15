package com.example.bt_tuan3_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bt_tuan3_2.ui.theme.BT_TUAN3_2Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BT_TUAN3_2Theme {
                MainScreen()
            }
        }
    }
}




// ✅ Quản lý màn hình
@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf("splash") }

    LaunchedEffect(Unit) {
        delay(2000) // Chờ 2 giây rồi chuyển màn hình
        currentScreen = "onboarding"
    }

    when (currentScreen) {
        "splash" -> SplashScreen { currentScreen = "onboarding" }
        "onboarding" -> OnboardingScreen { currentScreen = "home" }
        "home" -> HomeScreen()
    }
}


@Composable
fun SplashScreen(onSplashEnd: () -> Unit) {
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000) // Chờ 2 giây rồi chuyển màn hình
        onSplashEnd()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            modifier = Modifier.size(250.dp),
            contentDescription = "UTH logo"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "UTH SmartTasks",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
    }
}

// ✅ Dữ liệu cho các trang Onboarding
data class OnboardingPage(val image: Int, val title: String, val description: String)

val onboardingPages = listOf(
    OnboardingPage(
        image = R.drawable.banner, // 🔹 Thay bằng ảnh thực tế
        title = "Easy Time Management",
        description = "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first"
    ),
    OnboardingPage(
        image = R.drawable.banner2, // 🔹 Thay bằng ảnh thực tế
        title = "Increase Work Effectiveness",
        description = "Time management and the determination of more important tasks will give your job statistics better and always improve"
    ),
    OnboardingPage(
        image = R.drawable.banner3, // 🔹 Thay bằng ảnh thực tế
        title = "Reminder Notification",
        description = "The advantage of this application is that it also provides reminders for you so you don’t forget to keep doing your assignments well"
    )
)

// ✅ Màn hình Onboarding
@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    var currentPage by remember { mutableStateOf(0) }
    val lastPage = onboardingPages.lastIndex

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp , 66.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 🔹 Chấm tròn chỉ số trang
            Row {
                repeat(onboardingPages.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .padding(2.dp)
                            .background(
                                if (index == currentPage) Color.Blue else Color.LightGray,
                                shape = CircleShape
                            )
                    )
                }
            }

            // 🔹 Nút Skip
            Text(
                text = "skip",
                color = Color.Blue,
                fontSize = 16.sp,
                modifier = Modifier.clickable { onFinish() }
            )
        }

        Spacer(modifier = Modifier.height(46.dp))

        // 🔹 Ảnh
        Image(
            painter = painterResource(id = onboardingPages[currentPage].image),
            modifier = Modifier.size(250.dp),
            contentDescription = "Onboarding Image"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Tiêu đề & mô tả
        Text(
            text = onboardingPages[currentPage].title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = onboardingPages[currentPage].description,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Thanh điều hướng + Skip


        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Nút điều hướng (Back / Next / Get Started)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 🔹 Nút Back (ẩn ở trang đầu tiên)
            if (currentPage > 0) {
                Button(
                    onClick = { currentPage-- },
                    colors = ButtonDefaults.buttonColors(Color.LightGray),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "←", color = Color.White, fontSize = 18.sp)
                }
            } else {
                Spacer(modifier = Modifier.width(60.dp))
            }

            // 🔹 Nút Next / Get Started
            Button(
                onClick = {
                    if (currentPage < lastPage) {
                        currentPage++
                    } else {
                        onFinish() // Chuyển sang màn hình chính
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Blue),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = if (currentPage < lastPage) "Next →" else "Get Started",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}


// ✅ Màn hình chính sau Onboarding
@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome to UTH SmartTasks!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen {}
}
@Preview(showBackground = true)
@Composable
fun PreviewOnboardingScreen() {
    OnboardingScreen {}
}
@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}
