import java.util.UUID
import kotlinx.serialization.Serializable

@Serializable
class Recipe(var title: String, var servings: Int, var description: String = "",  var totalTime: Int = 0, var ingredients: MutableList<Ingredient> = mutableListOf(), var instructions: MutableList<Instruction> = mutableListOf(), var tags: MutableList<Tag> = mutableListOf(), val id: String = UUID.randomUUID().toString()) {

    //edits Recipe title
    fun editTitle() {

            println("Please enter your updated recipe title: ")
            var editedTitle = readln()
            while (editedTitle.isBlank()) {
                println("Response blank. Please enter your updated title: ")
                editedTitle = readln()
            }
            title = editedTitle
            println("Title successfully changed!")
            println(title)
        }

    //edits Recipe description
    fun editDescription() {
        println("Please enter your updated recipe description: ")
        var editedDesc = readln()
        while (editedDesc.isBlank()) {
            println("Response blank. Please enter your updated description: ")
            editedDesc= readln()
        }
        description = editedDesc
        println("Description successfully changed!")
        println(description)
    }

    //edits Recipe servings
    fun editServings() {
        println("Please enter your updated recipe servings as a whole number: ")
        var editedServings = readln().toIntOrNull()

        while (editedServings == null || editedServings <= 0) {
            println("Invalid input. Please enter a whole number greater than 0: ")
            editedServings = readln().toIntOrNull()
        }

            servings = editedServings!!
            println("Servings updated successfully")
            println(servings)

    }

    //edits Recipe servings
    fun editTotalTime() {
        println("Please enter your updated time as a whole number: ")
        var editedTime = readln().toIntOrNull()

        while (editedTime == null || editedTime <= 0) {
            println("Invalid input. Please enter a whole number greater than 0: ")
            editedTime = readln().toIntOrNull()
        }

        totalTime = editedTime!!
        println("Time updated successfully")
        println(totalTime)

    }
    //converts minutes into hour(s) and minute(s) and accounts for plural and whole hour conditions
    fun convertTotalTime(): String {
        if (totalTime >= 60) {
            var convertedTime = totalTime / 60
            var remainingTime = totalTime % 60
            if ( remainingTime == 0) {
                return "${convertedTime} ${if (convertedTime > 1) "hours" else "hour"}"
            }else return "${convertedTime} ${if (convertedTime > 1) "hours" else "hour"} " +  "${remainingTime} ${if (remainingTime > 1) "minutes" else "minute"}"

        } else
            return "${totalTime} ${if (totalTime > 1) "minutes" else "minute"}"

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



