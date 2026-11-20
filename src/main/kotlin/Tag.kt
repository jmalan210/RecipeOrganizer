import kotlinx.serialization.Serializable

val tagList: List<Tag> = listOf(Tag("Breakfast"), Tag("Main Meals"), Tag("Soups"), Tag("Desserts"), Tag("Chocolate"), Tag("Cookies"), Tag("Snacks"), Tag("Quick Meals"), Tag("Appetizers"), Tag("Drinks"), Tag("Chicken"), Tag("Beef"), Tag("Pork"), Tag("Slow Cooker"), Tag("Italian"), Tag("Mexican"), Tag("Indian"), Tag("Thai")).sortedBy {tag -> tag.name }

@Serializable
data class Tag(val name: String)
