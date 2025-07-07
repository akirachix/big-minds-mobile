package com.bigminds.safigreens
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.*
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.graphics.Shape
import  androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings

data class Product(
    val product_id: Int,
    val name: String,
    val product_image: String,
    val category: String,
    val unit: String
)


interface ProductApiService {
    @GET("products/")
    suspend fun getProducts(): List<Product>
}

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://safi-greens-app.onrender.com/api/")
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

val CustomGreen = Color(0xFF003A2A)

@Composable
fun LandingPage(viewModel: ProductViewModel = viewModel()) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var selectedNavIndex by remember { mutableStateOf(0) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent()
        }
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    selectedIndex = selectedNavIndex,
                    onItemSelected = { selectedNavIndex = it }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
//                    .padding(bottom = 24.dp)
            ) {
                HeroSection(
                    searchQuery = viewModel.searchQuery,
                    onSearchChange = viewModel::onSearchQueryChange,
                    selectedCategory = viewModel.selectedCategory,
                    onCategorySelect = viewModel::onCategorySelected,
                    onMenuClick = {
                        coroutineScope.launch { drawerState.open() }
                    }
                )
                if (viewModel.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(8.dp),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(viewModel.filteredList) { product ->
                            ProductCard(product)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun HeroSection(searchQuery: String,
                onSearchChange: (String) -> Unit,
                selectedCategory: String,
               onCategorySelect: (String) -> Unit,
                onMenuClick: () -> Unit) {
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
                        fontWeight = FontWeight.Bold,
                        color = CustomGreen
                    )
                    Text(
                        text = "Kisumu, Kenya",
                        fontSize = 12.sp,
                        color = CustomGreen
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
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {

            Box(
                modifier = Modifier
                    .width(24.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
                    .background(CustomGreen)

            )

            Spacer(modifier = Modifier.width(16.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = CustomGreen),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .weight(1f) // fill remaining space
                    .fillMaxHeight()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img),
                        contentDescription = "Farmer",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Shop Smarter,",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                        Text(
                            text = "Save more!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .width(24.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp))
                    .background(CustomGreen)
            )
        }


        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White, shape = RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    leadingIcon = {
                        Icon(Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = CustomGreen)
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
        Spacer(modifier = Modifier.height(16.dp))
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
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(4.dp)
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
                fontWeight = FontWeight.SemiBold,
                text = product.name,
                color = CustomGreen,
                fontSize = 18.sp
            )
            Row (verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = "30 KSH",
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "1 ${product.unit}",
                    color = Color.Black,
                    fontSize = 14.sp

                )
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
                Text(text = "Add", fontSize = 16.sp)
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
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun BottomNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val icons = listOf(
        R.drawable.ic_home,
        R.drawable.ic_cart,
        R.drawable.ic_orders,
        R.drawable.ic_bell,
        R.drawable.ic_store
    )
    val labels = listOf("Home", "Cart", "Orders", "Notification", "Shop")
    Surface(
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 0.dp, end = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            icons.forEachIndexed { index, icon ->
                val isSelected = selectedIndex == index
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) CustomGreen else Color.Transparent)
                        .border(1.dp, CustomGreen, CircleShape)
                        .clickable { onItemSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = labels[index],
                        tint = if (isSelected) Color.White else CustomGreen,
                        modifier = Modifier.size(24.dp)
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
































