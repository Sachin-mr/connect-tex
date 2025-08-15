package com.example.connect_tex.forgotpassword

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.connect_tex.R
import com.example.connect_tex.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupResetButton()
        setupBackButton()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.forgot_password_title)
    }

    private fun setupResetButton() {
        binding.btnResetPassword.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            
            if (validateEmail(email)) {
                performPasswordReset(email)
            }
        }
    }

    private fun setupBackButton() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun validateEmail(email: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            binding.etEmail.error = getString(R.string.email_required)
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = getString(R.string.email_invalid)
            return false
        }

        return true
    }

    private fun performPasswordReset(email: String) {
        // Show loading state
        binding.btnResetPassword.isEnabled = false
        binding.btnResetPassword.text = getString(R.string.sending_reset_link)

        // Simulate password reset process
        binding.btnResetPassword.postDelayed({
            // For demo purposes, always show success
            Toast.makeText(this, getString(R.string.reset_link_sent), Toast.LENGTH_LONG).show()
            
            // Reset button state
            binding.btnResetPassword.isEnabled = true
            binding.btnResetPassword.text = getString(R.string.reset_password)
            
            // Clear email field
            binding.etEmail.text?.clear()
            
        }, 2000) // Simulate 2 second delay
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
