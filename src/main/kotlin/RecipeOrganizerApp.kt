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



@Composable
fun RecipeOrganizerApp() {
   MaterialTheme {
      Column(modifier = Modifier.padding(16.dp).fillMaxWidth(),verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
      Text("Recipe Organizer", style = MaterialTheme.typography.headlineLarge)
      Text("A place to keep your recipes!")
         Button(onClick = {println("Add Recipe button clicked!")}) {
            Text("Add Recipe")
         }
   } }


}
