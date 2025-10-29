package lat.pam.droidcafe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import lat.pam.droidcafe.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        updateCartUI()

        binding.btnCheckout.setOnClickListener {
            if (CartManager.isEmpty()) {
                Toast.makeText(requireContext(), "Keranjang masih kosong!", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.nav_order)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvCartItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCartItems.adapter = MenuAdapter(CartManager.getItems()) { selectedItem ->
            CartManager.removeItem(selectedItem)
            Toast.makeText(requireContext(), "${selectedItem.name} dihapus dari keranjang", Toast.LENGTH_SHORT).show()
            updateCartUI()
        }
    }

    private fun updateCartUI() {
        if (CartManager.isEmpty()) {
            binding.rvCartItems.visibility = View.GONE
            binding.tvEmptyCart.visibility = View.VISIBLE
        } else {
            binding.rvCartItems.visibility = View.VISIBLE
            binding.tvEmptyCart.visibility = View.GONE
            (binding.rvCartItems.adapter as MenuAdapter).notifyDataSetChanged()
        }

        // 🔹 Pastikan tombol checkout tetap di bawah
        binding.btnCheckout.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.hideFab()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
