import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class RecipeLibrary() {

    private var list: SnapshotStateList<Recipe> = mutableStateListOf()

    //adds recipe to the library
    fun addRecipe(recipe: Recipe) {

        list.add(recipe)
    }

    //finds recipe by its UUID
    fun findRecipeById(id: String): Recipe? {
        val recipe = list.find{ it.id == id}
        return recipe
    }

    //removes a recipe from the library
    fun removeRecipe(id: String) {
        println(list.size)
        val recipe = list.find { it.id == id }
        println(recipe==null)
        println(recipe?.id)
        if (recipe != null) {
            list.remove(recipe)
            list.forEach { recipe ->
                println("${recipe.title} - ${recipe.id}")
            }
            println(list.size)
            list.forEach { recipe ->
                println(recipe.id)
            }
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

    //searches for a recipe by tag.  any() stops function once first tag is found, preventing dupes
    fun searchByTag(searchTag: String): List<Recipe> {
        val filteredList = mutableListOf<Recipe>()
        for (recipe in list) {
            if (recipe.tags.any {tag -> tag.name.equals(searchTag, ignoreCase = true)}) {
                filteredList.add(recipe)
                }

            }
        return filteredList

    }

    fun searchByIngredient(searchIngredient: String): List<Recipe> {
        val filteredList = mutableListOf<Recipe>()
        for (recipe in list) {
            if (recipe.ingredients.any {ingredient -> ingredient.name.contains(searchIngredient, ignoreCase = true)}) {
                filteredList.add(recipe)
            }
        }
        return filteredList
    }

}
