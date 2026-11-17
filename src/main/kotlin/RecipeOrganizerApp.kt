import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.text.Normalizer


@Composable
fun RecipeOrganizerApp() {
   var currentScreen = remember{mutableStateOf("home")}
   MaterialTheme {

   when (currentScreen.value) {
      "home" -> HomeScreen({newScreen: String -> currentScreen.value = newScreen})
      "addRecipe" -> AddRecipeScreen({newScreen: String -> currentScreen.value = newScreen})
      "recipeList" -> RecipeListScreen({newScreen: String -> currentScreen.value = newScreen})
      "search" -> SearchScreen({newScreen: String -> currentScreen.value = newScreen})
      "manageTags" -> ManageTagsScreen({newScreen: String -> currentScreen.value = newScreen})
      else -> HomeScreen({newScreen: String -> currentScreen.value = newScreen})
   }

   }
}