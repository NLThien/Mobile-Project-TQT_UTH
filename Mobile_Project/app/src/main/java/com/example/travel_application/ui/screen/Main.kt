package com.example.travel_application.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travel_application.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import android.R.id.bold
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(navController: NavController) {
    val navController = NavController(LocalContext.current)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Image(
//            painter = painterResource(id = androidx.compose.foundation.layout.R.drawable.logo),
//            contentDescription = "Logo",
//            modifier = Modifier.size(300.dp)
//        )

        Text("Trang chủ ứng dụng", fontSize = 30.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

        Text(
            text = "Trung chủ ứng dụng du lịch",
            fontSize = 16.sp,
            modifier = Modifier
                .padding(50.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.dp))

//        Button(onClick = { navController.navigate(Screen.ComponentList.route) },
//            modifier = Modifier
//                .width(340.dp)
//                .height(50.dp)
//        ) {
//            Text("Push", fontSize = 20.sp)
//        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    val navController = NavController(LocalContext.current)
    MainScreen(navController = navController)
}