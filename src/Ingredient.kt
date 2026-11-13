//unit is ? and defaults to null to allow for ingredients that don't have quantities, such as eggs
class Ingredient(var name: String, var quantity: Double, var unit: String? = null) {
}