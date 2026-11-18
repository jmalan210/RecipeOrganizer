import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList


val tagList: List<Tag> = listOf(Tag("Breakfast"), Tag("Main Meals"), Tag("Soups"), Tag("Desserts"), Tag("Chocolate"), Tag("Cookies"), Tag("Snacks"), Tag("Quick Meals"), Tag("Appetizers"), Tag("Drinks"), Tag("Chicken"), Tag("Beef"), Tag("Pork"), Tag("Slow Cooker"), Tag("Italian"), Tag("Mexican"), Tag("Indian"), Tag("Thai")).sortedBy {tag -> tag.name }
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddRecipeScreen(onNavigate: (String ) -> Unit, recLib: RecipeLibrary, recRepo: RecipeRepository) {
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
    val instruction = remember{mutableStateOf("")}
    val instructionList = remember{mutableStateListOf<Instruction>()}
    val editedInstruction = remember{mutableStateOf<Int?>(null)}
    val selectedTags: SnapshotStateList<Tag> = remember{mutableStateListOf<Tag>()}
    val errorMessage = remember{mutableStateOf("")}
    val recipeSaved = remember{mutableStateOf(false)}
    val recipeSaveFailed = remember{mutableStateOf(false)}


    //adds an ingredient to ingredientList and resets field values to empty
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

    //adds an instruction to instructionList and resets field values to empty
    fun addInstruction(step: String) {
        if (step.isNotBlank()) {
            val instructionStep = Instruction(step)
            instructionList.add(instructionStep)
            instruction.value = ""
        }
    }

    //checks various fields for validation before committing to Recipe
    fun fieldValidation(): Boolean {
        val convertedServings = servings.value.toIntOrNull()
        val convertedTime = totalTime.value.toIntOrNull()
        if(title.value.isBlank()) {
            return false
        }
        if(convertedServings == null || convertedServings <= 0){
            return false
        }
        if(convertedTime == null || convertedTime <= 0) {
            return false
        }
        if(ingredientList.isEmpty()) {
            return false
        }
        if(instructionList.isEmpty()) {
            return false
        }
        return true
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
            label = { Text("Title") },
             singleLine = true)

        OutlinedTextField(
            description.value,
            onValueChange = { newDescription -> description.value = newDescription },
            label = { Text("Description") },
            maxLines = 5
        )
        OutlinedTextField(
            servings.value,
            onValueChange = { newServings -> servings.value = newServings },
            label = { Text("Servings") },
            singleLine = true)

        Text(errorMessage.value)

        OutlinedTextField(
            totalTime.value,
            onValueChange = { newTotalTime -> totalTime.value = newTotalTime },
            label = { Text("Total Time (minutes)") },
            singleLine = true)

        OutlinedTextField(
            ingredientName.value,
            onValueChange = { newIngredientName: String -> ingredientName.value = newIngredientName },
            label = { Text("IngredientName") },
            singleLine = true)

        OutlinedTextField(
            ingredientQuantity.value,
            onValueChange = { newIngredientQuantity -> ingredientQuantity.value = newIngredientQuantity },
            label = { Text("Quantity") },
            singleLine = true)

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
                unitList.forEach { unit ->
                    DropdownMenuItem(text = { Text(unit) }, onClick = {
                        ingredientUnit.value = unit
                        unitDropdownExpanded.value = false
                    })

                }
            }
        }
            Button(onClick = {
                if (editedIngredient.value == null) {
                    addIngredient(ingredientName.value, ingredientQuantity.value, ingredientUnit.value)
                } else {
                    ingredientList[editedIngredient.value!!] =
                        Ingredient(ingredientName.value, ingredientQuantity.value.toDouble(), ingredientUnit.value)
                    editedIngredient.value = null
                    ingredientName.value = ""
                    ingredientQuantity.value = ""
                    ingredientUnit.value = null
                }
            }) {
                Text("Add Ingredient")
            }


            ingredientList.forEachIndexed { index, ingredient ->
                Text("${ingredient.quantity} ${ingredient.unit} ${ingredient.name}")
                Button(onClick = { ingredientList.remove(ingredient) }) {
                    Text("X")
                }
                Button(onClick = {
                    editedIngredient.value = index
                    ingredientName.value = ingredient.name
                    ingredientUnit.value = ingredient.unit
                    ingredientQuantity.value = ingredient.quantity.toString()
                }) {
                    Text("edit")
                }
            }

            OutlinedTextField(
                instruction.value,
                onValueChange = { newInstruction -> instruction.value = newInstruction },
                label = { Text("Instructions") },
                maxLines = 5
            )

            Button(onClick = {
                if (editedInstruction.value == null) {
                    addInstruction(instruction.value)
                } else {
                    instructionList[editedInstruction.value!!] = Instruction(instruction.value)
                    editedInstruction.value = null
                    instruction.value = ""
                }
            }
            ) {
                Text("Add Instruction")
            }

            instructionList.forEachIndexed { index, instructionStep ->
                Text("${index + 1}. ${instructionStep.step}")
                Button(onClick = {
                    if (editedInstruction.value == index) {
                        editedInstruction.value = null
                        instruction.value = ""
                    }
                    instructionList.remove(instructionStep)
                })


                { Text("X") }

                Button(onClick = {
                    editedInstruction.value = index
                    instruction.value = instructionStep.step
                }) { Text("edit") }
            }

        tagList.forEach {tag ->
            Row {
                Checkbox(checked = selectedTags.contains(tag), onCheckedChange = {isChecked -> if(isChecked) {selectedTags.add(tag)}
                else {selectedTags.remove(tag)}})
                Text(tag.name)

            }
        }

        Button(onClick = {if (fieldValidation()) {
            val convertedServings = servings.value.toIntOrNull()
            val convertedTime = totalTime.value.toIntOrNull()
            val recipe = Recipe(title.value, convertedServings!!, description.value,  convertedTime!!, ingredientList, instructionList, selectedTags  )
            recLib.addRecipe(recipe)
            recRepo.saveLibrary()
            recipeSaved.value = true
            recipeSaveFailed.value = false
            title.value = ""
            servings.value = ""
            description.value = ""
            totalTime.value = ""
            ingredientList.clear()
            instructionList.clear()
            selectedTags.clear()
        }
            else {
            println("Validation failed")
            recipeSaveFailed.value = true
        }})
        {
            Text("Add Recipe")


        }
        if (recipeSaved.value) {
            Text("Recipe saved!")


        }

        if (recipeSaveFailed.value) {
            Text("Unable to save. Please check that all fields are completed correctly!")
        }


}
    Button(onClick = { onNavigate("home") }) {
        Text("Back")

    }}
