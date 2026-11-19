import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RecipeListScreen(onNavigate: (String) -> Unit, recLib: RecipeLibrary, recRepo: RecipeRepository) {
    println("Test")
    val recipeList = recLib.returnAllRecipes()
    for (recipe in recipeList) {
        println("${recipe.title} - ${recipe.id}")

    }
    val recipeListSize = recipeList.size
    val selectedRecipe: MutableState<Recipe?> = remember { mutableStateOf<Recipe?>(null) }

    Column(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
                     Text(recipeListSize.toString())


            Text("Recipe List Screen")

        recipeList.forEach { recipe ->
            val currentRecipe = selectedRecipe.value
            Card(modifier = Modifier.padding(10.dp)) {
                Text(recipe.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp).clickable {
                    if (currentRecipe != null && currentRecipe.id == recipe.id) {
                        selectedRecipe.value = null
                    } else {
                        selectedRecipe.value = recipe
                    }
                })

            }
            val recipeValue = selectedRecipe.value
            if (recipeValue != null && recipe.id == recipeValue.id) {
                Card {
                    Text(recipeValue.description)

                    Text("Servings: ${recipeValue.servings.toString()}", fontWeight = FontWeight.Bold)
                    Text("Total time: ${recipeValue.totalTime.toString()}", fontWeight = FontWeight.Bold)
                    Text("Ingredients:", fontWeight = FontWeight.Bold)
                    recipeValue.ingredients.forEach { ingredient ->
                        Text("${ingredient.quantity} ${ingredient.unit ?: ""} ${ingredient.name}")
                    }
                    Text("Instructions:", fontWeight = FontWeight.Bold)
                    recipeValue.instructions.forEach { instruction ->
                        Text(instruction.step)
                    }
                    Text("Tags:", fontWeight = FontWeight.Bold)
                    recipeValue.tags.forEach { tag ->
                        Text(tag.name)
                    }

                    Button(onClick = {
                        println("BEFORE DELETE BUTTON CLICKED")
                        recLib.removeRecipe(recipeValue.id)
                        println("AFTER DELETE BUTTON CLICKED")
//                        selectedRecipe.value = null
                    }) {
                        Text("Delete Recipe")
                    }
                }
            }
        }

                Button(onClick = { onNavigate("home") }) {
                    Text("Back")

            }
        }
    }
