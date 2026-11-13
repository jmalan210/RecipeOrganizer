class RecipeLibrary() {

    private var list: MutableList<Recipe> = mutableListOf()

    fun addRecipe(recipe: Recipe) {

        list.add(recipe)
    }

    fun findRecipeById(id: String): Recipe? {
        val recipe = list.find{ it.id == id}
        return recipe
    }


    fun removeRecipe(id: String) {
        val recipe = list.find { it.id == id }
        if (recipe != null) {
            list.remove(recipe)
        }

    }
}