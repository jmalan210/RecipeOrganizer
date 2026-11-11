
class Recipe(var title: String, var servings: Int, var ingredients: MutableList<Ingredient> = mutableListOf(), var instructions: MutableList<Instruction> = mutableListOf()) {
    var description: String = ""
    var totalTime: Int = 0
    var tags: MutableList<String> = mutableListOf()

    //adds an Ingredient to the list of ingredients in the recipe
    fun addIngredient(ingredient: Ingredient) {
        ingredients.add(ingredient)
    }

    // removes an Ingredient from the list of ingredients while ignoring case
    fun removeIngredient(name: String) {
        var deletedIngredient: Ingredient? = null
        for (ingredient in ingredients) {
            if (ingredient.name.equals(name, ignoreCase = true)){
                deletedIngredient = ingredient
                break
            }
        }
            if (deletedIngredient != null) {
                ingredients.remove(deletedIngredient)
            }
        }

    // adds an Instruction to the list of instructions
    fun addInstruction(instruction:Instruction) {
        instructions.add(instruction)
    }

    // removes an Instruction at a given index
    fun removeInstruction(index: Int) {
        if (index >=1 && index <= instructions.size) {
        instructions.removeAt(index - 1)

    }
    }


    }
