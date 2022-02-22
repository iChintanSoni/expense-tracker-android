package dev.chintansoni.domain.model

data class Category(
    val id: Int,
    val name: String,
    val description: String,
    val canBeDeleted: Boolean
) {

    /**
     * returns true if id is non-zero, else false
     */
    fun canBeDeleted(): Boolean = id != 0 && canBeDeleted

    companion object {

        fun dummyInstance() = Category(
            id = (0..100).random(),
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
    }
}

val UncategorizedCategory = "Uncategorized"

/**
 * Generates default categories to use
 */
fun generateDefaultCategories(): List<Category> {
    return listOf(
        UncategorizedCategory to "Used when the category is unknown",
        "Food" to "Lunch, Dinner, Snacks, Breakfast",
        "Shopping" to "Footwear, Clothing, Gifts, Books & Magazines, Electronics, Watches",
        "Travel" to "Team outing, Family trip",
        "Subscription" to "Netflix, Hotstar, Amazon Prime"
    ).map {
        Category(
            id = 0,
            name = it.first,
            description = it.second,
            canBeDeleted = it.first != UncategorizedCategory
        )
    }
}

/**
 * Generates dummy list of categories
 */
fun generateDummyTransactions(): List<Transaction> {
    val transactionList = mutableListOf<Transaction>()
    repeat(1000) {
        transactionList.add(Transaction.dummyInstance())
    }
    return transactionList
}