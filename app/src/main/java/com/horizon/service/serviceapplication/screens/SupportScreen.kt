package com.horizon.service.serviceapplication.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Divider
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import com.horizon.service.serviceapplication.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpSupportScreen(
    navController: NavController, // or pass onBack callback
    context: Context
) {
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            "Help & Support",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }
                    },
                    actions = { Spacer(Modifier.width(48.dp)) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        titleContentColor = Color.Black
                    )
                )
                Divider(
                    color = Color.White,
                    thickness = 5.dp
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp)
        )
        {

            // Header Section
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // 🔹 Background Image
                    Image(
                        painter = painterResource(id = R.drawable.texture),
                        contentDescription = "Card Background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )

                    // 🔹 Foreground Content
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.support),
                            contentDescription = "Help",
                            tint = Color(0xFF4CAF50), // green
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "We're Here to Help",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Get in touch with our support team for any questions or assistance",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }


            Spacer(Modifier.height(24.dp))

            // Contact Info Section
            Text(
                "Contact Information",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(Modifier.height(12.dp))

            // Phone card
            ContactCard(
                icon = R.drawable.phone,
                title = "Phone Support",
                value = "+91 7559940557",
                isPhone = true,
                onCallClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:+917559940557")
                    }
                    context.startActivity(intent)
                },
                onCopyClick = {
                    val clipboard =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Phone", "+91 7559940557")
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "Number copied", Toast.LENGTH_SHORT).show()
                }
            )

            Spacer(Modifier.height(12.dp))

            // Email card
            ContactCard(
                icon = R.drawable.email,
                title = "Email Support",
                value = "graceengineeringindustries@yahoo.com",
                isPhone = false,
                onCallClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:graceengineeringindustries@yahoo.com")
                    }
                    context.startActivity(intent)
                },
                onCopyClick = {
                    val clipboard =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Email", "graceengineeringindustries@yahoo.com")
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "Email copied", Toast.LENGTH_SHORT).show()
                }
            )

            Spacer(Modifier.height(24.dp))

            // Support Hours
            Text(
                "Support Hours",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(Modifier.height(12.dp))
            SupportHoursRow("Monday – Friday", "9:00 AM – 6:00 PM", true)
            SupportHoursRow("Saturday", "10:00 AM – 4:00 PM", true)
            SupportHoursRow("Sunday", "Closed", false)
        }
    }
}

@Composable
fun ContactCard(
    icon: Int,
    title: String,
    value: String,
    isPhone: Boolean,
    onCallClick: () -> Unit,
    onCopyClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.texture),
                contentDescription = "Card Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left circle icon
                    Icon(
                        imageVector = if (isPhone) Icons.Default.Call else Icons.Default.Email,
                        contentDescription = if (isPhone) "Call" else "Email")


                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(value, style = MaterialTheme.typography.bodySmall)
                }

                // Action icons
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onCallClick() }
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                IconButton(onClick = onCopyClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.copy), // your headset icon
                        contentDescription = "Copy",
                        tint = Color(0xFF4CAF50), // green
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SupportHoursRow(day: String, time: String, open: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(day, style = MaterialTheme.typography.bodyMedium)
        Text(
            time,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if (open) Color(0xFF4CAF50) else Color.Red
            )
        )
    }
}
