package sertifikasivsga.ac.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import sertifikasivsga.ac.R
import sertifikasivsga.ac.ui.auth.LoginActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        findViewById<ImageView>(R.id.splash_logo).setImageResource(R.drawable.logo_splash_screen)

        val loginIntent = Intent(this@SplashScreen, LoginActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(loginIntent)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {
        const val SPLASH_TIME_OUT = 5000
    }
}
