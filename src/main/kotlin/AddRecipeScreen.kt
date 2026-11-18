import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.mutableStateListOf

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddRecipeScreen(onNavigate: (String) -> Unit) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val servings = remember { mutableStateOf("") }
    val totalTime = remember { mutableStateOf("") }
    val ingredientName = remember { mutableStateOf("") }
    val ingredientQuantity = remember { mutableStateOf("") }
    val ingredientUnit = remember { mutableStateOf<String?>(null) }
    val unitDropdownExpanded = remember { mutableStateOf(false) }
    val unitList: List<String> = listOf(
        "Cup",
        "Tablespoon",
        "Teaspoon",
        "Fluid ounce",
        "Pint",
        "Quart",
        "Gallon",
        "Ounce",
        "Pound",
        "Milligram",
        "Gram",
        "Kilogram",
        "Milliliter",
        "Liter",
        "Pinch",
        "Dash",
        "None",
        "Clove",
        "Can",
        "Box",
        "Package"
    ).sorted()
    val ingredientList = remember{mutableStateListOf<Ingredient>()}
    val editedIngredient = remember{mutableStateOf<Int?>(null)}

    fun addIngredient(name: String, quantity: String, unit: String?) {
        val convertedQuantity = quantity.toDoubleOrNull()
        if (convertedQuantity != null && convertedQuantity > 0 && name.isNotBlank()) {
            val ingredient = Ingredient(name, convertedQuantity, unit)
            ingredientList.add(ingredient)
            ingredientName.value = ""
            ingredientQuantity.value = ""
            ingredientUnit.value = null
        }
    }


    Column(
        modifier = Modifier.padding(16.dp).fillMaxWidth().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add Recipe Screen")
        OutlinedTextField(
            title.value,
            onValueChange = { newTitle -> title.value = newTitle },
            label = { Text("Title") })
        OutlinedTextField(
            description.value,
            onValueChange = { newDescription -> description.value = newDescription },
            label = { Text("Description") },
            minLines = 3,
            maxLines = 5
        )
        OutlinedTextField(
            servings.value,
            onValueChange = { newServings -> servings.value = newServings },
            label = { Text("Servings") })
        OutlinedTextField(
            totalTime.value,
            onValueChange = { newTotalTime -> totalTime.value = newTotalTime },
            label = { Text("Total Time (minutes)") })
        OutlinedTextField(
            ingredientName.value,
            onValueChange = { newIngredientName: String -> ingredientName.value = newIngredientName },
            label = { Text("IngredientName") })

        OutlinedTextField(
            ingredientQuantity.value,
            onValueChange = { newIngredientQuantity -> ingredientQuantity.value = newIngredientQuantity },
            label = { Text("Quantity") })

        ExposedDropdownMenuBox(
            expanded = unitDropdownExpanded.value,
            onExpandedChange = { newUnitDropdownExpanded -> unitDropdownExpanded.value = newUnitDropdownExpanded }) {
            OutlinedTextField(
                value = ingredientUnit.value ?: "",
                readOnly = true,
                onValueChange = {},
                label = { Text("Unit") },
                modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(unitDropdownExpanded.value) })
            ExposedDropdownMenu(
                expanded = unitDropdownExpanded.value,
                onDismissRequest = { unitDropdownExpanded.value = false }) {
                unitList.forEach { unit -> DropdownMenuItem(text = {Text(unit)}, onClick = {ingredientUnit.value = unit
                    unitDropdownExpanded.value = false })

            }
        }
    }
        Button(onClick = {if (editedIngredient.value == null) {addIngredient(ingredientName.value, ingredientQuantity.value, ingredientUnit.value)}
        else { ingredientList[editedIngredient.value!!] = Ingredient(ingredientName.value, ingredientQuantity.value.toDouble(), ingredientUnit.value)
        editedIngredient.value = null
            ingredientName.value = ""
            ingredientQuantity.value = ""
            ingredientUnit.value = null}}) {
            Text("Add Ingredient")
        }


            ingredientList.forEachIndexed { index, ingredient ->
                Text("${ingredient.quantity} ${ingredient.unit} ${ingredient.name}")
                Button(onClick = {ingredientList.remove(ingredient)}) {
                    Text("X")
                }
                Button(onClick = {editedIngredient.value = index
                    ingredientName.value = ingredient.name
                    ingredientUnit.value = ingredient.unit
                ingredientQuantity.value = ingredient.quantity.toString()}) {
                    Text("edit")
                }
            }


}
    Button(onClick = { onNavigate("home") }) {
        Text("Back")
    }}
