import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
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
}
