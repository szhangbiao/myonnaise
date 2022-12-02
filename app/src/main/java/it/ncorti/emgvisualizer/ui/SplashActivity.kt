package it.ncorti.emgvisualizer.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import it.ncorti.emgvisualizer.R
import it.ncorti.emgvisualizer.utils.KEY_COMPLETED_ONBOARDING
import it.ncorti.emgvisualizer.utils.PREFS_GLOBAL
import it.ncorti.emgvisualizer.utils.REQUEST_LOCATION_CODE

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestPermission()
    }

    private fun requestPermission() {
        val hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        if (hasPermission) {
            goToNextScreen()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_LOCATION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goToNextScreen()
                } else {
                    Toast.makeText(this, getString(R.string.location_permission_denied_message), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToNextScreen() {
        // Checking if we should on-board the user the first time.
        val prefs = getSharedPreferences(PREFS_GLOBAL, Context.MODE_PRIVATE)
        if (!prefs.getBoolean(KEY_COMPLETED_ONBOARDING, false)) {
            startActivity(Intent(this, IntroActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}