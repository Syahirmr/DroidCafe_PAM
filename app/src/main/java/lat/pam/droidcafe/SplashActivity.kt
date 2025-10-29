package lat.pam.droidcafe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logoImage: ImageView = findViewById(R.id.logoImage)
        val appName: TextView = findViewById(R.id.appName)
        val tagline: TextView = findViewById(R.id.tagline)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        // Awal: Fade-in
        logoImage.startAnimation(fadeIn)
        appName.startAnimation(fadeIn)
        tagline.startAnimation(fadeIn)

        // Setelah 1 detik: scale-up logo
        Handler(Looper.getMainLooper()).postDelayed({
            logoImage.startAnimation(scaleUp)
        }, 1000)

        // Setelah 2.8 detik: fade-out dan pindah activity
        Handler(Looper.getMainLooper()).postDelayed({
            logoImage.startAnimation(fadeOut)
            appName.startAnimation(fadeOut)
            tagline.startAnimation(fadeOut)

            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@SplashActivity, WelcomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }, 800)
        }, 2800)
    }
}
