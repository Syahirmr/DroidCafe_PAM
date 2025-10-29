package lat.pam.droidcafe

object CartManager {
    private val cartItems = mutableListOf<MenuItem>()

    fun addItem(item: MenuItem) {
        cartItems.add(item)
    }

    fun removeItem(item: MenuItem) {
        cartItems.remove(item)
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun getItems(): List<MenuItem> = cartItems

    // 🔹 Tambahan fungsi biar gampang ngecek kosong/tidak
    fun isEmpty(): Boolean = cartItems.isEmpty()
}
