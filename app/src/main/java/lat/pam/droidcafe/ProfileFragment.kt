package lat.pam.droidcafe

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import lat.pam.droidcafe.databinding.FragmentProfileBinding
import java.io.File
import java.io.FileOutputStream

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val PICK_IMAGE_REQUEST = 1001
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val fullname = prefs.getString("fullname", "User")
        val username = prefs.getString("username", "@username")
        val savedPhotoPath = prefs.getString("profile_photo_path", null)

        binding.tvFullname.text = fullname
        binding.tvUsername.text = "@$username"

        // 🖼️ Load foto profil terakhir (kalau ada)
        if (savedPhotoPath != null) {
            val file = File(savedPhotoPath)
            if (file.exists()) binding.imgProfile.setImageURI(Uri.fromFile(file))
        }

        // 📸 Tombol ubah foto profil
        binding.btnChangePhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        // ⚙️ Edit profil
        binding.tvEditProfile.setOnClickListener {
            android.widget.Toast.makeText(requireContext(), "Fitur edit profil belum tersedia ☕", android.widget.Toast.LENGTH_SHORT).show()
        }

        // 🛍️ Pesanan Saya
        binding.tvOrders.setOnClickListener {
            android.widget.Toast.makeText(requireContext(), "Fitur pesanan segera hadir 🍰", android.widget.Toast.LENGTH_SHORT).show()
        }

        // 🚪 Tombol Logout
        binding.tvLogout.setOnClickListener {
            prefs.edit().clear().apply()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    // 🔄 Hasil ambil gambar dari galeri
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            imageUri?.let { uri ->
                binding.imgProfile.setImageURI(uri)
                saveProfileImageToCache(uri)
            }
        }
    }

    // 💾 Simpan foto ke cache + SharedPreferences
    private fun saveProfileImageToCache(uri: Uri) {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val file = File(requireContext().cacheDir, "profile_photo.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()

        val prefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        prefs.edit().putString("profile_photo_path", file.absolutePath).apply()
    }

    // 🔹 Sembunyikan FAB saat masuk ke ProfileFragment
    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.hideFab()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
