import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button



@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {

    Column( modifier = Modifier.padding(16.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text("Recipe Organizer", style = MaterialTheme.typography.headlineLarge)

        Text("A place to keep your recipes!")

        Button(onClick = {onNavigate("addRecipe")}) {
            Text("Add Recipe")

        }
        Button(onClick ={onNavigate("recipeList")}) {
            Text("All Recipes")

        }
        Button(onClick ={onNavigate("search")}) {
            Text("Search")

        }
        Button(onClick ={onNavigate("manageTags")}) {
            Text("Manage Tags")

        }
    }


}