// ==========================================
// Demo Name: Android-Google-BottomSheet-Auth
// Developed & Designed By: DcodeSharvi
// Description: Modern Google Bottom Sheet Sign-In and Firebase Auth Implementation
// ==========================================

package com.dcodesharvi.bottomsheetgoogle

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val mainLayout = findViewById<View>(R.id.main)
        val yellowCardInner = findViewById<View>(R.id.yellowCardInnerLayout)

        // Extend yellow card to fill the navigation bar area smoothly
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Padding for top only (Status Bar)
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)

            // Base 80dp padding converted to pixels
            val density = resources.displayMetrics.density
            val basePaddingPx = (80 * density).toInt()

            yellowCardInner.setPadding(
                yellowCardInner.paddingLeft,
                yellowCardInner.paddingTop,
                yellowCardInner.paddingRight,
                basePaddingPx + systemBars.bottom
            )
            insets
        }

        val btnGithub = findViewById<Button>(R.id.btnGithub)
        val btnInsta = findViewById<Button>(R.id.btnInsta)

        btnGithub.setOnClickListener {
            openLink("https://github.com/DcodeSharvi")
        }

        btnInsta.setOnClickListener {
            openLink("https://instagram.com/DcodeSharvi")
        }
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}