fun main() {

    val chocChipCookies = Recipe ("Chocolate Chip Cookies", 24)

    val butter = Ingredient("Butter, softened", 2.0, "cups")
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

//    chocChipCookies.removeIngredient("Cinnamon")
    for (ingredient in chocChipCookies.ingredients) {
        println(ingredient.name)
    }
    val preheat = Instruction("Preheat oven to 375 F.")
    val wet = Instruction("In a stand mixer, cream butter, sugars, eggs, and extract until light and fluffy.")
    val dry = Instruction("Add soda, salt, and flour and mix until just combined.")
    val stir = Instruction("Stir in chocolate chips.")
    val bake = Instruction("Place 2-Tbsp scoops on cookie sheet and bake for 10-12 minutes.")
    val cool = Instruction("Allow to cool on cookie sheet for 5-10 minutes until set.")
    var instructionsList = mutableListOf<Instruction>(preheat, wet, dry, stir, bake, cool)
    chocChipCookies.instructions.addAll(instructionsList)

//    chocChipCookies.removeInstruction(2)
    for (instruction in chocChipCookies.instructions.withIndex()) {

            println( "${instruction.index + 1}. ${instruction.value.step}")

    }

    val cookies = chocChipCookies.addTag("Cookies")
    val test1 = chocChipCookies.addTag("COOKIES")
    val test2 = chocChipCookies.addTag("cookies")
    val desserts = chocChipCookies.addTag("Desserts")
    val chocolate = chocChipCookies.addTag("Chocolate")

//    chocChipCookies.removeTag("deSserts")
    for (tag in chocChipCookies.tags) {
        println(tag)
    }

}