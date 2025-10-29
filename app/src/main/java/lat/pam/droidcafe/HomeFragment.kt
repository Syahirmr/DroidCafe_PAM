package lat.pam.droidcafe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import lat.pam.droidcafe.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // 🔹 Data rekomendasi (dummy)
    private val rekomendasiList = listOf(
        MenuItem("Cappuccino", R.drawable.ic_coffee),
        MenuItem("Latte", R.drawable.ic_latte),
        MenuItem("Croissant", R.drawable.ic_croissant),
        MenuItem("Donut", R.drawable.ic_donut),
        MenuItem("Espresso", R.drawable.ic_espresso)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 🔹 Ambil data user dari SharedPreferences
        val prefs = requireActivity().getSharedPreferences("UserPrefs", android.content.Context.MODE_PRIVATE)
        val fullname = prefs.getString("fullname", null)
        val username = prefs.getString("username", null)

        // 🔹 Tentukan nama yang ditampilkan
        val displayName = when {
            !fullname.isNullOrEmpty() -> fullname
            !username.isNullOrEmpty() -> username
            else -> "User"
        }

        // 🔹 Tampilkan greeting dinamis
        binding.tvGreeting.text = "Halo, $displayName 👋"

        // 🔹 Setup RecyclerView rekomendasi
        setupRekomendasiRecycler()

        return binding.root
    }

    // 🔹 Setup RecyclerView rekomendasi
    private fun setupRekomendasiRecycler() {
        val recyclerView = binding.recyclerRekomendasi
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        recyclerView.adapter = MenuAdapter(rekomendasiList) { selectedItem ->
            // 🔹 Tentukan kategori dari item yang diklik
            val category = when (selectedItem.name) {
                "Cappuccino", "Latte", "Espresso" -> "Coffee"
                "Croissant", "Donut" -> "Snack"
                else -> "Food"
            }

            // 🔹 Siapin bundle
            val bundle = Bundle().apply {
                putString("selectedCategory", category)
            }

            // ✅ Ambil NavController utama dari MainActivity
            val navController = (requireActivity()
                .supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
                .navController

            // ✅ Pastikan MenuFragment nggak dobel di backstack
            navController.popBackStack(R.id.nav_menu, false)

            // ✅ Navigasi ke MenuFragment dengan kategori
            navController.navigate(R.id.nav_menu, bundle)

            // ✅ Update BottomNavigationView biar tab Menu aktif
            val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNav.selectedItemId = R.id.nav_menu

            // ✅ Tampilkan FAB
            (activity as? MainActivity)?.showFab()
        }
    }

    // 🔹 Sembunyikan FAB saat HomeFragment aktif
    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.hideFab()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
