class RecipeLibrary() {

    private var list: MutableList<Recipe> = mutableListOf()

    //adds recipe to the library
    fun addRecipe(recipe: Recipe) {

        list.add(recipe)
    }

    //finds recipe by it's UUID
    fun findRecipeById(id: String): Recipe? {
        val recipe = list.find{ it.id == id}
        return recipe
    }

    //removes a recipe from the library
    fun removeRecipe(id: String) {
        val recipe = list.find { it.id == id }
        if (recipe != null) {
            list.remove(recipe)
        }

    }

    //returns all recipes from the library
    fun returnAllRecipes():List<Recipe> {
        return list
    }

    //searches recipe titles for matching strings
    fun searchByTitle(string: String): List<Recipe> {
        val filteredList = mutableListOf<Recipe>()
        for (recipe in list) {
            if (recipe.title.contains(string, ignoreCase = true))
                filteredList.add(recipe)
        }
        return filteredList
    }
}