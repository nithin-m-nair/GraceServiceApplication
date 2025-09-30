package com.horizon.service.serviceapplication.screens

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.horizon.service.serviceapplication.R
import com.horizon.service.serviceapplication.utils.NetworkImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedProductScreen(
    navController: NavController,
    productName: String,
    productDesc: String,
    manufacturer: String,
    warranty: String,
    supportAvailable: Boolean,
    contactNumber: String,
    imageResId: String
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "Featured Product Details",
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
        ) {
            Spacer(Modifier.height(8.dp))

            // Product Image (fixed height instead of weight)
            NetworkImage(
                url = imageResId,
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // fixed height for consistency
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            )

            Spacer(Modifier.height(16.dp))

            // Card with product details (auto expand for text)
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() // ✅ card grows with text
                    .padding(horizontal = 12.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.texture),
                        contentDescription = "Card Background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            productName,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            productDesc,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )

                        Spacer(Modifier.height(20.dp))

                        // manufacturer
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.factory),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Manufacturer: ") }
                                    append(manufacturer)
                                },
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        // warranty
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.warranty),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Warranty: ") }
                                    append(warranty)
                                },
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        // support
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.support),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Support: ") }
                                    append(if (supportAvailable) "24/7 Available" else "Not Available")
                                },
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        ContactRowWithCallButton(contactNumber)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // WhatsApp Button (fixed height, not weighted)
            Button(
                onClick = {
                    val message = """
            Hello! 👋

            I am interested in your product and would like more information.

            Product Details:
            - Product Name: $productName
            - Model/ID: $manufacturer

            Could you please provide more details about pricing, availability, and delivery options?

            Thank you!
        """.trimIndent()
                    sendWhatsAppMessages(
                        context = context,
                        phone = contactNumber,
                        message = message
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.whatsapp),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
                Spacer(Modifier.width(8.dp))
                Text("Enquire on WhatsApp", color = Color.White)
            }
        }

    }
}

fun sendWhatsAppMessages(context: Context, phone: String, message: String) {
    try {
        val uri = Uri.parse("https://wa.me/$phone?text=${Uri.encode(message)}")
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage("com.whatsapp")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "WhatsApp not installed.", Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun ContactRowWithCallButton(contactNumber: String) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(end = 10.dp), // Added padding for better spacing
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- Left side: Icon + Text ---
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = R.drawable.phone), // Replace with your icon
                contentDescription = "Contact Info Icon",
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = buildAnnotatedString {
                    // Apply a bold style only to this part
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Contact: ")
                    }
                    // This part remains in the default style
                    append(contactNumber)
                },
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // --- Right side: Circular Call Button ---
        Card(
            onClick = {
                // Creates an intent to open the dialer with the number
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$contactNumber")
                }
                context.startActivity(intent)
            },
            modifier = Modifier.size(56.dp), // A standard size for floating action buttons
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.White // The card's background color
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp // A nice shadow effect
            )
        ) {
            // Box is used to center the content within the Card
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.phone), // Replace with your icon
                    contentDescription = "Call",
                    tint = Color(0xFF25D366), // A nice "call" green, similar to WhatsApp
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
