package lat.pam.droidcafe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import lat.pam.droidcafe.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)

        setupCitySpinner()
        setupSubmitButton()

        return binding.root
    }

    private fun setupCitySpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.city_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCity.adapter = adapter
        }
    }

    private fun setupSubmitButton() {
        binding.btnSubmitOrder.setOnClickListener {
            val name = binding.etName.text.toString()
            val address = binding.etAddress.text.toString()
            val phone = binding.etPhone.text.toString()
            val note = binding.etNote.text.toString()
            val city = binding.spinnerCity.selectedItem.toString()

            val deliveryMethod = when {
                binding.rbSameDay.isChecked -> "Same Day"
                binding.rbNextDay.isChecked -> "Next Day"
                binding.rbPickup.isChecked -> "Pick Up"
                else -> "-"
            }

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(requireContext(), "Mohon lengkapi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(
                requireContext(),
                "Pesanan atas nama $name\nDikirim ke $city ($deliveryMethod)",
                Toast.LENGTH_LONG
            ).show()

            // ✅ Navigasi ke halaman konfirmasi (Screen 19)
            findNavController().navigate(R.id.nav_confirmation)
        }
    }

    // 🔹 Sembunyikan FAB pas halaman Order dibuka
    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.hideFab()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
