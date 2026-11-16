import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import java.io.File


class RecipeRepository(val lib:RecipeLibrary) {

    //converts library to JSON
    fun libToJson(): String {
        val library = lib.returnAllRecipes()
        val jsonString = Json.encodeToString(library)
        return jsonString
    }

    //writes JSON to file and creates directory "data" if it does not exist
    fun jsonToFile(json: String) {
        val data = File("data")
        val recipes = File("data/recipes.json")
        if (data.isDirectory()) {
            recipes.writeText(json)

        } else {
            data.mkdir()
            recipes.writeText(json)
        }
    }

    //connects libToJson and jsonToFile so the program only needs to call one function to save
    fun saveLibrary() {
        jsonToFile(libToJson())
    }

    //takes string from file and converts it back to JSON
    fun jsonToLib(file:File): List<Recipe> {
        val string =file.readText()
        val restoredList = Json.decodeFromString<List<Recipe>>(string)
        return restoredList

    }

    fun listToLib(list: List<Recipe>) {
        for (recipe in list) {
            if (lib.findRecipeById(recipe.id) == null)
            {lib.addRecipe(recipe)}
        }
    }

    //connects jsonToLib and listToLib for one call to load repository
    fun loadLib() {
        val dir = File("data")
        if (dir.exists()) {
            val file= File("data/recipes.json")
            if (file.exists()) {
                listToLib(jsonToLib(file))
            }
        }

    }
}



