package lat.pam.droidcafe

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import lat.pam.droidcafe.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔹 Load animasi dari folder res/anim/
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        val fadeScale = AnimationUtils.loadAnimation(this, R.anim.fade_scale_in)

        // 🔹 Jalankan animasi
        binding.illustration.startAnimation(fadeIn)
        binding.titleText.startAnimation(slideUp)
        binding.subText.startAnimation(slideUp)
        binding.buttonLogin.startAnimation(fadeScale)
        binding.buttonRegister.startAnimation(fadeScale)

        // 🔹 Event tombol Login
        binding.buttonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // 🔹 Event tombol Register
        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}
