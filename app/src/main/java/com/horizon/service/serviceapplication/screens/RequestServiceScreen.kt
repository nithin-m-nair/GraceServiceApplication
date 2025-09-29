package com.horizon.service.serviceapplication.screens

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.horizon.service.serviceapplication.R
import com.horizon.service.serviceapplication.model.FeaturedItemData
import androidx.core.graphics.toColorInt
import com.horizon.service.serviceapplication.model.ServiceRequest
import com.horizon.service.serviceapplication.utils.showCustomToast
import com.horizon.service.serviceapplication.viewModels.ServiceRequestUiState
import com.horizon.service.serviceapplication.viewModels.ServiceRequestViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceRequestScreen(navController: NavController,
                         productItem: List<FeaturedItemData>,
                         viewModel: ServiceRequestViewModel) {
    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedIndex by remember { mutableStateOf(-1) }

    val context = LocalContext.current
    val requestState by viewModel.requestState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Service Request",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
                    }
                },
                actions = { Spacer(Modifier.width(48.dp)) },
                colors = TopAppBarDefaults.topAppBarColors(titleContentColor = Color.Black)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.texture),
                contentDescription = "Screen Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            ) {
                // Name
                item {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Name") },
                        placeholder = { Text("Enter your Name") },
                        modifier = Modifier.fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(8.dp)),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            cursorColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Phone Number
                item {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone Number") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = "Phone") },
                        placeholder = { Text("Enter your 10-digit mobile number") },
                        modifier = Modifier.fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(8.dp)),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            cursorColor = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Location
                item {
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Location") },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = "Location") },
                        placeholder = { Text("Enter your city/area") },
                        modifier = Modifier.fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(8.dp)),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            cursorColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Product Type
                item {
                    Text("Product Type", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                itemsIndexed(productItem) { index, item ->
                    val isSelected = index == selectedIndex
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color.LightGray),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { selectedIndex = index }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedIndex = index },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFF65984B),
                                    unselectedColor = Color.Gray
                                )
                            )
                            Column(modifier = Modifier.padding(start = 8.dp)) {
                                Text(item.name.toString(), fontWeight = FontWeight.Bold)
                                Text(item.responseTime.toString(), fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }

                // Description
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Service Description", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("Describe the issue or service you need...") },
                        modifier = Modifier.fillMaxWidth().height(120.dp)
                            .background(Color.White, RoundedCornerShape(8.dp)),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            cursorColor = Color.Black
                        ),
                        leadingIcon = { Icon(Icons.Default.Edit, contentDescription = "Description") },
                        maxLines = 5
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Submit Button
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clickable {
                                when {
                                    name.isBlank() || phone.isBlank() || location.isBlank() || description.isBlank() || selectedIndex == -1 -> {
                                        showCustomToast(context, "All fields are mandatory", false)
                                    }
                                    !phone.matches(Regex("^[0-9]{10}\$")) -> {
                                        showCustomToast(context, "Enter a valid 10-digit phone number", false)
                                    }
                                    else -> {
                                        // 🔹 Call API here
                                        val request = ServiceRequest(
                                            phone = phone,
                                            description = description,
                                            productID = productItem[selectedIndex].id.toString(),
                                            location = location,
                                            date = currentDate,
                                            name = name
                                        )
                                        viewModel.submitRequest(request)
                                    }
                                }
                            },
                        shape = RoundedCornerShape(50.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
                        border = BorderStroke(1.dp, Color.LightGray)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.tick),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Submit Request")
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
    // Show Loading / Success / Error feedback
    when (requestState) {
        ServiceRequestUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF65984B), // match your theme
                    strokeWidth = 4.dp
                )
            }
        }

        is ServiceRequestUiState.Success -> {
            LaunchedEffect(Unit) {
                showCustomToast(context, "Request Submitted Successfully!", true)
                viewModel.resetState()
                navController.popBackStack()
            }
        }

        is ServiceRequestUiState.Error -> {
            val errorMessage = (requestState as ServiceRequestUiState.Error).message
            LaunchedEffect(errorMessage) {
                showCustomToast(context, errorMessage, false)
                viewModel.resetState()
            }
        }

        else -> {}
    }
}



