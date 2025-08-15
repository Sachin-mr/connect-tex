package com.example.connect_tex

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.connect_tex.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLoginButton()
        setupForgotPassword()
        setupSignUpLink()
    }

    private fun setupLoginButton() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInput(email, password)) {
                performLogin(email, password)
            }
        }
    }

    private fun setupForgotPassword() {
        binding.tvForgotPassword.setOnClickListener {
            val intent = Intent(this, com.example.connect_tex.forgotpassword.ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupSignUpLink() {
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, com.example.connect_tex.register.RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            binding.etEmail.error = getString(R.string.email_required)
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = getString(R.string.email_invalid)
            return false
        }

        if (TextUtils.isEmpty(password)) {
            binding.etPassword.error = getString(R.string.password_required)
            return false
        }

        if (password.length < 6) {
            binding.etPassword.error = getString(R.string.password_too_short)
            return false
        }

        return true
    }

    private fun performLogin(email: String, password: String) {
        // Show loading state
        binding.btnLogin.isEnabled = false
        binding.btnLogin.text = getString(R.string.login_logging_in)

        // Simulate login process (replace with actual authentication)
        binding.btnLogin.postDelayed({
            // For demo purposes, accept any valid email/password combination
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Login successful
                Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                
                // Navigate to main activity
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                // Login failed
                Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
                binding.btnLogin.isEnabled = true
                binding.btnLogin.text = getString(R.string.login_button)
            }
        }, 1500) // Simulate 1.5 second delay
    }
}
