import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RecipeListScreen(onNavigate: (String) -> Unit, recLib: RecipeLibrary, recRepo: RecipeRepository, onEditRecipe: (Recipe) -> Unit, savedRecipeId: String? = null) {

    val recipeList = recLib.returnAllRecipes().sortedBy{recipe-> recipe.title.lowercase()}
    val selectedRecipe: MutableState<Recipe?> = remember(savedRecipeId) { mutableStateOf(savedRecipeId?.let{recLib.findRecipeById(it)}) }
    val listState = rememberLazyListState()
    val savedRecipeIndex= recipeList.indexOfFirst{it.id ==savedRecipeId}
    LaunchedEffect(savedRecipeId) {
        if (savedRecipeIndex != -1) {
        listState.animateScrollToItem(savedRecipeIndex + 1)
    }}
    LazyColumn(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        item { Text("All Recipes") }


        items(recipeList) { recipe ->
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
                    RecipeDetails(recipeValue)

                    Button(onClick = {

                        recLib.removeRecipe(recipeValue.id)
                        recRepo.saveLibrary()
                        selectedRecipe.value = null
                    }) {
                        Text("Delete Recipe")
                    }

                    Button(onClick = {onEditRecipe(recipeValue)}) {
                        Text("Edit Recipe")
                    }
                }
            }
        }
        item {
            Button(onClick = { onNavigate("home") }) {
                Text("Home")

            }
        }
    }
}