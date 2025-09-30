package com.horizon.service.serviceapplication.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.horizon.service.serviceapplication.R
import com.horizon.service.serviceapplication.Repository.Repo
import com.horizon.service.serviceapplication.model.FeaturedItemData
import com.horizon.service.serviceapplication.network.RetrofitApiService
import com.horizon.service.serviceapplication.utils.NetworkImage
import com.horizon.service.serviceapplication.viewModels.HomeViewModel
import com.horizon.service.serviceapplication.viewModels.HomeViewModelFactory
import com.horizon.service.serviceapplication.viewModels.PromoUiState
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavController?=null,
               viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                   factory = HomeViewModelFactory(Repo(RetrofitApiService())) // for Android
               )) {
    val promoState by viewModel.promoState.collectAsState()
    // Wrap entire layout in a Box so we can layer components
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 🔹 Screen Background (solid green)
        Box(
            modifier = Modifier
                .fillMaxSize()
        )

        // 🔹 Foreground Content
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            // AppBar Section with solid color
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(116.dp) // solid background color
            ) {

                Spacer(Modifier.height(60.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 20.dp, 20.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left logo
                    Image(
                        painter = painterResource(id = R.drawable.logo_text),
                        contentDescription = "Left Logo",
                        modifier = Modifier.size(100.dp)
                    )

                    // Right logo
                    Image(
                        painter = painterResource(id = R.drawable.settings),
                        contentDescription = "Right Logo",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                navController?.navigate("support")
                            },
                        colorFilter = ColorFilter.tint(Color(0xFF000000))
                    )
                }
                Divider(
                    color = Color.White,
                    thickness = 5.dp,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }


            // Promo Content (middle part, stays green)
            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
               // PromoContent(navController!!)
                when (promoState) {
                    is PromoUiState.Error ->Text(
                        text = (promoState as PromoUiState.Error).message,
                        color = Color.Red
                    )
                    PromoUiState.Loading -> CircularProgressIndicator()
                    is PromoUiState.Success -> PromoContent(
                        navController!!,
                        (promoState as PromoUiState.Success).items
                    )
                }
            }

            // Bottom Card with texture background
            Card(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            )
            {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.texture),
                        contentDescription = "Bottom Card Texture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 24.dp, end = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Need Service?",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.Black
                        )

                        Card(
                            modifier = Modifier
                                .wrapContentWidth()
                                .height(60.dp)
                                .clickable {
                                    navController?.currentBackStackEntry?.savedStateHandle?.set("data", (promoState as PromoUiState.Success).items)
                                    navController?.navigate("serviceRequest")
                                           },
                            shape = RoundedCornerShape(50.dp),
                            elevation = CardDefaults.cardElevation(0.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White, // background
                                contentColor = Color.Black    // text/icon color
                            ),
                            border = BorderStroke(1.dp, Color.LightGray)
                        ) {
                            Row(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.request_service),
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp),
                                    tint = Color.Unspecified
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Request Service",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Normal,
                                        color = Color.Black
                                    )
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun PromoContent(navController: NavController, promoItems: List<FeaturedItemData>) {

    val pagerState = rememberPagerState(pageCount = { promoItems.size })

    // 🔄 Auto-scroll effect
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000) // 3 seconds per page
            val nextPage = (pagerState.currentPage + 1) % promoItems.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fill,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PromoCard(
                    item = promoItems[page],
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    navController = navController
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Indicator dots
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(promoItems.size) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index) Color.Black else Color.LightGray
                        )
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}

@Composable
fun PromoCard(item: FeaturedItemData, modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = item.name.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = item.description.toString(),
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                letterSpacing = 1.sp,
                lineHeight = 20.sp
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("promoItem", item)
                navController.navigate("featuredProduct")
            }
        ) {
            Text(
                text = "know more",
                color = Color.Black,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        NetworkImage(
            url = item.image.toString(),
            contentDescription = item.name.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp), // set the height you want
            contentScale = ContentScale.Fit
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
