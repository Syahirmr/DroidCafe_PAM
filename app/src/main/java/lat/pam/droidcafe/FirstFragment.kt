package lat.pam.droidcafe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import lat.pam.droidcafe.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Aksi untuk menampilkan toast saat gambar diklik
        view.findViewById<ImageView>(R.id.donut).setOnClickListener {
            displayToast(getString(R.string.donut_order_message))
        }

        view.findViewById<ImageView>(R.id.ice_cream).setOnClickListener {
            displayToast(getString(R.string.ice_cream_order_message))
        }

        view.findViewById<ImageView>(R.id.froyo).setOnClickListener {
            displayToast(getString(R.string.froyo_order_message))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Fungsi untuk menampilkan pesan toast
    private fun displayToast(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}
