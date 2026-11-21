import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.size



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddRecipeScreen(onNavigate: (String ) -> Unit, recLib: RecipeLibrary, recRepo: RecipeRepository, recipe: Recipe? = null, onRecipeSaved: (String) -> Unit) {
    val title = remember(recipe?.id) { mutableStateOf(recipe?.title ?: "") }
    val description = remember(recipe?.id) { mutableStateOf(recipe?.description ?: "") }
    val servings = remember(recipe?.id) { mutableStateOf(recipe?.servings?.toString() ?: "") }
    val totalTime = remember(recipe?.id) { mutableStateOf(recipe?.totalTime?.toString() ?: "") }
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
    val ingredientList = remember(recipe?.id) {
        mutableStateListOf(
            *(recipe?.ingredients?.toTypedArray() ?: emptyArray<Ingredient>())
        )
    } //*spread operator expands array into individual arguments
    val editedIngredient = remember { mutableStateOf<Int?>(null) }
    val instruction = remember { mutableStateOf("") }
    val instructionList = remember(recipe?.id) {
        mutableStateListOf(
            *(recipe?.instructions?.toTypedArray() ?: emptyArray<Instruction>())
        )
    }
    val editedInstruction = remember { mutableStateOf<Int?>(null) }
    val selectedTags: SnapshotStateList<Tag> =
        remember(recipe?.id) { mutableStateListOf(*(recipe?.tags?.toTypedArray() ?: emptyArray<Tag>())) }
    val errorMessage = remember { mutableStateOf("") }
    val recipeSaved = remember { mutableStateOf(false) }
    val recipeSaveFailed = remember { mutableStateOf(false) }


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
        if (title.value.isBlank()) {
            return false
        }
        if (convertedServings == null || convertedServings <= 0) {
            return false
        }
        if (convertedTime == null || convertedTime <= 0) {
            return false
        }
        if (ingredientList.isEmpty()) {
            return false
        }
        if (instructionList.isEmpty()) {
            return false
        }
        return true
    }


        Column {
            Button(
                onClick = { println("home clicked")
                    onNavigate("home") },
                modifier = Modifier.padding(16.dp).align(Alignment.Start)
            ) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "Back to Main Page")

            }

        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                if (recipe == null) {
                    "Add a New Recipe"
                } else {
                    "Edit Recipe"
                }, style = MaterialTheme.typography.headlineLarge
            )

            OutlinedTextField(
                title.value,
                onValueChange = { newTitle -> title.value = newTitle },
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                description.value,
                onValueChange = { newDescription -> description.value = newDescription },
                label = { Text("Description") },
                maxLines = 5,
                modifier = Modifier.fillMaxWidth()
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    servings.value,
                    onValueChange = { newServings -> servings.value = newServings },
                    label = { Text("Servings") },
                    singleLine = true
                )

                Text(errorMessage.value)

                OutlinedTextField(
                    totalTime.value,
                    onValueChange = { newTotalTime -> totalTime.value = newTotalTime },
                    label = { Text("Total Time (minutes)") },
                    singleLine = true
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {


                OutlinedTextField(
                    ingredientQuantity.value,
                    onValueChange = { newIngredientQuantity -> ingredientQuantity.value = newIngredientQuantity },
                    label = { Text("Quantity") },
                    singleLine = true,
                    modifier = Modifier.weight(1.0F)
                )

                ExposedDropdownMenuBox(
                    expanded = unitDropdownExpanded.value,
                    onExpandedChange = { newUnitDropdownExpanded ->
                        unitDropdownExpanded.value = newUnitDropdownExpanded
                    },
                    modifier = Modifier.weight(1.5F)
                ) {
                    OutlinedTextField(
                        value = ingredientUnit.value ?: "",
                        readOnly = true,
                        onValueChange = {},
                        label = { Text("Unit") },
                        modifier = Modifier.menuAnchor(
                            ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                            enabled = true
                        ),
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
                OutlinedTextField(
                    ingredientName.value,
                    onValueChange = { newIngredientName: String -> ingredientName.value = newIngredientName },
                    label = { Text("IngredientName") },
                    singleLine = true,
                    modifier = Modifier.weight(3.0F)

                )
                Button(
                    onClick = {
                        if (editedIngredient.value == null) {
                            addIngredient(ingredientName.value, ingredientQuantity.value, ingredientUnit.value)
                        } else {
                            ingredientList[editedIngredient.value!!] =
                                Ingredient(
                                    ingredientName.value,
                                    ingredientQuantity.value.toDouble(),
                                    ingredientUnit.value
                                )
                            editedIngredient.value = null
                            ingredientName.value = ""
                            ingredientQuantity.value = ""
                            ingredientUnit.value = null
                        }
                    },
                    modifier = Modifier.size(20.dp), contentPadding = PaddingValues(1.dp),
                    shape = CircleShape
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Ingredient"
                    )
                }
            }


            ingredientList.forEachIndexed { index, ingredient ->
                Row(
                    modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${ingredient.quantity} ${
                            if (ingredient.unit == "None" || ingredient.unit == null) {
                                ""
                            } else (ingredient.unit + " ")
                        }${ingredient.name}"
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { ingredientList.remove(ingredient) },
                            modifier = Modifier.size(20.dp),
                            contentPadding = PaddingValues(0.dp),
                            shape = CircleShape
                        ) {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Delete Ingredient"
                            )
                        }
                        Button(onClick = {
                            editedIngredient.value = index
                            ingredientName.value = ingredient.name
                            ingredientUnit.value = ingredient.unit
                            ingredientQuantity.value = ingredient.quantity.toString()
                        }, modifier = Modifier.size(20.dp), contentPadding = PaddingValues(1.dp), shape = CircleShape) {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit Ingredient"
                            )
                        }
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    instruction.value,
                    onValueChange = { newInstruction -> instruction.value = newInstruction },
                    label = { Text("Instructions (can add multiple steps)") },
                    maxLines = 5,
                    modifier = Modifier.weight(5.0F)
                )

                Button(
                    onClick = {
                        if (editedInstruction.value == null) {
                            addInstruction(instruction.value)
                        } else {
                            instructionList[editedInstruction.value!!] = Instruction(instruction.value)
                            editedInstruction.value = null
                            instruction.value = ""
                        }
                    }, modifier = Modifier.size(20.dp), contentPadding = PaddingValues(0.dp), shape = CircleShape
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Instruction"
                    )
                }
            }

            instructionList.forEachIndexed { index, instructionStep ->
                Row(
                    modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${index + 1}. ${instructionStep.step}")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                if (editedInstruction.value == index) {
                                    editedInstruction.value = null
                                    instruction.value = ""
                                }
                                instructionList.remove(instructionStep)
                            },
                            modifier = Modifier.size(20.dp),
                            contentPadding = PaddingValues(1.dp),
                            shape = CircleShape
                        )


                        {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Delete Instruction"
                            )
                        }

                        Button(
                            onClick = {
                                editedInstruction.value = index
                                instruction.value = instructionStep.step
                            },
                            modifier = Modifier.size(20.dp),
                            contentPadding = PaddingValues(1.dp),
                            shape = CircleShape
                        )
                        {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit Instruction"
                            )
                        }
                    }
                }
            }
            Text("Categories")
            FlowRow(modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.outline).padding(12.dp)) {
                tagList.forEach { tag ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = selectedTags.contains(tag), onCheckedChange = { isChecked ->
                            if (isChecked) {
                                selectedTags.add(tag)
                            } else {
                                selectedTags.remove(tag)
                            }
                        })
                        Text(tag.name)

                    }
                }
            }
            Button(onClick = {
                if (fieldValidation()) {
                    val convertedServings = servings.value.toIntOrNull()
                    val convertedTime = totalTime.value.toIntOrNull()
                    var savedId: String?
                    //logic to add new recipe
                    if (recipe == null) {
                        val recipe = Recipe(
                            title.value,
                            convertedServings!!,
                            description.value,
                            convertedTime!!,
                            ingredientList.toMutableList(),
                            instructionList.toMutableList(),
                            selectedTags.toMutableList()
                        )
                        recLib.addRecipe(recipe)
                        savedId = recipe.id
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
                    //logic to edit existing recipe
                    else {
                        recipe.title = title.value
                        recipe.description = description.value
                        recipe.servings = convertedServings!!
                        recipe.totalTime = convertedTime!!
                        recipe.ingredients = ingredientList.toMutableList()
                        recipe.instructions = instructionList.toMutableList()
                        recipe.tags = selectedTags.toMutableList()
                        savedId = recipe.id
                        recipeSaved.value = true
                        recipeSaveFailed.value = false

                    }
                    recRepo.saveLibrary()
                    onRecipeSaved(savedId)
                } else {
                    println("Validation failed")
                    recipeSaveFailed.value = true
                }
            })
            {
                Text(
                    if (recipe == null) {
                        "Add Recipe"
                    } else {
                        "Save Changes"
                    }
                )


            }
            if (recipeSaved.value) {
                Text("Recipe saved!")


            }

            if (recipeSaveFailed.value) {
                Text("Unable to save. Please check that all fields are completed correctly!")
            }


        }

    }}

