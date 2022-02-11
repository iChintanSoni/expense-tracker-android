package dev.chintansoni.domain.model

data class Category(val id: Int, val name: String, val description: String) {
    companion object {

        fun dummyInstance() = Category(
            id = (0..100).random(),
            name = "Sample Category",
            description = "Sample Description",
        )

        fun newInstance() = Category(
            id = 0,
            name = "",
            description = "",
        )
    }
}