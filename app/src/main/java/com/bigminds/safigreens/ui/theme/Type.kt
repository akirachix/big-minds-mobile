import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bigminds.safigreens.R


val Nunito = FontFamily(
    Font(R.font.nunito, FontWeight.Normal),

)


val AppTypography = Typography(
    displayLarge = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Bold, fontSize = 32.sp),
    displayMedium = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Medium, fontSize = 28.sp),
    displaySmall = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Normal, fontSize = 24.sp),
    titleLarge = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Bold, fontSize = 20.sp),
    titleMedium = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Medium, fontSize = 18.sp),
    titleSmall = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    bodyLarge = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    bodyMedium = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Normal, fontSize = 14.sp),
    bodySmall = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Normal, fontSize = 12.sp),
    labelLarge = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Normal, fontSize = 14.sp),
    labelMedium = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Normal, fontSize = 12.sp),
    labelSmall = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Normal, fontSize = 11.sp)
)