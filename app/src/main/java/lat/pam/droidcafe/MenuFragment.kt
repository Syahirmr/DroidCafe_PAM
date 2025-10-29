package lat.pam.droidcafe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import lat.pam.droidcafe.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val categories = listOf("Coffee", "Snack", "Dessert", "Food", "Drink")

    private val menuData = mapOf(
        "Coffee" to listOf(
            MenuItem("Cappuccino", R.drawable.ic_coffee),
            MenuItem("Latte", R.drawable.ic_latte),
            MenuItem("Espresso", R.drawable.ic_espresso)
        ),
        "Snack" to listOf(
            MenuItem("Croissant", R.drawable.ic_croissant),
            MenuItem("Donut", R.drawable.ic_donut)
        ),
        "Dessert" to listOf(
            MenuItem("Brownies", R.drawable.ic_donut),
            MenuItem("Cheesecake", R.drawable.ic_croissant)
        ),
        "Food" to listOf(
            MenuItem("Burger", R.drawable.ic_burger),
            MenuItem("Sandwich", R.drawable.ic_croissant)
        ),
        "Drink" to listOf(
            MenuItem("Iced Coffee", R.drawable.ic_coffee),
            MenuItem("Hot Chocolate", R.drawable.ic_latte)
        )
    )

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        // 🔹 Ambil data user dari SharedPreferences
        val prefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val fullname = prefs.getString("fullname", null)
        val username = prefs.getString("username", null)

        val displayName = when {
            !fullname.isNullOrEmpty() -> fullname
            !username.isNullOrEmpty() -> username
            else -> "User"
        }

        binding.tvGreeting.text = "Halo, $displayName ☕"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔹 Setup kategori horizontal
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // 🔹 Ambil kategori dari HomeFragment (default: Coffee)
        val selectedCategory = arguments?.getString("selectedCategory") ?: "Coffee"

        // 🔹 Adapter kategori
        categoryAdapter = CategoryAdapter(categories) { clickedCategory ->
            updateMenuList(clickedCategory)
        }
        binding.rvCategories.adapter = categoryAdapter

        // 🔹 Setup daftar menu grid
        binding.rvMenuItems.layoutManager = GridLayoutManager(requireContext(), 2)

        // 🔹 Tampilkan kategori dari HomeFragment
        updateMenuList(selectedCategory)

        // 🔹 Highlight kategori aktif
        categoryAdapter.setSelectedCategory(selectedCategory)

        // 🔹 Scroll ke posisi kategori aktif
        val index = categories.indexOf(selectedCategory)
        if (index != -1) binding.rvCategories.scrollToPosition(index)
    }

    // ✅ Update fungsi ini biar item bisa ditambah ke keranjang
    private fun updateMenuList(category: String) {
        val menuList = menuData[category] ?: emptyList()

        val menuAdapter = MenuAdapter(menuList) { selectedItem ->
            CartManager.addItem(selectedItem)
            Toast.makeText(requireContext(), "${selectedItem.name} ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
        }

        binding.rvMenuItems.adapter = menuAdapter
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.showFab()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
