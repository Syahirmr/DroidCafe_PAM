package lat.pam.droidcafe

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import lat.pam.droidcafe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var backPressedTime: Long = 0
    private var backToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Ambil NavHostFragment & NavController utama
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNav: BottomNavigationView = binding.bottomNavigationView
        bottomNav.setupWithNavController(navController)

        // ✅ Listener custom supaya sinkronisasi tetap rapi
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (navController.currentDestination?.id != R.id.nav_home) {
                        navController.popBackStack(R.id.nav_home, false)
                        navController.navigate(R.id.nav_home)
                    }
                    hideFab()
                    true
                }

                R.id.nav_menu -> {
                    if (navController.currentDestination?.id != R.id.nav_menu) {
                        navController.popBackStack(R.id.nav_menu, false)
                        navController.navigate(R.id.nav_menu)
                    }
                    showFab()
                    true
                }

                R.id.nav_profile -> {
                    if (navController.currentDestination?.id != R.id.nav_profile) {
                        navController.popBackStack(R.id.nav_profile, false)
                        navController.navigate(R.id.nav_profile)
                    }
                    hideFab()
                    true
                }

                else -> false
            }
        }

        // ✅ Tampilkan/hilangkan FAB tergantung fragment aktif
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_menu -> showFab()
                else -> hideFab()
            }
        }

        // ✅ Klik FAB → buka CartFragment
        binding.fabOrder.setOnClickListener {
            if (navController.currentDestination?.id != R.id.nav_cart) {
                navController.navigate(R.id.nav_cart)
            }
        }
    }

    // 💡 Bisa diakses dari fragment lain
    fun showFab() {
        binding.fabOrder.show()
    }

    fun hideFab() {
        binding.fabOrder.hide()
    }

    // ✅ DOUBLE BACK EXIT LOGIC
    override fun onBackPressed() {
        // Kalau bukan di HomeFragment, balikin navigasi dulu
        if (navController.currentDestination?.id != R.id.nav_home) {
            super.onBackPressedDispatcher.onBackPressed()
            return
        }

        // Kalau udah di HomeFragment → cek double back
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast?.cancel()
            finishAffinity() // langsung tutup app
        } else {
            backToast = Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT)
            backToast?.show()
        }

        backPressedTime = System.currentTimeMillis()
    }
}
