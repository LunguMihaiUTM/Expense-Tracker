package com.dog.expensetracker.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dog.expensetracker.R
import com.dog.expensetracker.navigation.LocalRootNavigator
import com.dog.expensetracker.navigation.RootNavDestinations

@Composable
fun WelcomeScreen() {
    val navController = LocalRootNavigator.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 70.dp)
        ) {

            Box(
                contentAlignment = Alignment.Center, // centers the image inside
                modifier = Modifier
                    .size(300.dp) // total size of the circle
                    .background(
                        color = Color(0xFFFDEDFF), // light pink (you can change hex)
                        shape = CircleShape
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wallet_image),
                    contentDescription = "Wallet",
                    modifier = Modifier.size(230.dp) // smaller than circle so it fits inside
                )
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ){
                // Title
                Text(
                    text = "Save your money with\nExpense Tracker",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    lineHeight = 38.sp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))

                // Subtitle
                Text(
                    text = "Save money! The more your money works for you, the less you have to work for money.",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 24.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }


            // Button
            Button(
                onClick = {
                    navController.navigate(RootNavDestinations.Home)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B61FF)), // Purple button
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .height(85.dp)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(text = "Let's Start", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun WelcomeScreenPreview() {
//    WelcomeScreen(rememberNavController())
//}
