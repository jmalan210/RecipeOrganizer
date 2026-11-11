fun main() {

    val chocChipCookies = Recipe ("Chocolate Chip Cookies", 24)

    val butter = Ingredient("Butter", 2.0, "cups")
    val sugar = Ingredient("White sugar", .75, "cup")
    val bSugar = Ingredient("Brown sugar", .75, "cup")
    val eggs = Ingredient("Eggs", 2.0)
    val extract = Ingredient("Maple extract", 1.0, "tsp")
    val soda = Ingredient("Baking Soda", 1.0, "tsp")
    val salt = Ingredient("Salt", 0.5, "tsp")
    val flour = Ingredient("Flour", 2.75,"cups")
    val chips = Ingredient("Semi-sweet chocolate chips", 12.0, "oz")
    var ingredientsList= mutableListOf<Ingredient>(butter, sugar, bSugar, eggs, extract, soda, salt, flour, chips)
    chocChipCookies.ingredients.addAll(ingredientsList)

    chocChipCookies.removeIngredient("Cinnamon")
    for (ingredient in chocChipCookies.ingredients) {
        println(ingredient.name)
    }


}