
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.screens.ImageSliderScreen


@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth()) {
            ImageSliderScreen()
        }
    }
}