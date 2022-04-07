package dev.chintansoni.domain.model

data class Category(
    val id: Long,
    val name: String,
    val description: String,
    val canBeDeleted: Boolean
) {

    /**
     * returns true if id is non-zero, else false
     */
    fun canBeDeleted(): Boolean = id != 0L && canBeDeleted

    companion object {

        val uncategorized = "Uncategorized"

        fun dummyInstance() = Category(
            id = (0..100L).random(),
            name = "Sample Category",
            description = "Sample Description",
            canBeDeleted = false
        )

        fun newInstance() = Category(
            id = 0,
            name = "",
            description = "",
            canBeDeleted = true
        )

        private fun uncategorized() = Category(
            id = 0,
            name = uncategorized,
            description = "Used when the category is unknown",
            canBeDeleted = false
        )

        /**
         * Generates default categories to use
         */
        fun generateDefaultCategories(): List<Category> {
            return mutableListOf(uncategorized()).apply {
                addAll(
                    listOf(
                        "Food" to "Lunch, Dinner, Snacks, Breakfast",
                        "Shopping" to "Footwear, Clothing, Gifts, Books & Magazines, Electronics, Watches",
                        "Travel" to "Team outing, Family trip",
                        "Subscription" to "Netflix, Hotstar, Amazon Prime"
                    ).map {
                        Category(
                            id = 0,
                            name = it.first,
                            description = it.second,
                            canBeDeleted = true
                        )
                    }
                )
            }
        }
    }
}