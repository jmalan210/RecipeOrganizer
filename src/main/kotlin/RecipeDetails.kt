import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RecipeDetails(recipe: Recipe) {
    Text(recipe.description)

    Text("Servings: ${recipe.servings.toString()}", fontWeight = FontWeight.Bold)
    Text("Total time: ${recipe.totalTime.toString()}", fontWeight = FontWeight.Bold)
    Text("Ingredients:", fontWeight = FontWeight.Bold)
    recipe.ingredients.forEach { ingredient ->
        Text("${ingredient.quantity} ${if (ingredient.unit == "None" || ingredient.unit == null){""} else (ingredient.unit + " ")}${ingredient.name}")
    }
    Text("Instructions:", fontWeight = FontWeight.Bold)
    recipe.instructions.forEach { instruction ->
        Text(instruction.step)
    }
    Text("Tags:", fontWeight = FontWeight.Bold)
    recipe.tags.forEach { tag ->
        Text(tag.name)
    }

}