import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

//searchScreen handles all searchs in the library by title, ingredient, or tag, and return a clickable list to the user.
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchScreen(onNavigate: (String) -> Unit, recLib: RecipeLibrary) {
    val searchType = remember{ mutableStateOf("Title") }
    val searchDropdownExpanded = remember{mutableStateOf(false)}
    val searchQuery = remember{mutableStateOf("")}
    val searchOptions :List<String> = listOf("Title", "Ingredient", "Tag")
    val selectedSearchTags = remember{mutableStateListOf<Tag>()}
    val searchResults = remember{mutableStateListOf<Recipe>()}
    val selectedRecipe = remember{mutableStateOf<Recipe?>(null)}

    Column {
        Button(
            onClick = { onNavigate("home") },
            modifier = Modifier.padding(16.dp).align(Alignment.Start)
        ) {
            Icon(imageVector = Icons.Filled.Home, contentDescription = "Back to Main Page")

        }

    Column(
        modifier = Modifier.padding(16.dp).fillMaxWidth().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),

    ) {
        Text("Search Recipes", Modifier.fillMaxWidth(),style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center )
        ExposedDropdownMenuBox( expanded = searchDropdownExpanded.value,
            onExpandedChange = { newSearchDropdownExpanded ->
                searchDropdownExpanded.value = newSearchDropdownExpanded}){

            OutlinedTextField(value = searchType.value, onValueChange = {}, modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true), readOnly = true, label = {
                Text("Search By")}, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = searchDropdownExpanded.value)})

            ExposedDropdownMenu(expanded = searchDropdownExpanded.value, onDismissRequest = {searchDropdownExpanded.value = false}) {
                searchOptions.forEach { option ->
                    DropdownMenuItem(text = { Text(option)},
                        onClick = {searchType.value = option
                        searchDropdownExpanded.value = false
                        searchResults.clear()})
                }
            }

        }
            when(searchType.value) {
                "Title", "Ingredient" -> OutlinedTextField(
                    value = searchQuery.value, onValueChange = { userText: String -> searchQuery.value = userText },
                    label = { Text(searchType.value) })

                "Tag" -> FlowRow {
                    tagList.forEach { tag ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = selectedSearchTags.contains(tag),
                                onCheckedChange = { checkStatus: Boolean ->
                                    if (checkStatus) {
                                        selectedSearchTags.add(tag)
                                    } else {
                                        selectedSearchTags.remove(tag)
                                    }
                                })
                            Text(tag.name)
                        }

                    }
                }
            }

        Button(onClick = {searchResults.clear()
            selectedRecipe.value = null
            when(searchType.value) {
                "Title" -> {if (searchQuery.value.trim().isNotEmpty()){searchResults.addAll(recLib.searchByTitle(searchQuery.value.lowercase().trim()))}}
                "Ingredient" -> {if (searchQuery.value.trim().isNotEmpty())searchResults.addAll(recLib.searchByIngredient(searchQuery.value.lowercase().trim()))}
                "Tag" -> {if (selectedSearchTags.isNotEmpty()){searchResults.addAll(recLib.searchByTag(selectedSearchTags))}}

            }}) {
            Text("Search")
        }

        searchResults.forEach { recipe ->
            Card(Modifier.fillMaxWidth().padding(10.dp).clickable{
                if (selectedRecipe.value?.id == recipe.id) {selectedRecipe.value = null}
                else {selectedRecipe.value = recipe}}){ Text(recipe.title, modifier = Modifier.padding(10.dp), fontWeight = FontWeight.Bold)}

            val clickedRecipe = selectedRecipe.value
            if(clickedRecipe != null && clickedRecipe.id == recipe.id) {
                println("Displaying recipe details")
                Card{
                    Column(Modifier.padding(16.dp)) {
                        RecipeDetails(clickedRecipe)
                    }
                }
                    }
            }
        }



    }}
