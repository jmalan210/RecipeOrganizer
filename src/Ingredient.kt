//unit is ? and defaults to null to allow for ingredients that don't have quantities, such as eggs
class Ingredient(val name: String, var quantity: Double, val unit: String? = null) {
}