import java.util.UUID
import kotlinx.serialization.Serializable

@Serializable
class Recipe(var title: String, var servings: Int, var description: String = "",  var totalTime: Int = 0, var ingredients: MutableList<Ingredient> = mutableListOf(), var instructions: MutableList<Instruction> = mutableListOf(), var tags: MutableList<Tag> = mutableListOf(), val id: String = UUID.randomUUID().toString()) {



    //converts minutes into hour(s) and minute(s) and accounts for plural and whole hour conditions
    fun convertTotalTime(): String {
        if (totalTime == 0) {
            return ("Not specified")
        }
        return if (totalTime >= 60) {
            val convertedTime = totalTime / 60
            val remainingTime = totalTime % 60
            if ( remainingTime == 0) {
                 "$convertedTime ${if (convertedTime > 1) "hours" else "hour"}"
            }else {"$convertedTime ${if (convertedTime > 1) "hours" else "hour"} " +  "$remainingTime ${if (remainingTime > 1) "minutes" else "minute"}"}

        } else
        {"$totalTime ${if (totalTime > 1) "minutes" else "minute"}"}

    }


}



