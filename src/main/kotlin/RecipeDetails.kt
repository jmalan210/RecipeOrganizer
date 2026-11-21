import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle

//a function to display all the recipe information. Put in a central location because it is used more than once.
@Composable
fun RecipeDetails(recipe: Recipe) {
    Text(recipe.description)

    Text(buildAnnotatedString { withStyle(SpanStyle(fontWeight = FontWeight.Bold)){append("Servings: ")}
        append(recipe.servings.toString())
        })
    Text(buildAnnotatedString {withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Total Time: ") }
            append(recipe.convertTotalTime())
        })
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