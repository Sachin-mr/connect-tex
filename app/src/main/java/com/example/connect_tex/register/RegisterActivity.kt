package com.example.connect_tex.register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.connect_tex.MainActivity
import com.example.connect_tex.R
import com.example.connect_tex.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRegisterButton()
        setupBackButton()
        setupTermsCheckbox()
        setupLoginLink()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.register_title)
    }

    private fun setupRegisterButton() {
        binding.btnRegister.setOnClickListener {
            if (validateForm()) {
                performRegistration()
            }
        }
    }

    private fun setupBackButton() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupTermsCheckbox() {
        binding.cbTerms.setOnCheckedChangeListener { _, isChecked ->
            binding.btnRegister.isEnabled = isChecked
        }
    }

    private fun setupLoginLink() {
        binding.tvLoginLink.setOnClickListener {
            finish() // Go back to login screen
        }
    }

    private fun validateForm(): Boolean {
        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()

        // Validate Full Name
        if (TextUtils.isEmpty(fullName)) {
            binding.etFullName.error = getString(R.string.full_name_required)
            return false
        }

        if (fullName.length < 2) {
            binding.etFullName.error = getString(R.string.full_name_too_short)
            return false
        }

        // Validate Email
        if (TextUtils.isEmpty(email)) {
            binding.etEmail.error = getString(R.string.email_required)
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = getString(R.string.email_invalid)
            return false
        }

        // Validate Password
        if (TextUtils.isEmpty(password)) {
            binding.etPassword.error = getString(R.string.password_required)
            return false
        }

        if (password.length < 6) {
            binding.etPassword.error = getString(R.string.password_too_short)
            return false
        }

        // Validate Confirm Password
        if (TextUtils.isEmpty(confirmPassword)) {
            binding.etConfirmPassword.error = getString(R.string.confirm_password_required)
            return false
        }

        if (password != confirmPassword) {
            binding.etConfirmPassword.error = getString(R.string.passwords_not_match)
            return false
        }

        // Validate Phone (optional but if provided, should be valid)
        if (!TextUtils.isEmpty(phone) && phone.length < 10) {
            binding.etPhone.error = getString(R.string.phone_invalid)
            return false
        }

        // Validate Terms
        if (!binding.cbTerms.isChecked) {
            Toast.makeText(this, getString(R.string.accept_terms_required), Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun performRegistration() {
        // Show loading state
        binding.btnRegister.isEnabled = false
        binding.btnRegister.text = getString(R.string.creating_account)

        // Simulate registration process
        binding.btnRegister.postDelayed({
            // For demo purposes, always show success
            Toast.makeText(this, getString(R.string.registration_success), Toast.LENGTH_LONG).show()
            
            // Navigate to main activity
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            
        }, 2000) // Simulate 2 second delay
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
