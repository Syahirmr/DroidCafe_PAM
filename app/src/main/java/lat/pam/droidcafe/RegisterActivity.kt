package lat.pam.droidcafe

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import lat.pam.droidcafe.databinding.ActivityRegisterBinding

data class User(val fullname: String, val username: String, val password: String)

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔐 Toggle show/hide password
        binding.ivToggleRegPassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.etRegPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ivToggleRegPassword.setImageResource(R.drawable.ic_eye)
            } else {
                binding.etRegPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.ivToggleRegPassword.setImageResource(R.drawable.ic_eye_off)
            }
            binding.etRegPassword.setSelection(binding.etRegPassword.text.length)
        }

        val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val gson = Gson()
        val type = object : TypeToken<MutableList<User>>() {}.type
        var userList: MutableList<User> = gson.fromJson(prefs.getString("users", "[]"), type) ?: mutableListOf()

        // ☕ Tombol Register
        binding.btnRegister.setOnClickListener {
            val fullName = binding.etRegFullName.text.toString().trim()
            val username = binding.etRegUsername.text.toString().trim()
            val password = binding.etRegPassword.text.toString().trim()

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields ☕", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cek kalau username sudah dipakai
            val existingUser = userList.find { it.username == username }
            if (existingUser != null) {
                Toast.makeText(this, "Username already taken 😅", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Tambahkan user baru
            userList.add(User(fullName, username, password))

            // Simpan lagi ke SharedPreferences
            val editor = prefs.edit()
            editor.putString("users", gson.toJson(userList))
            editor.apply()

            Toast.makeText(this, "Account created successfully ✅", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // ✨ Text "Login"
        binding.tvLoginNow.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
