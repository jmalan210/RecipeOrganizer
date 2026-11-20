import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateListOf

val searchOptions :List<String> = listOf("Title", "Ingredient", "Tag")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchScreen(onNavigate: (String) -> Unit) {
    var searchType = remember{ mutableStateOf<String>("Title") }
    var searchDropdownExpanded = remember{mutableStateOf(false)}
    var searchQuery = remember{mutableStateOf("")}
    val searchOptions :List<String> = listOf("Title", "Ingredient", "Tag")
    val selectedSearchTags = remember{mutableStateListOf<Tag>()}
    Text("Search Screen")
    Column(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),

    ) {
        ExposedDropdownMenuBox( expanded = searchDropdownExpanded.value,
            onExpandedChange = { newSearchDropdownExpanded ->
                searchDropdownExpanded.value = newSearchDropdownExpanded}){

            OutlinedTextField(value = searchType.value, onValueChange = {}, modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true), readOnly = true, label = {
                Text("Search By")}, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = searchDropdownExpanded.value)})

            ExposedDropdownMenu(expanded = searchDropdownExpanded.value, onDismissRequest = {searchDropdownExpanded.value = false}) {
                searchOptions.forEach { option ->
                    DropdownMenuItem(text = { Text(option)},
                        onClick = {searchType.value = option
                        searchDropdownExpanded.value = false})
                }
            }

        }
            when(searchType.value){
                "Title", "Ingredient" -> OutlinedTextField(value = searchQuery.value, onValueChange = {userText: String -> searchQuery.value = userText },
                label = {Text(searchType.value)})

                "Tag" -> tagList.forEach { tag ->
                    Row {
                        Checkbox(checked = false, onCheckedChange = {})
                        Text(tag.name)
                    }

                }
            }




        Button(onClick = { onNavigate("home") }) {
            Text("Home")
        }
    }
}