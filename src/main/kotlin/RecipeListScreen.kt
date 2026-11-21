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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign

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
    Column {
        Button(onClick = { onNavigate("home") },
            Modifier.align(Alignment.Start).padding(16.dp)) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Return Home"
            )}
    LazyColumn(
        modifier = Modifier.padding(16.dp).fillMaxWidth().weight(1f),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        item { Text("All Recipes", Modifier.fillMaxWidth(),style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center ) }


        items(recipeList) { recipe ->
            val currentRecipe = selectedRecipe.value
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clickable{
                if (currentRecipe != null && currentRecipe.id == recipe.id) {
                    selectedRecipe.value = null
                } else {
                    selectedRecipe.value = recipe
                }}) {

                Text(recipe.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp) )

            }
            val recipeValue = selectedRecipe.value
            if (recipeValue != null && recipe.id == recipeValue.id) {
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        RecipeDetails(recipeValue)
                        Row(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(8.dp, Alignment.End)) {
                            Button(
                                onClick = {

                                    recLib.removeRecipe(recipeValue.id)
                                    recRepo.saveLibrary()
                                    selectedRecipe.value = null
                                }, modifier = Modifier.size(20.dp), contentPadding = PaddingValues(1.dp),
                                shape = CircleShape
                            ) {
                                Icon(
                                    modifier = Modifier.size(12.dp),
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Delete Recipe"
                                )
                            }

                            Button(
                                onClick = { onEditRecipe(recipeValue) },
                                modifier = Modifier.size(20.dp),
                                contentPadding = PaddingValues(1.dp),
                                shape = CircleShape
                            ) {
                                Icon(
                                    modifier = Modifier.size(12.dp),
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Edit Recipe"
                                )
                            }
                        }
                    }
                }
            }
        }

        item {


            }
        }
    }}
