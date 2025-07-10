package com.bigminds.safigreens
import android.R.attr.delay
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import  androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay


data class Product(
    val product_id: Int,
    val name: String,
    val product_image: String,
    val category: String,
    val unit: String
)


val CustomGreen = Color(0xFF003A2A)


sealed class Screen(val route: String, val icon: Int, val label: String) {
    object Home : Screen("home", R.drawable.ic_home, "Home")
    object Cart : Screen("cart", R.drawable.ic_cart, "Cart")
    object Orders : Screen("orders", R.drawable.ic_orders, "Orders")
    object Notifications : Screen("notifications", R.drawable.ic_bell, "Notification")
    object Shop : Screen("shop", R.drawable.ic_store, "Shop")
}
val bottomNavItems = listOf(
    Screen.Home,
    Screen.Cart,
    Screen.Orders,
    Screen.Notifications,
    Screen.Shop
)

interface ProductApiService {
    @GET("products/")
    suspend fun getProducts(): List<Product>
}


object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://safigreens-ae7369bd05fc.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }
}


class ProductViewModel : ViewModel() {
    var productList by mutableStateOf<List<Product>>(emptyList())
        private set
    var searchQuery by mutableStateOf("")
    var selectedCategory by mutableStateOf("All")


    val filteredList: List<Product>
        get() = productList.filter {
            (selectedCategory == "All" || it.category == selectedCategory) &&
                    it.name.contains(searchQuery, ignoreCase = true)
        }
    var isLoading by mutableStateOf(true)
    init {
        getProducts()
    }
    private fun getProducts() {
        viewModelScope.launch {
            try {
                productList = RetrofitInstance.api.getProducts()
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
    fun onSearchQueryChange(query: String) {
        searchQuery = query
    }
    fun onCategorySelected(category: String) {
        selectedCategory = category
    }
}


@Composable
fun LandingPage(viewModel: ProductViewModel = viewModel()) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent() }
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        GreetingBar(onMenuClick = {
                            coroutineScope.launch { drawerState.open() }
                        })
                        CardCarousel()
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 64.dp)
                        ) {
                            stickyHeader {
                                StickySearchAndCategory(
                                    searchQuery = viewModel.searchQuery,
                                    onSearchChange = viewModel::onSearchQueryChange,
                                    selectedCategory = viewModel.selectedCategory,
                                    onCategorySelect = viewModel::onCategorySelected
                                )
                            }
                            if (viewModel.isLoading) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 100.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            } else {
                                val items = viewModel.filteredList.chunked(2)
                                items.forEach { rowItems ->
                                    item {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 8.dp, vertical = 6.dp),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            rowItems.forEach { product ->
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                ) {
                                                    ProductCard(product)
                                                }
                                            }
                                            if (rowItems.size == 1) {
                                                Spacer(modifier = Modifier.weight(1f))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                composable(Screen.Cart.route) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Cart Page")
                    }
                }
                composable(Screen.Orders.route) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Orders Page")
                    }
                }
                composable(Screen.Notifications.route) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Notifications Page")
                    }
                }
                composable(Screen.Shop.route) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Shop Page")
                    }
                }
            }
        }
    }
}


@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = product.product_image,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .padding(4.dp)
                    .clip(CircleShape)


            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = product.name,
                color = CustomGreen,
                fontSize = 18.sp
            )
            Row (verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = "30 KSH",
                    color = Color.Black,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "1 ${product.unit}",
                    color = Color.Black,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodySmall,                )
            }


            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* TODO: Handle Add */ },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomGreen,
                    contentColor = Color.White)
            ) {
                Text(
                    text = "Add",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(32.dp))
                Icon(
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = "Cart",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}


@Composable
fun CategoryItem(
    imageRes: Int? = null,
    iconRes: Int? = null,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clickable { onClick() }
    ) {


        Box(
            modifier = Modifier
                .size(60.dp)
                .drawBehind {
                    if (isSelected) {
                        val strokeWidth = 4.dp.toPx()
                        val radius = size.minDimension / 2
                        val paint = Paint().apply {
                            color = Color.White
                            style = PaintingStyle.Stroke
                            this.strokeWidth = strokeWidth
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        }
                        drawCircle(
                            color = Color.White,
                            center = center,
                            radius = radius - strokeWidth / 2,
                            style = Stroke(
                                width = strokeWidth,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            )
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (iconRes != null) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    tint = Color.White,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(CustomGreen)
                        .padding(12.dp)
                )
            } else if (imageRes != null) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = label,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = CustomGreen,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    Surface(shadowElevation = 8.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            bottomNavItems.forEach { screen ->
                val isSelected = screen.route == currentDestination
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) CustomGreen else Color.Transparent)
                        .border(1.dp, CustomGreen, CircleShape)
                        .clickable {
                            if (currentDestination != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.label,
                        tint = if (isSelected) Color.White else CustomGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun DrawerContent() {
    Column(
        modifier = Modifier
            .fillMaxHeight(1f)
            .fillMaxWidth(0.5f)
            .background(Color.White, shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .padding(top =32.dp)
    ) {
        Row (verticalAlignment = Alignment.CenterVertically){
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile picture",
                tint = CustomGreen,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Profile", color = CustomGreen, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(8.dp))


        }
        Spacer(modifier = Modifier.height(8.dp))
        Row (verticalAlignment = Alignment.CenterVertically){
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Profile picture",
                tint = CustomGreen,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Settings", color = CustomGreen, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(8.dp))


        }
    }
}


data class PromoCard(
    val imageRes: Int,
    val title: String,
    val subtitle: String
)

@Composable
fun PromoCardItem(promo: PromoCard, reverseLayout: Boolean = false) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CustomGreen),
        modifier = Modifier
            .width(300.dp)
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (!reverseLayout) {
                PromoText(promo)
                Spacer(modifier = Modifier.width(12.dp))
                PromoImage(promo)
            } else {
                PromoImage(promo)
                Spacer(modifier = Modifier.width(12.dp))
                PromoText(promo)
            }
        }
    }
}

@Composable
private fun PromoText(promo: PromoCard) {
    Column() {
        Text(
            text = promo.title,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = promo.subtitle,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun PromoImage(promo: PromoCard) {
    Image(
        painter = painterResource(id = promo.imageRes),
        contentDescription = promo.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(100.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(12.dp))
    )
}


@Composable
fun CardCarousel() {
    val promoList = listOf(
        PromoCard(R.drawable.img, "Shop Smarter,", "Save More!"),
        PromoCard(R.drawable.fresh, "Fresh Fruits,", "Delivered Daily!"),
        PromoCard(R.drawable.green_grocery, "Green Goodness,", "Right to Your Door!")
    )
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000L)
            val nextIndex = listState.firstVisibleItemIndex + 1
            coroutineScope.launch {
                listState.animateScrollToItem(nextIndex)
            }
        }
    }
    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(count = Int.MAX_VALUE) { index ->
            val promo = promoList[index % promoList.size]
            val reverse = index % promoList.size != 1 // Only reverse second card
            PromoCardItem(promo = promo, reverseLayout = reverse)
        }
    }
}

@Composable
fun GreetingBar(onMenuClick:()-> Unit){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF4F8F5))
            .padding(bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "Menu",
                tint = CustomGreen,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onMenuClick() }
            )
            Column(modifier = Modifier.offset(x = (-80).dp)) {
                Text(
                    text = "Hello Lucy,",
                    fontSize = 16.sp,
                    color = CustomGreen,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Kisumu, Kenya",
                    fontSize = 12.sp,
                    color = CustomGreen,
                    style = MaterialTheme.typography.bodyMedium
                )

            }

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile picture",
                tint = CustomGreen,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}


@Composable
fun StickySearchAndCategory(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color(0xFFF4F8F5))
            .padding(vertical = 16.dp)
    ) {
        SearchBar(searchQuery, onSearchChange)
        Spacer(modifier = Modifier.height(8.dp))
        CategorySelectorRow(selectedCategory, onCategorySelect)
    }
}


@Composable
fun CategorySelectorRow(selectedCategory: String, onCategorySelect: (String) -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryItem(iconRes = R.drawable.list_solid,
            label = "All",
            isSelected = selectedCategory == "All",
            onClick = {onCategorySelect("All")})
        CategoryItem(imageRes = R.drawable.fruits,
            label = "Fruits",
            isSelected = selectedCategory == "Fruit",
            onClick = {onCategorySelect("Fruit")})
        CategoryItem(imageRes = R.drawable.vegetables,
            label = "Vegetables",
            isSelected = selectedCategory == "Vegetable",
            onClick = {onCategorySelect("Vegetable")})
        CategoryItem(imageRes = R.drawable.cereal,
            label = "Cereals",
            isSelected = selectedCategory == "Cereal",
            onClick = {onCategorySelect("Cereal")})
    }
}


@Composable
fun SearchBar(searchQuery: String, onSearchChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.White, shape = RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = CustomGreen)
            },
            trailingIcon = {
                Icon(painter = painterResource(id = R.drawable.sliders_solid),
                    contentDescription = "slider",
                    modifier = Modifier.size(16.dp),
                    tint = CustomGreen)
            },
            placeholder = { Text("Search", fontSize = 16.sp) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = CustomGreen,
                unfocusedBorderColor = CustomGreen,
                focusedTextColor = CustomGreen
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .height(48.dp),
        )
    }
}




