
class Recipe(var title: String, var servings: Int, var ingredients: MutableList<Ingredient> = mutableListOf(), var instructions: MutableList<Instruction> = mutableListOf()) {
    var description: String = ""
    var totalTime: Int = 0
    var tags: MutableList<Tag> = mutableListOf()

    //edits Recipe title
    fun editTitle(recipe: Recipe) {

            println("Please enter your updated recipe title: ")
            var editedTitle = readln()
            while (editedTitle.isBlank()) {
                println("Response blank. Please enter your updated title: ")
                editedTitle = readln()
            }
            recipe.title = editedTitle
            println("Title successfully changed!")
            println(recipe.title)
        }


    //adds an Ingredient to the list of ingredients in the recipe
    fun addIngredient(ingredient: Ingredient) {
        ingredients.add(ingredient)
    }

    //edits an Ingredient by accessing its index
    fun editIngredient(index: Int) {
        var editedIngredient: Ingredient? = null
        var updatedQuantity: Double? = null
        var updatedUnit: String
        var updatedName: String

        if (index >= 1 && index <= ingredients.size) {
            editedIngredient = ingredients[index -1]
        }
        if (editedIngredient != null) {

            var response: String = "-1"
            while (response != "0") {
                println("Which property would you like to edit?")
                println("0. Cancel")
                println("1. Quantity")
                println("2. Unit")
                println("3. Name")
                response = readln()

                if (response == "1") {
                    println("Please enter your updated quantity in decimal format (ie., 1.0 or 2.25")
                    updatedQuantity = readln().toDoubleOrNull()

                    while (updatedQuantity == null) {
                        println("Invalid input. Please enter a valid quantity in decimal format:")
                        updatedQuantity = readln().toDoubleOrNull()
                    }
                    var validQuantity: Double = updatedQuantity!!
                    while (validQuantity <= 0) {

                            println("Please enter a valid quantity greater than 0: ")
                            val newQuantity = readln().toDoubleOrNull()
                                if (newQuantity != null) {
                                    validQuantity = newQuantity

                                }
                    }
                    editedIngredient.quantity = validQuantity
                    println("Quantity successfully updated!")

                } else if (response == "2") {
                    println("Please enter your updated unit: ")
                    updatedUnit = readln()
                    while (updatedUnit.isBlank()) {
                        println("Empty response. Please enter your updated unit: ")
                        updatedUnit = readln()
                    }
                    editedIngredient.unit = updatedUnit
                    println("Unit successfully updated!")

                } else if (response == "3") {
                    println("Please enter your updated name: ")
                    updatedName = readln()
                    while (updatedName.isBlank()) {
                        println("Empty response. Please enter your updated name:")
                        updatedName = readln()
                    }
                    editedIngredient.name = updatedName
                    println("Name successfully updated!")
                } else if (response != "0"){
                    println("Invalid choice. Please enter 1, 2, or 3: ")

                }
            }

        }
        }

    // removes an Ingredient from the list of ingredients while ignoring case
    fun removeIngredient(name: String) {
        var deletedIngredient: Ingredient? = null
        for (ingredient in ingredients) {
            if (ingredient.name.equals(name, ignoreCase = true)) {
                deletedIngredient = ingredient
                break
            }
        }
        if (deletedIngredient != null) {
            ingredients.remove(deletedIngredient)
        }
    }

    // adds an Instruction to the list of instructions
    fun addInstruction(instruction: Instruction) {
        instructions.add(instruction)
    }

    //edits an instruction at the given index
    fun editInstruction(index:Int) {

        if (index >= 1 && index <= instructions.size) {
            println("Please enter your updated instruction: ")
            var editedInstruction = readln()
            while (editedInstruction.isBlank()) {
                println("Response blank. Please enter your updated instruction: ")
                editedInstruction = readln()
            }
                instructions[index - 1].step = editedInstruction
            println("Instruction successfully changed!")
            println(instructions[index -1].step)
            }
         else {
            println("No instruction at that index.")
        }
    }
    // removes an Instruction at a given index
    fun removeInstruction(index: Int) {
        if (index >= 1 && index <= instructions.size) {
            instructions.removeAt(index - 1)
        }
    }

    //checks tag list for dupes and adds a unique capitalized tag to the tags list
    fun addTag(tag: Tag) {
        val lcName: String = tag.name.lowercase()
        val correctedName = lcName.replaceFirstChar { it.uppercase() }
        if (!tags.any { existingTag -> existingTag.name.equals(correctedName, ignoreCase = true) }) {

            val newTag = Tag(correctedName)
            tags.add(newTag)
        }
    }

    //removes a found tag from the tags list
    fun removeTag(tag: Tag) {
        val deletedTag = tags.find { existingTag -> existingTag.name.equals(tag.name, ignoreCase = true) }

        if (deletedTag != null) {
            tags.remove(deletedTag)
        }

    }
}



