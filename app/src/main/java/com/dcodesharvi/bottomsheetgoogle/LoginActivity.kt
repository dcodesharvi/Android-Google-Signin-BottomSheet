// ==========================================
// Demo Name: Android-Google-BottomSheet-Auth
// Developed & Designed By: DcodeSharvi
// Description: Modern Google Bottom Sheet Sign-In and Firebase Auth Implementation
// ==========================================

package com.dcodesharvi.bottomsheetgoogle

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val WEB_CLIENT_ID = "206744584369-ichp8iec8pvqmhi6k42dhti7stje4hu4.apps.googleusercontent.com"

    private lateinit var auth: FirebaseAuth

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    // Standard Auth Views
    private lateinit var btnStandardSignIn: View
    private lateinit var tvStandardText: TextView
    private lateinit var pbStandard: ProgressBar

    // Google Auth Views
    private lateinit var btnGoogleSignIn: View
    private lateinit var tvG: TextView
    private lateinit var tvGoogleText: TextView
    private lateinit var tvArrow: TextView
    private lateinit var pbGoogle: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val mainLayout = findViewById<View>(R.id.main)
        val whiteCardInner = findViewById<View>(R.id.whiteCardInnerLayout)

        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeAndSystem = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)

            val density = resources.displayMetrics.density
            val basePaddingPx = (72 * density).toInt()

            whiteCardInner.setPadding(
                whiteCardInner.paddingLeft,
                whiteCardInner.paddingTop,
                whiteCardInner.paddingRight,
                basePaddingPx + imeAndSystem.bottom
            )
            insets
        }

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        etEmail.filters = arrayOf(InputFilter.AllCaps())

        btnStandardSignIn = findViewById(R.id.btnStandardSignIn)
        tvStandardText = findViewById(R.id.tvStandardText)
        pbStandard = findViewById(R.id.pbStandard)

        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn)
        tvG = findViewById(R.id.tvG)
        tvGoogleText = findViewById(R.id.tvGoogleText)
        tvArrow = findViewById(R.id.tvArrow)
        pbGoogle = findViewById(R.id.pbGoogle)

        setupValidationListeners()

        btnGoogleSignIn.setOnClickListener {
            handleGoogleSignIn()
        }

        btnStandardSignIn.setOnClickListener {
            handleStandardSignIn()
        }
    }

    private fun setupValidationListeners() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateInputs()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        etEmail.addTextChangedListener(textWatcher)
        etPassword.addTextChangedListener(textWatcher)

        validateInputs()
    }

    private fun validateInputs() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        val isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isValidPassword = password.isNotEmpty()

        if (isValidEmail && isValidPassword) {
            btnStandardSignIn.isEnabled = true
            btnStandardSignIn.alpha = 1.0f
        } else {
            btnStandardSignIn.isEnabled = false
            btnStandardSignIn.alpha = 0.5f
        }
    }

    private fun setStandardButtonLoadingState(isLoading: Boolean) {
        if (isLoading) {
            btnStandardSignIn.isClickable = false
            tvStandardText.visibility = View.INVISIBLE
            pbStandard.visibility = View.VISIBLE
        } else {
            btnStandardSignIn.isClickable = true
            tvStandardText.visibility = View.VISIBLE
            pbStandard.visibility = View.GONE
        }
    }

    private fun handleStandardSignIn() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        setStandardButtonLoadingState(true)

        lifecycleScope.launch {
            delay(2000)

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@LoginActivity) { task ->
                    setStandardButtonLoadingState(false)
                    if (task.isSuccessful) {
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        @Suppress("DEPRECATION")
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun setGoogleButtonLoadingState(isLoading: Boolean) {
        if (isLoading) {
            btnGoogleSignIn.isClickable = false
            tvG.visibility = View.INVISIBLE
            tvGoogleText.visibility = View.INVISIBLE
            tvArrow.visibility = View.INVISIBLE
            pbGoogle.visibility = View.VISIBLE
        } else {
            btnGoogleSignIn.isClickable = true
            tvG.visibility = View.VISIBLE
            tvGoogleText.visibility = View.VISIBLE
            tvArrow.visibility = View.VISIBLE
            pbGoogle.visibility = View.GONE
        }
    }

    private fun handleGoogleSignIn() {
        setGoogleButtonLoadingState(true)
        val credentialManager = CredentialManager.create(this)

        // Using GetGoogleIdOption restores the modern Bottom Sheet UI
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(WEB_CLIENT_ID)
            .setAutoSelectEnabled(false)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    context = this@LoginActivity,
                    request = request,
                )

                val credential = result.credential
                if (credential is CustomCredential &&
                    credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {

                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    @Suppress("DEPRECATION")
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                } else {
                    Log.e(TAG, "Unexpected type of credential")
                }
            } catch (e: GetCredentialCancellationException) {
                // User intentionally swiped away or cancelled the bottom sheet
                Log.e(TAG, "User cancelled the sign in")
            } catch (e: NoCredentialException) {
                // This is triggered if the OS applies a cooldown because of a previous cancellation
                Log.e(TAG, "Cooldown active or no accounts found")
                Toast.makeText(this@LoginActivity, "Android cooldown active. Try again later or clear the app cache.", Toast.LENGTH_LONG).show()
            } catch (e: GetCredentialException) {
                Log.e(TAG, "Login Failed: ${e.message}")
                Toast.makeText(this@LoginActivity, "Sign-in failed", Toast.LENGTH_SHORT).show()
            } finally {
                setGoogleButtonLoadingState(false)
            }
        }
    }
}